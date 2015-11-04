package com.filippov.messenger.service.user;

import com.filippov.messenger.entity.user.User;

import java.util.List;

public interface IUserService {

    public User createUser(String email, String password);

    public List<User> getUsers(String email);

    public User getUserById(int id);

    public boolean deleteUser(int id);

    public boolean updateUser(int id, User newUser);
}
