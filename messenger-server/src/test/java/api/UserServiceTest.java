package api;

import dao.user.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.*;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static final String baseUrl = "http://localhost:8555";
    private static User testUser;

    /* "getUsers" method with "email" parameter */
    private static User doGetUserByEmail(CustomRestTemplate restTemplate, String email){
        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl + "/api/users/?email=" + email, User[].class);
        User[] arrUser = response.getBody();
        if(arrUser != null && arrUser.length == 1 && arrUser[0] != null) {
            return arrUser[0];
        } else {
            return null;
        }
    }

    /* "getUser" method with "id" parameter */
    private static ResponseEntity<User> doGetUser(CustomRestTemplate restTemplate, int id){
        return restTemplate.getForEntity(baseUrl + "/api/users/" + id, User.class);
    }

    /* "createUser" method with "email" & "password" parameter */
    private static ResponseEntity<String> doCreateUser(CustomRestTemplate restTemplate, HttpEntity entity){
        return restTemplate.exchange(baseUrl + "/api/users", HttpMethod.POST, entity, String.class);
    }

    /* "deleteUser" method with "id" parameter */
    private static ResponseEntity<String> doDeleteUser(CustomRestTemplate restTemplate, HttpEntity entity, Integer id){
        return restTemplate.exchange(baseUrl + "/api/users", HttpMethod.POST, entity, String.class);
    }

    @BeforeClass
    public static void init(){

        // FIND user "filippov@mail.com"
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        testUser = doGetUserByEmail(restTemplate, "filippov@mail.com");
        assertNotNull(testUser);

        // DELETE "newUser@mail.com" if exist
        restTemplate.clearHttpHeaders();
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        User user = doGetUserByEmail(restTemplate, "newUser@mail.com");
        if (user != null)
            doDeleteUser(restTemplate, entity, user.getId());

        // CREATE user "existUser@mail.com"
        restTemplate.addHttpHeader("email", "existUser@mail.com");
        restTemplate.addHttpHeader("password", "12345");
        entity = new HttpEntity(restTemplate.getHttpHeaders());
        ResponseEntity<String> responseEntity = doCreateUser(restTemplate, entity);
        if (responseEntity.getStatusCode() != HttpStatus.CREATED || responseEntity.getStatusCode() != HttpStatus.BAD_REQUEST){
            fail("User \"existUser@mail.com\" creation error");
        }
    }

    /* Get user by id
    * User already exists in DB
    * Should return user */
    @Test
    public void getUserTest(){

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<User> responseEntity = doGetUser(restTemplate, testUser.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        User currentUser = responseEntity.getBody();
        assertNotNull(currentUser);
        assertEquals(testUser.getId(), currentUser.getId());
        assertEquals(testUser.getEmail(), currentUser.getEmail());
        assertEquals(testUser.getPassword(), currentUser.getPassword());
    }

    /* Get user by id
    * Incorrect user id
    * expected HttpStatus.NOT_FOUND */
    @Test
    public void getUserIncorrectIdTest(){

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<User> responseEntity = doGetUser(restTemplate, -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /* Create new user
    * User doesn't exist in DB
    * Should return HttpStatus.CREATED */
    @Test
    public void createUserTest(){

        String email = "newUser@mail.com";
        String password = "12345";

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addHttpHeader("email", email);
        restTemplate.addHttpHeader("password", password);
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doCreateUser(restTemplate, entity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    /* Create new user
    * User already exist in DB
    * expected HttpStatus.BAD_REQUEST */
    @Test
    public void createUserAlreadyExistTest(){

        String email = "existUser@mail.com";
        String password = "12345";

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addHttpHeader("email", email);
        restTemplate.addHttpHeader("password", password);
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doCreateUser(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
