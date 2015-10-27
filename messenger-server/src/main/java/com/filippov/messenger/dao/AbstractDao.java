package com.filippov.messenger.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDao {

    @Autowired
    private SessionFactory hibernateSessionFactory;

    protected Session getSession() {
        return hibernateSessionFactory.getCurrentSession();
    }

    protected void save(Object entity) {
        getSession().save(entity);
    }

    protected void delete(Object entity) {
        getSession().delete(entity);
    }

    protected void merge(Object entity) {
        getSession().merge(entity);
    }

    protected void persist(Object entity) {
        getSession().persist(entity);
    }

}
