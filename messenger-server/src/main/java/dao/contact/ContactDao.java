package dao.contact;

import dao.message.Message;
import dao.user.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDao implements IContactDao {

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

    public List<Contact> getContacts(User ownerUser) {

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

    public Contact getContactByUsers(User ownerUser, User contactUser) {

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

    public Contact createContact(User ownerUser, User contactUser, String name) {

        Contact newContact = new Contact(ownerUser, contactUser);
        newContact.setContactName(name);
        save(newContact);

        return newContact;
    }

    public void updateContact(int id, Contact sourceContact) throws ContactNotFoundException {

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
