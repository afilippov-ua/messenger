package dao.user;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao implements IUserDao {

    @Autowired
    private SessionFactory hibernateSessionFactory;

    private Session getSession() {
        return hibernateSessionFactory.getCurrentSession();
    }

    private void save(Object entity) {
        getSession().save(entity);
    }

    private void delete(Object entity) {
        getSession().delete(entity);
    }

    private void merge(Object entity) {
        getSession().merge(entity);
    }

    private void persist(Object entity) {
        getSession().persist(entity);
    }

    public List<User> getUsers() {

        Query query = getSession().createQuery("from User");

        return (List<User>)query.list();
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

    public User getUserByEmail(String email){
        Query query = getSession().createQuery("from User where email = :email");
        query.setParameter("email", email);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (User)result.get(0);
        }
    }

    public User createUser(String email, String password) throws UserAlreadyExistException{

        User newUser = new User(email, password);
        save(newUser);

        return newUser;
    }

    public void updateUser(int id, User sourceUser) throws UserNotFoundException {

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
