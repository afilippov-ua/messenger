package com.filippov.messenger.dao.user;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends AbstractDao implements IUserDao {

    public User createUser(User user) {
        save(user);
        return user;
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

    public List<User> getUsersByName(String name) {
        Query query = getSession().createQuery("from User where LOWER(name) LIKE '%" + name + "%'");
        return query.list();
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
