package api.user;

import dao.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    public ResponseEntity createUser(String email, String password);

    public ResponseEntity<List<User>> getUsers(String email);

    public ResponseEntity<User> getUser(Integer id);

    public ResponseEntity deleteUser(Integer id);

    public ResponseEntity updateUser(Integer id, User sourceUser);

}
