package com.filippov.messenger.dao.contact;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.contact.Contact;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDao extends AbstractDao implements IContactDao {

    public List<Contact> getContacts(User ownerUser) throws IllegalArgumentException {

        if (ownerUser == null)
            throw new IllegalArgumentException("argument \"ownerUser\" is not valid");

        Query query = getSession().createQuery("from Contact where ownerUser = :ownerUser");
        query.setParameter("ownerUser", ownerUser);

        return (List<Contact>) query.list();
    }

    public Contact getContact(int id) {

        Query query = getSession().createQuery("from Contact where id = :id");
        query.setParameter("id", id);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (Contact)result.get(0);
        }
    }

    public Contact getContactByUsers(User ownerUser, User contactUser) throws IllegalArgumentException {

        if (ownerUser == null)
            throw new IllegalArgumentException("argument \"ownerUser\" is not valid");

        if (contactUser == null)
            throw new IllegalArgumentException("argument \"contactUser\" is not valid");

        Query query = getSession().createQuery("from Contact " +
                "where ownerUser = :ownerUser and contactUser =:contactUser");
        query.setParameter("ownerUser", ownerUser);
        query.setParameter("contactUser", contactUser);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (Contact)result.get(0);
        }
    }

    public Contact createContact(User ownerUser, User contactUser, String name) throws IllegalArgumentException {

        if (ownerUser == null)
            throw new IllegalArgumentException("argument \"ownerUser\" is not valid");

        if (contactUser == null)
            throw new IllegalArgumentException("argument \"contactUser\" is not valid");

        if (name == null)
            throw new IllegalArgumentException("argument \"name\" is not valid");

        Contact newContact = new Contact(ownerUser, contactUser);
        newContact.setContactName(name);
        save(newContact);

        return newContact;
    }

    public void updateContact(int id, Contact sourceContact) throws ContactNotFoundException, IllegalArgumentException {

        if (sourceContact == null)
            throw new IllegalArgumentException("argument \"name\" is not valid");

        Contact currentContact = getContact(id);
        if (currentContact == null)
            throw new ContactNotFoundException("Contact with id" + id + " was not found");

        currentContact.loadValues(sourceContact);

        persist(currentContact);
    }

    public void deleteContact(int id) throws ContactNotFoundException {

        Contact currentContact = getContact(id);
        if (currentContact == null)
            throw new ContactNotFoundException("Contact with id " + id + " was not found");

        delete(currentContact);
    }
}