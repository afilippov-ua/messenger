package dao.user;

import java.util.List;

public interface IUserDao {

    public List<User> getUsers();

    public User getUserById(int id);

    public User getUserByEmail(String email);

    public User createUser(String email, String password) throws UserAlreadyExistException;

    public void updateUser(int id, User user) throws UserNotFoundException;

    public void deleteUser(int id) throws UserNotFoundException;

}
