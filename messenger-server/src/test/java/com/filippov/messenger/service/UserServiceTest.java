package com.filippov.messenger.service;

import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.service.user.IUserService;
import com.filippov.messenger.service.user.UserService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
@ContextConfiguration(locations = "classpath*:test-service-config.xml")
@Transactional
public class UserServiceTest {

    @TestSubject
    private IUserService userService = new UserService();

    @Mock(fieldName = "userDao")
    private IUserDao mockDao;

    @Mock(fieldName = "passwordEncoder")
    private PasswordEncoder mockPasswordEncoder;

    private User testUser1, testUser2, testUser3;

    @Before
    public void setup() {
        testUser1 = new User("test user 1", "12345");
        testUser1.setName("test user name 1");
        testUser1.setId(1);

        testUser2 = new User("test user 2", "12345");
        testUser2.setName("test user name 2");
        testUser2.setId(2);

        testUser3 = new User("test user 3", "12345");
        testUser3.setName("test user name 3");
        testUser3.setId(3);
    }

    /* Test method: "createUser()" */
    @Test
    public void createUserTest() {
        String hash = "$2a$10$coqkujwDNFhiIlSEbvtAxuRo5kTC0WpqzBgtX7rzfifA7m3s7t092";
        String name = "username";
        User testUser = new User("test user", hash, name);
        expect(mockDao.getUserByEmail(testUser.getEmail())).andReturn(null).once();
        expect(mockDao.createUser(eq(testUser))).andReturn(testUser).once();
        replay(mockDao);
        replay(mockPasswordEncoder);

        User user = userService.createUser(testUser.getEmail(), hash, name);
        verify(mockDao);
        verify(mockPasswordEncoder);
        assertNotNull(user);
        assertEquals(testUser, user);
    }

    /* Test method: "createUser()"
    * User with this email is already exists */
    @Test
    public void createUserUserAlreadyExistTest() {
        User testUser = new User("test user", "12345");
        expect(mockDao.getUserByEmail(testUser.getEmail())).andReturn(testUser).once();
        replay(mockDao);

        assertNull(userService.createUser(testUser.getEmail(), testUser.getPassword(), ""));
        verify(mockDao);
    }

    /* Test method: "createUser()"
    * Illegal arguments */
    @Test
    public void createUserIllegalArgumentsTest() {
        replay(mockDao);

        assertNull(userService.createUser(null, testUser1.getPassword(), testUser1.getName()));
        verify(mockDao);

        reset(mockDao);
        replay(mockDao);
        assertNull(userService.createUser(testUser1.getEmail(), null, testUser1.getName()));
        verify(mockDao);
    }

    /* Test method: "getUserById()" */
    @Test
    public void getUserByIdTest() {
        expect(mockDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        replay(mockDao);

        assertEquals(testUser1, userService.getUserById(testUser1.getId()));
        verify(mockDao);
    }

    /* Test method: "getUserById()"
    * user not found */
    @Test
    public void getUserByIdNotFoundTest() {
        expect(mockDao.getUserById(1)).andReturn(null).once();
        replay(mockDao);

        assertNull(userService.getUserById(1));
        verify(mockDao);
    }

    /* Test method: "getUserById()"
    * Illegal arguments */
    @Test
    public void getUserByIdIllegalArgumentsTest() {
        replay(mockDao);
        assertNull(userService.getUserById(null));
        verify(mockDao);
    }

    /* Test method: "getUsers()" */
    @Test
    public void getUsersTest() {
        List<User> list = new ArrayList<>(3);
        list.add(testUser1);
        list.add(testUser2);
        list.add(testUser3);
        expect(mockDao.getUsers()).andReturn(list).once();
        replay(mockDao);

        List<User> newList = userService.getUsers(null);
        verify(mockDao);
        assertNotNull(newList);
        assertEquals(3, newList.size());
        assertTrue(newList.contains(testUser1));
        assertTrue(newList.contains(testUser2));
        assertTrue(newList.contains(testUser3));
    }

    /* Test method: "getUsers()"
    * User not found */
    @Test
    public void getUsersNotFoundTest() {
        expect(mockDao.getUsers()).andReturn(null).once();
        replay(mockDao);

        assertNull(userService.getUsers(null));
        verify(mockDao);
    }

    /* Test method: "getUsers()" by email */
    @Test
    public void getUsersByEmailTest() {
        expect(mockDao.getUserByEmail(testUser1.getEmail())).andReturn(testUser1).once();
        replay(mockDao);

        List<User> list = userService.getUsers(testUser1.getEmail());
        verify(mockDao);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertTrue(list.contains(testUser1));
    }

    /* Test method: "getUsers()" by email
    * user not found */
    @Test
    public void getUsersByEmailNotFoundTest() {
        expect(mockDao.getUserByEmail(testUser1.getEmail())).andReturn(null).once();
        replay(mockDao);

        assertNull(userService.getUsers(testUser1.getEmail()));
        verify(mockDao);
    }

    /* Test method: "findUsersByEmailOrName()" */
    @Test
    public void findUsersByEmailOrNameTest() {
        List<User> list = new ArrayList<>(3);
        list.add(testUser1);
        list.add(testUser2);
        list.add(testUser3);
        expect(mockDao.findUsersByNameOrEmail("test")).andReturn(list).once();
        replay(mockDao);

        List<User> newList = userService.findUsersByEmailOrName("test");
        verify(mockDao);
        assertNotNull(newList);
        assertEquals(3, newList.size());
        assertTrue(newList.contains(testUser1));
        assertTrue(newList.contains(testUser2));
        assertTrue(newList.contains(testUser3));
    }

    /* Test method: "findUsersByEmailOrName()"
    * User not found */
    @Test
    public void findUsersByEmailOrNameNotFoundTest() {
        expect(mockDao.findUsersByNameOrEmail("")).andReturn(null).once();
        replay(mockDao);

        assertNull(userService.findUsersByEmailOrName(""));
        verify(mockDao);
    }

    /* Test method: "updateUser()" */
    @Test
    public void updateUserTest() {
        expect(mockDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockDao.updateUser(testUser1)).andReturn(true).once();
        replay(mockDao);

        assertTrue(userService.updateUser(testUser1.getId(), testUser1));
        verify(mockDao);
    }

    /* Test method: "updateUser()"
    * User not found*/
    @Test
    public void updateUserNotFoundTest() {
        expect(mockDao.getUserById(testUser1.getId())).andReturn(null).once();
        replay(mockDao);

        assertFalse(userService.updateUser(testUser1.getId(), testUser1));
        verify(mockDao);
    }

    /* Test method: "updateUser()"
    * Illegal arguments */
    @Test
    public void updateUserIllegalArgumentsTest() {
        replay(mockDao);

        assertFalse(userService.updateUser(null, testUser1));
        verify(mockDao);

        reset(mockDao);
        replay(mockDao);
        assertFalse(userService.updateUser(testUser1.getId(), null));
        verify(mockDao);
    }

    /* Test method: "deleteUser()" */
    @Test
    public void deleteUserTest() {
        expect(mockDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockDao.deleteUser(testUser1)).andReturn(true).once();
        replay(mockDao);

        assertTrue(userService.deleteUser(testUser1.getId()));
        verify(mockDao);
    }

    /* Test method: "deleteUser()"
    * User not found */
    @Test
    public void deleteUserNotFoundTest() {
        expect(mockDao.getUserById(testUser1.getId())).andReturn(null).once();
        replay(mockDao);

        assertFalse(userService.deleteUser(testUser1.getId()));
        verify(mockDao);
    }

    /* Test method: "deleteUser()"
    * Illegal arguments */
    @Test
    public void deleteUserIllegalArgumentsTest() {
        replay(mockDao);

        assertFalse(userService.deleteUser(null));
        verify(mockDao);
    }
}
