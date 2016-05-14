package com.filippov.messenger.service.user;

import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(String email, String password, String name) {
        if (email == null || password == null)
            return null;
        if (userDao.getUserByEmail(email) != null)
            return null;
        return userDao.createUser(new User(email, password, name));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        if (id == null)
            return null;
        return userDao.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        if (email == null)
            return null;
        return userDao.getUserByEmail(email);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersByEmailOrName(String text) {
        if (text == null)
            return null;
        return userDao.findUsersByNameOrEmail(text);
    }

    @Override
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

    @Override
    @Transactional
    public boolean deleteUser(Integer id) {
        if (id == null)
            return false;

        User currentUser = userDao.getUserById(id);
        if (currentUser == null)
            return false;

        return userDao.deleteUser(currentUser);
    }

    @Override
    public String encodePassword(String decodePassword) {
        return passwordEncoder.encode(decodePassword);
    }
}
