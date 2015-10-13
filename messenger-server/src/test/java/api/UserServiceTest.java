package api;

import dao.user.User;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static final String baseUrl = "http://localhost:8555";

    private static ResponseEntity<User[]> getUserByEmail(RestTemplate restTemplate, String email){
        return restTemplate.getForEntity(baseUrl + "/api/users/?email=" + email, User[].class);
    }

    @Test
    public void testGetUsers(){

        String email = "filippov@mail.com";
        CustomRestTemplate restTemplate = new CustomRestTemplate();

        ResponseEntity<User[]> responseEntity = getUserByEmail(restTemplate, email);
        assertNotNull(responseEntity.getBody());

    }
}
