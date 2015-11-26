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
        if (email == null || password == null)
            return null;
        if (userDao.getUserByEmail(email) != null)
            return null;
        return userDao.createUser(new User(email, password));
    }

    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        if (id == null)
            return null;
        return userDao.getUserById(id);
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
    public List<User> getUsersByName(String name) {
        if (name == null)
            return null;
        return userDao.getUsersByName(name);
    }

    @Transactional
    public boolean updateUser(Integer id, User sourceUser) {
        if (id == null || sourceUser == null)
            return false;

        User currentUser = userDao.getUserById(id);
        if (currentUser == null)
            return false;

        currentUser.setName(sourceUser.getName());
        currentUser.setEmail(sourceUser.getEmail());
        currentUser.setPassword(sourceUser.getPassword());
        return userDao.updateUser(currentUser);
    }

    @Transactional
    public boolean deleteUser(Integer id) {
        if (id == null)
            return false;

        User currentUser = userDao.getUserById(id);
        if (currentUser == null)
            return false;

        return userDao.deleteUser(currentUser);
    }
}
