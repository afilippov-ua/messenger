package com.filippov.messenger.controller.user;

import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.service.user.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("username") String username) {
        logger.trace(String.format("/api/users (POST) - method \"createUser\", email: \"%s\", password: \"%s\", username: \"%s\"", email, password, username));
        try {
            email = URLDecoder.decode(email, "UTF-8");
            password = URLDecoder.decode(password, "UTF-8");
            username = URLDecoder.decode(username, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (userService.createUser(email, password, username) == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
        logger.trace(String.format("/api/users/%d (GET) - method \"getUser\"", id));
        User currentUser = userService.getUserById(id);
        if (currentUser == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers(
            @RequestHeader(value = "email", required = false) String email,
            @RequestHeader(value = "findText", required = false) String findText) {
        logger.trace(String.format("/api/users (GET) - method \"getUsers\""));
        if (findText != null) {
            try {
                findText = URLDecoder.decode(findText, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userService.findUsersByEmailOrName(findText), HttpStatus.OK);
        } else
            return new ResponseEntity<>(userService.getUsers(email), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@PathVariable("id") Integer id,
                                     @RequestBody User sourceUser) {
        logger.trace(String.format("/api/users/%d (PUT) - method \"updateUser\", source user: %s", id, sourceUser));
        // set changed fields
        if (id != null) {
            User userById = userService.getUserById(id);
            if (userById != null) {
                if (sourceUser.getEmail() != null && !sourceUser.getEmail().isEmpty()) {
                    List<User> list = userService.getUsers(sourceUser.getEmail());
                    if (list != null && !list.contains(userById))
                        // another user with this email already exists
                        return new ResponseEntity(HttpStatus.BAD_REQUEST);

                    userById.setEmail(sourceUser.getEmail());
                }
                if (sourceUser.getPassword() != null && !sourceUser.getPassword().isEmpty()) {
                    userById.setPassword(sourceUser.getPassword());
                }
                userById.setName(sourceUser.getName());
                if (userService.updateUser(id, userById))
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                else
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {
        logger.trace(String.format("/api/users/%d (DELETE) - method \"deleteUser\"", id));
        if (userService.deleteUser(id))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}