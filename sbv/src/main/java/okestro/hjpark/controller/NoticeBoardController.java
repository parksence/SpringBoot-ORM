package okestro.hjpark.controller;

import okestro.hjpark.base.BaseResponse;
import okestro.hjpark.entity.NoticeBoardEntity;
import okestro.hjpark.service.NoticeBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        Map<String, Object> map = new HashMap();

        // NoticeBoardList 조회
        List<NoticeBoardEntity> boardList = noticeBoardService.boardList();

        // List<Entity> -> map 변환
        for (NoticeBoardEntity board : boardList) {
            map.put(board.getId().toString(), board);
        }
        
        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(map)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteBoard(@PathVariable("id") Long id) {
        noticeBoardService.deleteBoard(id);

        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 유저 생성 API

    // 유저 수정 API

    // 유저 삭제 API

    // 시간남으면 게시물에 댓글 작성하여 DB에 CURD 구현
}
