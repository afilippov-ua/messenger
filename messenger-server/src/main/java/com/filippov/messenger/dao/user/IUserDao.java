package com.filippov.messenger.dao.user;

import com.filippov.messenger.entity.user.User;

import java.util.List;

public interface IUserDao {
    User createUser(String email, String password);

    User getUserById(Integer id);

    User getUserByEmail(String email);

    List<User> getUsers();

    boolean updateUser(User user);

    boolean deleteUser(User user);
}
