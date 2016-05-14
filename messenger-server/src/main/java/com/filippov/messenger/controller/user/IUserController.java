package com.filippov.messenger.controller.user;

import com.filippov.messenger.entity.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserController {
    ResponseEntity<Integer> createUser(String email, String password, String username);

    ResponseEntity<User> getUser(Integer id);

    ResponseEntity<List<User>> getUsers(String email, String name);

    ResponseEntity updateUser(Integer id, User sourceUser);

    ResponseEntity deleteUser(Integer id);
}
