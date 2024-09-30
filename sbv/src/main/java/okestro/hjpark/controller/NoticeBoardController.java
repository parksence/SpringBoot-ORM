package okestro.hjpark.controller;

import okestro.hjpark.base.BaseResponse;
import okestro.hjpark.entity.NoticeBoardEntity;
import okestro.hjpark.service.NoticeBoardService;
import okestro.hjpark.vo.PasswordVerificationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice/board")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    // 게시판 목록 조회 API
    @GetMapping("list")
    public ResponseEntity<BaseResponse> boardList() {
        // NoticeBoardList 조회
        List<NoticeBoardEntity> boardList = noticeBoardService.boardList();

        // List<Entity> -> Map 변환 (Stream API 사용)
        Map<String, Object> boardMap = boardList.stream()
                .collect(Collectors.toMap(board -> board.getId().toString(), board -> board));

        // BaseResponse 객체 생성 및 응답 처리
        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(boardMap)
                .build();

        return ResponseEntity.ok(response); // ResponseEntity 사용 최적화
    }

    // 상세 조회 API
    @GetMapping("/list/{id}")
    public ResponseEntity<BaseResponse> boardDetail(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap();

        NoticeBoardEntity board = noticeBoardService.board(id);
        map.put(board.getId().toString(), board);

        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(map)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 추가 API
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addBoard(@RequestBody NoticeBoardEntity board) {
        Map<String, Object> map = new HashMap();
        NoticeBoardEntity newBoard = noticeBoardService.addBoard(board);

        map.put(newBoard.getId().toString(), newBoard);

        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(map)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 수정 API
    @PutMapping("/update")
    public ResponseEntity<BaseResponse> updateBoard(@RequestBody NoticeBoardEntity board) {
        Map<String, Object> map = new HashMap();
        NoticeBoardEntity updateBoard = noticeBoardService.updateBoard(board);

        map.put(updateBoard.getId().toString(), updateBoard);

        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(map)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 삭제 API
    @PostMapping("/delete")
    public ResponseEntity<String> deleteBoard(@RequestBody Map<String, List<Long>> requestData) {

        List<Long> ids = requestData.get("ids");
        System.out.println("ids.toString() = " + ids.toString());
        try {
            // 삭제 로직 수행
            for (Long id : ids) {
                 noticeBoardService.deleteBoard(id);
            }
            return ResponseEntity.ok("게시물이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
        }
    }

    // 게시물 패스워드 확인
    @PostMapping("/verify-password")
    public ResponseEntity<BaseResponse> getBoardPassword(@RequestBody PasswordVerificationRequest request) {
        Long postId = request.getId(); // ID 가져오기
        String password = request.getPassword(); // 패스워드 가져오기

        // 패스워드 검증 로직
        boolean isValid = noticeBoardService.verifyPassword(postId, password); // 패스워드 검증 메서드 호출

        BaseResponse response = new BaseResponse();
        if (isValid) {
            response.setCode("true");
        } else {
            response.setCode("false");
        }

        return ResponseEntity.ok(response);
    }
}
