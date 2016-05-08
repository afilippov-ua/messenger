package com.filippov.messenger.service.user;

import com.filippov.messenger.entity.user.User;

import java.util.List;

public interface IUserService {
    User createUser(String email, String password, String name);

    User getUserById(Integer id);

    List<User> getUsers(String email);

    List<User> findUsersByEmailOrName(String text);

    boolean updateUser(Integer id, User sourceUser);

    boolean deleteUser(Integer id);
}
