package okestro.hjpark.service;

import okestro.hjpark.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> userList();
    UserEntity addUser(UserEntity user);
    UserEntity updateUser(UserEntity user);
    void deleteBoard(Long id);
}
