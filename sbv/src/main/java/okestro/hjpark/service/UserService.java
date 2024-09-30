package okestro.hjpark.service;

import okestro.hjpark.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserEntity> userList();
    UserEntity addUser(UserEntity user);
    UserEntity updateUser(UserEntity user);
    void deleteBoard(Long id);
}
