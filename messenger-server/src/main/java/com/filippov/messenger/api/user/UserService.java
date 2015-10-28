package com.filippov.messenger.api.user;

import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.dao.user.UserAlreadyExistException;
import com.filippov.messenger.dao.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserService implements IUserService {

    @Autowired
    IUserDao userDao;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestHeader("email") String email,
                                     @RequestHeader("password") String password) {

        try {
            userDao.createUser(email, password);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (UserAlreadyExistException e){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(value = "email", required = false) String email) {

        List<User> userList;
        if(email != null) {
            User currentUser = userDao.getUserByEmail(email);
            if (currentUser != null) {
                userList = new ArrayList<User>();
                userList.add(currentUser);
            } else {
                userList = null;
            }
        } else {
            userList = userDao.getUsers();
        }
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {

        User currentUser = userDao.getUserById(id);
        if (currentUser != null){
            return new ResponseEntity<User>(currentUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {

        try {
            userDao.deleteUser(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity updateUser(@PathVariable("id") Integer id,
                                     @RequestBody User sourceUser) {

        User currentUser = userDao.getUserById(id);
        if (currentUser == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        currentUser.loadValues(sourceUser);
        try {
            userDao.updateUser(id, currentUser);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
