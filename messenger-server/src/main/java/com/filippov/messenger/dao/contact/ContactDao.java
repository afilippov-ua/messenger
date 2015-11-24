package com.filippov.messenger.dao.contact;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.contact.Contact;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDao extends AbstractDao implements IContactDao {

    public List<Contact> getContacts(User ownerUser) {
        if (ownerUser == null)
            return null;

        Query query = getSession().createQuery("from Contact where ownerUser = :ownerUser");
        query.setParameter("ownerUser", ownerUser);
        return (List<Contact>) query.list();
    }

    public Contact getContact(Integer id) {
        if (id == null)
            return null;

        Query query = getSession().createQuery("from Contact where id = :id");
        query.setParameter("id", id);
        return (Contact) query.uniqueResult();
    }

    public Contact getContactByUsers(User ownerUser, User contactUser) {
        if (ownerUser == null || contactUser == null)
            return null;

        Query query = getSession().createQuery("from Contact " +
                "where ownerUser = :ownerUser and contactUser =:contactUser");
        query.setParameter("ownerUser", ownerUser);
        query.setParameter("contactUser", contactUser);
        return (Contact) query.uniqueResult();
    }

    public Contact createContact(User ownerUser, User contactUser, String name) {
        if (ownerUser == null || contactUser == null || name == null)
            return null;

        Contact newContact = new Contact(ownerUser, contactUser);
        newContact.setContactName(name);
        save(newContact);
        return newContact;
    }

    public boolean updateContact(Contact contact) {
        if (contact == null)
            return false;

        persist(contact);
        return true;
    }

    public boolean deleteContact(Contact contact) {
        if (contact == null)
            return false;

        delete(contact);
        return true;
    }
}
