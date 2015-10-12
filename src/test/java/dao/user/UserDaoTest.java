package dao.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class UserDaoTest {

    @Inject
    IUserDao userDao;

    @Test
    public void createUserTest(){

        String email = "testUser@mail.com";
        String password = "12345";

        try {
            User user = userDao.createUser(email, password);

            assertNotNull("User shouldn't be null!", user);
            assertEquals(email, user.getEmail());
            assertEquals(password, user.getPassword());
            assertNotEquals(new Integer(0), user.getId());

        } catch (UserAlreadyExistException e){
            fail(String.format("User with email %s already exists. Incorrect test data.", email));
        }
    }

    /** Create user
     * User already exists in DB.
     * Expected UserAlreadyExistException exception */
    @Test(expected = UserAlreadyExistException.class)
    public void createUserExceptionTest() throws UserAlreadyExistException {

        userDao.createUser("UpdateUser@javamonkeys.com", "12345");
    }

    @Test
    public void updateUserTest(){

        String email = "testUser@mail.com";
        String password = "12345";

        try {
            User user = userDao.createUser(email, password);

            assertNotNull("User shouldn't be null!", user);
            assertEquals(email, user.getEmail());
            assertEquals(password, user.getPassword());
            assertNotEquals(new Integer(0), user.getId());

        } catch (UserAlreadyExistException e){
            fail(String.format("User with email %s already exists. Incorrect test data.", email));
        }

    }

    @Test
    public void deleteUserTest(){

    }

    @Test
    public void getUserByIdTest(){

    }

    @Test
    public void getUserByEmailTest(){

    }

    @Test
    public void getUsersTest(){

    }
}
