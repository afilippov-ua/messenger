package com.filippov.messenger.dao.user;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends AbstractDao implements IUserDao {

    public User createUser(String email, String password) {
        if (getUserByEmail(email) != null)
            return null;

        User newUser = new User(email, password);
        save(newUser);
        return newUser;
    }

    public User getUserById(Integer id) {
        return (User) getSession().get(User.class, id);
    }

    public User getUserByEmail(String email) {
        Query query = getSession().createQuery("from User where LOWER(email) = :email");
        query.setParameter("email", email.toLowerCase());
        return (User) query.uniqueResult();
    }

    public List<User> getUsers() {
        return (List<User>) getSession().createQuery("from User").list();
    }

    public boolean updateUser(User user) {
        persist(user);
        return true;
    }

    public boolean deleteUser(User sourceUser) {
        delete(sourceUser);
        return true;
    }
}
