package com.filippov.messenger.dao.user;

import com.filippov.messenger.entity.user.User;

import java.util.List;

public interface IUserDao {
    User createUser(User user);

    User getUserById(Integer id);

    User getUserByEmail(String email);

    List<User> getUsers();

    List<User> getUsersByName(String name);

    List<User> findUsersByNameOrEmail(String text);

    boolean updateUser(User user);

    boolean deleteUser(User user);
}
