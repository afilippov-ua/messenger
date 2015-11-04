package com.filippov.messenger.service.user;

import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserDao userDao;

    @Transactional
    public User createUser(String email, String password) {
        return userDao.createUser(email, password);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers(String email) {

        List<User> userList = null;
        if(email != null) {
            User currentUser = userDao.getUserByEmail(email);
            if (currentUser != null) {
                userList = new ArrayList<>();
                userList.add(currentUser);
            }
        } else {
            userList = userDao.getUsers();
        }
        return userList;
    }

    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Transactional
    public boolean deleteUser(int id) {

        User currentUser = userDao.getUserById(id);
        if (currentUser == null)
            return false;

        return userDao.deleteUser(currentUser);
    }

    @Transactional
    public boolean updateUser(int id, User newUser) {

        if (newUser == null)
            return false;

        User currentUser = userDao.getUserById(id);
        if (currentUser == null)
            return false;

        currentUser.loadValues(newUser);
        return userDao.updateUser(currentUser);
    }
}
