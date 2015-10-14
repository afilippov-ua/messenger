package api;

import dao.user.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static final String baseUrl = "http://localhost:8555";

    ///////////////////////////////////////// BASIC REQUESTS /////////////////////////////////////////

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

    /* "updateUser" method with "idl" & "user" parameter */
    private static ResponseEntity<String> doUpdateUser(CustomRestTemplate restTemplate, HttpEntity entity, int id){
        return restTemplate.exchange(baseUrl + "/api/users/" + id, HttpMethod.PUT, entity, String.class);
    }

    /* "deleteUser" method with "id" parameter */
    private static ResponseEntity<String> doDeleteUser(CustomRestTemplate restTemplate, HttpEntity entity, int id){
        return restTemplate.exchange(baseUrl + "/api/users/" + id, HttpMethod.DELETE, entity, String.class);
    }

    ///////////////////////////////////////// INIT DATA /////////////////////////////////////////
    @BeforeClass
    public static void init(){

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // DELETE "newUser@mail.com" if exist
        restTemplate.clearHttpHeaders();
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        User user = doGetUserByEmail(restTemplate, "newUser@mail.com");
        if (user != null)
            doDeleteUser(restTemplate, entity, user.getId());

        // CREATE user "existingUser@mail.com"
        restTemplate.addHttpHeader("email", "existingUser@mail.com");
        restTemplate.addHttpHeader("password", "12345");
        entity = new HttpEntity(restTemplate.getHttpHeaders());
        ResponseEntity<String> responseEntity = doCreateUser(restTemplate, entity);
        if (responseEntity.getStatusCode() != HttpStatus.CREATED && responseEntity.getStatusCode() != HttpStatus.BAD_REQUEST){
            fail("User \"existingUser@mail.com\" creation error");
        }

        // CREATE user "deleteUser@mail.com"
        restTemplate.clearHttpHeaders();
        restTemplate.addHttpHeader("email", "deleteUser@mail.com");
        restTemplate.addHttpHeader("password", "12345");
        entity = new HttpEntity(restTemplate.getHttpHeaders());
       responseEntity = doCreateUser(restTemplate, entity);
        if (responseEntity.getStatusCode() != HttpStatus.CREATED && responseEntity.getStatusCode() != HttpStatus.BAD_REQUEST){
            fail("User \"deleteUser@mail.com\" creation error");
        }

        // CREATE user "updateUser@mail.com"
        restTemplate.clearHttpHeaders();
        restTemplate.addHttpHeader("email", "updateUser@mail.com");
        restTemplate.addHttpHeader("password", "12345");
        entity = new HttpEntity(restTemplate.getHttpHeaders());
        responseEntity = doCreateUser(restTemplate, entity);
        if (responseEntity.getStatusCode() != HttpStatus.CREATED && responseEntity.getStatusCode() != HttpStatus.BAD_REQUEST){
            fail("User \"updateUser@mail.com\" creation error");
        }

    }

    ///////////////////////////////////////// GET USER /////////////////////////////////////////

    /** Get user by id
    * User already exists in DB
    * Should return user */
    @Test
    public void getUserTest(){

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        User testUser = doGetUserByEmail(restTemplate, "filippov@mail.com");
        assertNotNull(testUser);

        ResponseEntity<User> responseEntity = doGetUser(restTemplate, testUser.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        User currentUser = responseEntity.getBody();
        assertNotNull(currentUser);
        assertEquals(testUser.getId(), currentUser.getId());
        assertEquals(testUser.getEmail(), currentUser.getEmail());
    }

    /** Get user by id
    * Incorrect user id
    * expected HttpStatus.NOT_FOUND */
    @Test
    public void getUserIncorrectIdTest(){

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<User> responseEntity = doGetUser(restTemplate, -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    ///////////////////////////////////////// CREATE USER /////////////////////////////////////////

    /** Create new user
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
        assertNull(responseEntity.getBody());

        // CHECK update user
        User user = doGetUserByEmail(restTemplate, email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    /** Create new user
    * User already exist in DB
    * expected HttpStatus.BAD_REQUEST */
    @Test
    public void createUserAlreadyExistTest(){

        String email = "existingUser@mail.com";
        String password = "12345";

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        restTemplate.addHttpHeader("email", email);
        restTemplate.addHttpHeader("password", password);
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doCreateUser(restTemplate, entity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    ///////////////////////////////////////// DELETE USER /////////////////////////////////////////

    /** Delete user from DB
     * User already exists in DB
     * Should return HttpStatus.NO_CONTENT */
    @Test
    public void deleteUserTest(){

        String email = "deleteUser@mail.com";
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        User user = doGetUserByEmail(restTemplate, email);
        assertNotNull(user);

        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doDeleteUser(restTemplate, entity, user.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // CHECK update user
        assertNull(doGetUserByEmail(restTemplate, email));
    }

    /** Delete user from DB
     * User doesn't exist in DB
     * expected HttpStatus.NOT_FOUND */
    @Test
    public void deleteUserDoesntExistTest(){

        CustomRestTemplate restTemplate = new CustomRestTemplate();
        HttpEntity entity = new HttpEntity(restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doDeleteUser(restTemplate, entity,  -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    ///////////////////////////////////////// UPDATE USER /////////////////////////////////////////

    /** Update user
     * User already exists in DB
     * Should return HttpStatus.NO_CONTENT */
    @Test
    public void updateUserTest(){

        String email = "updateUser@mail.com";
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        // GET user for update
        User user = doGetUserByEmail(restTemplate, email);
        assertNotNull(user);

        // UPDATE current user
        String newEmail = "JohnDoe@mail.com";
        String newPassword = "54321";
        String newName = "John Doe";

        user.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setName(newName);

        HttpEntity<User> entity = new HttpEntity<User>(user, restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doUpdateUser(restTemplate, entity, user.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        // CHECK update user
        user = doGetUserByEmail(restTemplate, newEmail);
        assertNotNull(user);
        assertEquals(newEmail, user.getEmail());
        assertEquals(newName, user.getName());

    }

    /** Update user
     * User doesn't exist in DB
     * expected HttpStatus.NOT_FOUND */
    @Test
    public void updateUserDoesntExistTest(){

        CustomRestTemplate restTemplate = new CustomRestTemplate();

        User newUser = new User("newUser@mail.com", "123");
        HttpEntity<User> entity = new HttpEntity<>(newUser, restTemplate.getHttpHeaders());

        ResponseEntity<String> responseEntity = doUpdateUser(restTemplate, entity,  -1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

}
