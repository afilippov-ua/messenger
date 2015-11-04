package com.filippov.messenger.dao.user;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import org.hibernate.Query;
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

    public User getUserByEmail(String email) {

        if (email == null)
            return null;

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
                           String password) {

        if (email == null || password == null)
            return null;

        if (getUserByEmail(email) != null)
            return null;

        User newUser = new User(email, password);
        save(newUser);

        return newUser;
    }

    public boolean updateUser(User user) {

        if (user == null)
            return false;

        persist(user);
        return true;
    }

    public boolean deleteUser(User sourceUser) {

        if (sourceUser == null)
            return false;

        delete(sourceUser);
        return true;
    }
}
