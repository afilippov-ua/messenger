package com.filippov.messenger.dao.user;

import com.filippov.messenger.entity.user.User;

import java.util.List;

public interface IUserDao {

    public List<User> getUsers();

    public User getUserById(Integer id);

    public User getUserByEmail(String email);

    public User createUser(String email, String password);

    public boolean updateUser(User user);

    public boolean deleteUser(User user);

}
