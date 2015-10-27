package com.filippov.messenger.dao.user;

import com.filippov.messenger.entity.user.User;

import java.util.List;

public interface IUserDao {

    public List<User> getUsers();

    public User getUserById(int id);

    public User getUserByEmail(String email) throws IllegalArgumentException;

    public User createUser(String email, String password) throws UserAlreadyExistException, IllegalArgumentException;

    public void updateUser(int id, User user) throws UserNotFoundException, IllegalArgumentException;

    public void deleteUser(int id) throws UserNotFoundException;

}
