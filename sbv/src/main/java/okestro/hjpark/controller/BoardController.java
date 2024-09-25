package okestro.hjpark.controller;

import okestro.hjpark.base.BaseResponse;
import okestro.hjpark.commons.Utils;
import okestro.hjpark.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081") // Vue 앱의 URL
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse> boardList() {
        Map<String, Object> boardList = boardService.boardList();

        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(Utils.asMap(boardList))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<BaseResponse> boardDetail(@PathVariable("id") int id) {
        Map<String, Object> boardDetail = boardService.boardDetail(id);

        if (boardDetail == null) {
            BaseResponse response = BaseResponse.builder()
                    .code("NOT_FOUND")
                    .data(null)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(Utils.asMap(boardDetail))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
