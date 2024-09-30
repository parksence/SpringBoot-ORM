package okestro.hjpark.controller;

import okestro.hjpark.base.BaseResponse;
import okestro.hjpark.entity.NoticeBoardEntity;
import okestro.hjpark.entity.UserEntity;
import okestro.hjpark.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 사용자 조회 API
    @GetMapping("list")
    public ResponseEntity<BaseResponse> userList() {
        // NoticeBoardList 조회
        List<UserEntity> userList = userService.userList();

        // List<Entity> -> Map 변환 (Stream API 사용)
        Map<String, Object> userMap = userList.stream()
                .collect(Collectors.toMap(user -> user.getId().toString(), user -> user));

        // BaseResponse 객체 생성 및 응답 처리
        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(userMap)
                .build();

        return ResponseEntity.ok(response); // ResponseEntity 사용 최적화
    }

    // 사용자 생성 API
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addBoard(@RequestBody UserEntity user) {
        Map<String, Object> map = new HashMap();
        UserEntity addUser = userService.addUser(user);

        map.put(addUser.getId().toString(), addUser);

        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(map)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 사용자 수정 API
    @PutMapping("/update")
    public ResponseEntity<BaseResponse> updateBoard(@RequestBody UserEntity user) {
        Map<String, Object> map = new HashMap();
        UserEntity updateUser = userService.updateUser(user);

        map.put(updateUser.getId().toString(), updateUser);

        BaseResponse response = BaseResponse.builder()
                .code("OK")
                .data(map)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 사용자 삭제 API
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, List<Long>> requestData) {

        List<Long> ids = requestData.get("ids");

        try {
            // 삭제 로직 수행
            for (Long id : ids) {
                userService.deleteBoard(id);
            }
            return ResponseEntity.ok("사용자가 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
        }
    }
}
