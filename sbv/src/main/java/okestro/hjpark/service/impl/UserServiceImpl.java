package okestro.hjpark.service.impl;

import okestro.hjpark.entity.UserEntity;
import okestro.hjpark.repo.UserRepository;
import okestro.hjpark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserEntity> userList() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity addUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteBoard(Long id) {
        userRepository.deleteById(id);
    }
}
