package dao.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class UserDaoTest {

    @Inject
    IUserDao userDao;

    /** Create user
     * User doesn't exist in DB.
     * User should be created (not null) */
    @Test
    public void createUserTest(){

        String email = "testUser@mail.com";
        String password = "12345";

        // create normal user
        try {
            User user = userDao.createUser(email, password);

            assertNotNull("User shouldn't be null!", user);
            assertEquals(email, user.getEmail());
            assertEquals(password, user.getPassword());
            assertNotEquals(new Integer(0), user.getId());

        } catch (UserAlreadyExistException e){
            fail(String.format("User with email %s already exists. Check the test data.", email));
        }
    }

    /** Create user
     * Incorrect email.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void createUserIncorrectEmailTest() throws UserAlreadyExistException, IllegalArgumentException {
        userDao.createUser(null, "12345");
    }

    /** Create user
     * Incorrect email.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void createUserIncorrectPasswordTest() throws UserAlreadyExistException, IllegalArgumentException {
        userDao.createUser("filippov@mail.com", null);
    }


    /** Create user
     * User already exists in DB.
     * Expected UserAlreadyExistException exception */
    @Test(expected = UserAlreadyExistException.class)
    public void createUserAlreadyExistTest() throws UserAlreadyExistException {
        userDao.createUser("filippov@mail.com", "12345");
    }

    /** Update user data
     * User already exists in DB
     * User should be updated */
    @Test
    public void updateUserTest(){

        String email = "UpdateUser@mail.com";

        try {
            User user = userDao.getUserByEmail(email);

            String newPassword = "111";
            String newName = "John Doe";

            assertNotEquals(newPassword, user.getPassword());
            assertNotEquals(newName, user.getName());

            user.setPassword(newPassword);
            user.setName(newName);

            userDao.updateUser(user.getId(), user);

            User currentUser = userDao.getUserByEmail(email);
            assertNotNull(currentUser);

            assertEquals(newPassword, user.getPassword());
            assertEquals(newName, user.getName());

        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found! Check the test data.", email));
        }

    }

    /** Update user
     * Incorrect user.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void updateUserIncorrectUserTest() throws UserNotFoundException, IllegalArgumentException {
        userDao.updateUser(-1, null);
    }

    /** Update user
     * User doesn't exist in DB.
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testUpdateUserException() throws UserNotFoundException {
        userDao.updateUser(-1, new User("DeleteUser", ""));
    }

    /** Delete user from DB
     * User already exists in DB
     * User should be delete */
    @Test
    public void deleteUserTest(){

        String email = "DeleteUser@mail.com";

        try {
            User user = userDao.getUserByEmail(email);
            userDao.deleteUser(user.getId());

            User currentUser = userDao.getUserByEmail(email);
            assertNull(currentUser);
        } catch (UserNotFoundException e) {
            fail(String.format("User with email %s was not found", email));
        }
    }

    /** Delete user from DB
     * User doesn't exist in DB.
     * Expected UserNotFoundException exception */
    @Test(expected = UserNotFoundException.class)
    public void testDeleteUserException() throws UserNotFoundException {
        userDao.deleteUser(-1);
    }

    /** Get user by id from DB
     * User already exists in DB.
     * User should be get (not null) */
    @Test
    public void getUserByIdTest(){

        int id = 3;
        String email = "filippov@mail.com";
        String password = "12345";

        User user = userDao.getUserById(id);
        assertNotNull("User shouldn't be null!", user);
        assertEquals("Incorrect user", email, user.getEmail());
        assertEquals("Incorrect user", password, user.getPassword());

    }

    /** Get user by id
     * User doesn't exist in DB. */
    @Test
    public void getUserByIdNullIfNotFoundTest() {
        User currentUser = userDao.getUserById(-1);
        assertNull(currentUser);
    }

    /** Get user by email from DB
     * User already exists in DB.
     * User should be get (not null) */
    @Test
    public void getUserByEmailTest(){

        String email = "filippov@mail.com";
        String password = "12345";
        Integer id = 3;

        User user = userDao.getUserByEmail(email);
        assertNotNull("Return value (USER) shouldn't be null!",user);
        assertEquals("Incorrect user", id, user.getId());
        assertEquals("Incorrect user", email, user.getEmail());
        assertEquals("Incorrect user", password, user.getPassword());
    }

    /** Get user by email
     * User doesn't exist in DB. */
    @Test
    public void getUserByEmailNullIfNotFoundTest() {
        User currentUser = userDao.getUserByEmail("incorrectEmail");
        assertNull(currentUser);
    }

    /** Get user by email from DB
     * Incorrect email.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void getUserByEmailIncorrectEmailTest() throws IllegalArgumentException {
        userDao.getUserByEmail(null);
    }

    /** Get list of users from DB
     * Users already exists in DB.
     * User list should be get (not null) */
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
