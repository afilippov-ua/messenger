package com.filippov.messenger.controller.user;

import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController implements IUserController {

    @Autowired
    IUserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestHeader("email") String email,
                                     @RequestHeader("password") String password) {
        if (userService.createUser(email, password) == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
        User currentUser = userService.getUserById(id);
        if (currentUser == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(
            @RequestHeader(value = "email", required = false) String email,
            @RequestHeader(value = "name", required = false) String name) {
        if (name != null)
            return new ResponseEntity<>(userService.getUsersByName(name), HttpStatus.OK);
        else
            return new ResponseEntity<>(userService.getUsers(email), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@PathVariable("id") Integer id,
                                     @RequestBody User sourceUser) {
        if (userService.updateUser(id, sourceUser))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {
        if (userService.deleteUser(id))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}