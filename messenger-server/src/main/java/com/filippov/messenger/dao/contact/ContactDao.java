package com.filippov.messenger.dao.contact;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.contact.Contact;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDao extends AbstractDao implements IContactDao {

    public Contact createContact(Contact contact) {
        save(contact);
        return contact;
    }

    public Contact getContact(Integer id) {
        return (Contact) getSession().get(Contact.class, id);
    }

    public Contact getContactByUsers(User ownerUser, User contactUser) {
        Query query = getSession().createQuery("from Contact " +
                "where ownerUser = :ownerUser and contactUser = :contactUser");
        query.setParameter("ownerUser", ownerUser);
        query.setParameter("contactUser", contactUser);
        return (Contact) query.uniqueResult();
    }

    public List<Contact> getContacts(User ownerUser) {
        Query query = getSession().createQuery("from Contact where ownerUser = :ownerUser");
        query.setParameter("ownerUser", ownerUser);
        return (List<Contact>) query.list();
    }

    public boolean updateContact(Contact contact) {
        persist(contact);
        return true;
    }

    public boolean deleteContact(Contact contact) {
        delete(contact);
        return true;
    }
}
