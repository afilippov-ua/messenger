package com.filippov.messenger.dao.user;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends AbstractDao implements IUserDao {

    public List<User> getUsers() {

        return (List<User>)getSession().createQuery("from User").list();
    }

    public User getUserById(int id) {

        Query query = getSession().createQuery("from User where id = :id");
        query.setParameter("id", id);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (User)result.get(0);
        }
    }

    public User getUserByEmail(String email) throws IllegalArgumentException{

        if (email == null)
            throw new IllegalArgumentException("argument \"email\" is not valid");

        Query query = getSession().createQuery("from User where LOWER(email) = :email");
        query.setParameter("email", email.toLowerCase());

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (User)result.get(0);
        }
    }

    public User createUser(String email,
                           String password) throws UserAlreadyExistException, IllegalArgumentException {

        if (email == null)
            throw new IllegalArgumentException("argument \"email\" is not valid");

        if (password == null)
            throw new IllegalArgumentException("argument \"password\" is not valid");

        if (getUserByEmail(email) != null)
            throw new UserAlreadyExistException("User with email" + email + " already exist");

        User newUser = new User(email, password);
        save(newUser);

        return newUser;
    }

    public void updateUser(int id,
                           User sourceUser) throws UserNotFoundException, IllegalArgumentException {

        if (sourceUser == null)
            throw new IllegalArgumentException("argument \"sourceUser\" is not valid");

        User currentUser = getUserById(id);
        if (currentUser == null)
            throw new UserNotFoundException("User with id" + id + " was not found");

        currentUser.loadValues(sourceUser);

        persist(currentUser);
    }

    public void deleteUser(int id) throws UserNotFoundException {

        User currentUser = getUserById(id);
        if (currentUser == null)
            throw new UserNotFoundException("User with id " + id + " was not found");

        delete(currentUser);
    }
}
