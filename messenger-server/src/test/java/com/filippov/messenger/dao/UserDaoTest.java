package com.filippov.messenger.dao;

import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.hibernate.PersistentObjectException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class UserDaoTest {

    @Autowired
    IUserDao userDao;

    /* Create user
     * User doesn't exist in DB.
     * Expected (not null) */
    @Test
    public void createUserTest(){

        String email = "testUser@mail.com";
        String password = "12345";

        User user = userDao.createUser(email, password);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertNotEquals(new Integer(0), user.getId());
    }

    /* Create user
     * User already exists in DB.
     * Expected null */
    @Test
    public void createUserAlreadyExistTest() {
        assertNull(userDao.createUser("filippov@mail.com", "12345"));
    }


    /* Create user
     * Illegal arguments
     * Expected null */
    @Test
    public void createUserIncorrectEmailTest() {
        assertNull(userDao.createUser(null, "12345"));
        assertNull(userDao.createUser("filippov@mail.com", null));
    }

    /* Update user data
     * User already exists in DB
     * Expected true */
    @Test
    public void updateUserTest(){

        String email = "UpdateUser@mail.com";

        User user = userDao.getUserByEmail(email);
        assertNotNull(user);

        String newPassword = "111";
        String newName = "John Doe";

        assertNotEquals(newPassword, user.getPassword());
        assertNotEquals(newName, user.getName());

        user.setPassword(newPassword);
        user.setName(newName);

        assertTrue(userDao.updateUser(user));

        User currentUser = userDao.getUserByEmail(email);
        assertNotNull(currentUser);

        assertEquals(newPassword, user.getPassword());
        assertEquals(newName, user.getName());
    }

    /* Update user
     * User doesn't exist in DB.
     * Expected PersistentObjectException */
    @Test(expected = PersistentObjectException.class)
    public void testUpdateUserException() {

        User user = new User();
        user.setId(-1);
        userDao.updateUser(user);
    }

    /* Update user
     * Illegal arguments
     * Expected false */
    @Test
    public void updateUserIllegalArgumentsTest() {
        assertFalse(userDao.updateUser(null));
    }

    /* Delete user from DB
     * User already exists in DB
     * Expected true */
    @Test
    public void deleteUserTest(){

        String email = "DeleteUser@mail.com";
        User user = userDao.getUserByEmail(email);
        assertNotNull(user);

        assertTrue(userDao.deleteUser(user));

        User currentUser = userDao.getUserByEmail(email);
        assertNull(currentUser);
    }

    /* Delete user from DB
     * User doesn't exist in DB.
     * Expected true */
    @Test
    public void deleteUserNotFoundTest() {

        User user = new User();
        user.setId(-1);
        assertTrue(userDao.deleteUser(user));
    }

    /* Delete user from DB
     * Illegal arguments
     * Expected false */
    @Test
    public void deleteUserIllegalArgumentsTest() {
        assertFalse(userDao.deleteUser(null));
    }

    /* Get user by id from DB
     * User already exists in DB.
     * Expected not null */
    @Test
    public void getUserByIdTest(){

        int id = 3;
        String email = "filippov@mail.com";
        String password = "12345";

        User user = userDao.getUserById(id);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    /* Get user by id
     * User doesn't exist in DB.
     * Expected null */
    @Test
    public void getUserByIdNotFoundTest() {
        assertNull(userDao.getUserById(-1));
    }

    /* Get user by email from DB
     * User already exists in DB.
     * Expected not null */
    @Test
    public void getUserByEmailTest(){

        String email = "filippov@mail.com";
        String password = "12345";
        Integer id = 3;

        User user = userDao.getUserByEmail(email);
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    /* Get user by email
     * User doesn't exist in DB.
     * Expected null */
    @Test
    public void getUserByEmailNotFoundTest() {
        assertNull(userDao.getUserByEmail("incorrectEmail"));
    }

    /* Get user by email from DB
     * Illegal arguments.
     * Expected null */
    @Test
    public void getUserByEmailIllegalArgumentsTest() {
        assertNull(userDao.getUserByEmail(null));
    }

    /* Get list of users from DB
     * Users already exists in DB.
     * Expected not null */
    @Test
    public void getUsersTest(){

        User findUser1 = userDao.getUserByEmail("filippov@mail.com");
        User findUser2 = userDao.getUserByEmail("serdyukov@mail.com");
        User findUser3 = userDao.getUserByEmail("sirosh@mail.com");

        assertNotNull(findUser1);
        assertNotNull(findUser2);
        assertNotNull(findUser3);

        List<User> userList = userDao.getUsers();
        assertNotNull(userList);
        assertNotEquals(0, userList.size());
        assertTrue(userList.contains(findUser1));
        assertTrue(userList.contains(findUser2));
        assertTrue(userList.contains(findUser3));
    }
}
