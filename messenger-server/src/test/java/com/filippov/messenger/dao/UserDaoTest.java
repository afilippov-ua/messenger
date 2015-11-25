package com.filippov.messenger.dao;

import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.junit.Before;
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

    private User testUser1, testUser2, testUser3;

    @Before
    public void setup() {
        User user = new User("test user 1", "12345");
        user.setName("test user name 1");
        testUser1 = userDao.createUser(user);
        assertNotNull(testUser1);

        user = new User("test user 2", "12345");
        user.setName("test user name 2");
        testUser2 = userDao.createUser(user);
        assertNotNull(testUser2);

        user = new User("test user 3", "12345");
        user.setName("test user name 3");
        testUser3 = userDao.createUser(user);
        assertNotNull(testUser3);
    }

    /* Test method: "createUser()" */
    @Test
    public void createUserTest() {
        User user = new User("new user", "12345");
        user.setName("new user name");
        assertNull(user.getId());
        user = userDao.createUser(user);
        assertNotNull(user);
        assertNotNull(user.getId());

        User newUser = userDao.getUserById(user.getId());
        assertNotNull(newUser);
        assertEquals(user, newUser);
    }

    /* Test method: "getUser()" */
    @Test
    public void getUserByIdTest() {
        User user = userDao.getUserById(testUser1.getId());
        assertNotNull(user);
        assertEquals(testUser1, user);
    }

    /* Test method: "getUserByEmail()" */
    @Test
    public void getUserByEmailTest() {
        User user = userDao.getUserByEmail(testUser1.getEmail());
        assertNotNull(user);
        assertEquals(testUser1, user);
    }

    /* Test method: "getUsers()" */
    @Test
    public void getUsersTest() {
        List<User> userList = userDao.getUsers();
        assertNotNull(userList);
        assertEquals(3, userList.size());
        assertTrue(userList.contains(testUser1));
        assertTrue(userList.contains(testUser2));
        assertTrue(userList.contains(testUser3));
    }

    /* Test method: "updateUser()" */
    @Test
    public void updateUserTest() {
        final String newEmail = "new email";
        final String newPass = "87654";
        final  String newName = "new name";

        assertNotEquals(newEmail, testUser1.getEmail());
        assertNotEquals(newPass, testUser1.getPassword());
        assertNotEquals(newName, testUser1.getName());
        testUser1.setEmail(newEmail);
        testUser1.setPassword(newPass);
        testUser1.setName(newName);

        assertTrue(userDao.updateUser(testUser1));
        User updatedUser = userDao.getUserById(testUser1.getId());
        assertNotNull(updatedUser);
        assertEquals(testUser1, updatedUser);
    }

    /* Test method: "deleteUser()" */
    @Test
    public void deleteUserTest() {
        assertTrue(userDao.deleteUser(testUser1));
        assertNull(userDao.getUserById(testUser1.getId()));
    }
}
