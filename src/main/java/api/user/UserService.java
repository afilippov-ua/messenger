package api.user;

import dao.user.IUserDao;
import dao.user.User;
import dao.user.UserAlreadyExistException;
import dao.user.UserNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserService implements IUserService {

    @Inject
    IUserDao userDao;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestParam("email") String email, @RequestParam("password") String password) {

        try {
            User newUser = userDao.createUser(email, password);
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        } catch (UserAlreadyExistException e){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(@RequestParam(value = "email", required = false) String email) {

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
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {

        User currentUser = userDao.getUserById(id);
        if (currentUser != null){
            return new ResponseEntity<User>(currentUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {

        try {
            userDao.deleteUser(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@PathVariable("id") Integer id, @RequestBody User sourceUser) {

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
