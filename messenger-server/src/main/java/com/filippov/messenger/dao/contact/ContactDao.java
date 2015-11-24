package com.filippov.messenger.dao.contact;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.contact.Contact;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDao extends AbstractDao implements IContactDao {

    public Contact createContact(User ownerUser, User contactUser, String name) {
        Contact newContact = new Contact(ownerUser, contactUser);
        newContact.setContactName(name);
        save(newContact);
        return newContact;
    }

    public Contact getContact(Integer id) {
        Query query = getSession().createQuery("from Contact where id = :id");
        query.setParameter("id", id);
        return (Contact) query.uniqueResult();
    }

    public Contact getContactByUsers(User ownerUser, User contactUser) {
        Query query = getSession().createQuery("from Contact " +
                "where ownerUser = :ownerUser and contactUser =:contactUser");
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
