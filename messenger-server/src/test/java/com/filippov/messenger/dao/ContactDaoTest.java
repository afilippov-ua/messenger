package com.filippov.messenger.dao;

import com.filippov.messenger.entity.contact.Contact;
import com.filippov.messenger.dao.contact.IContactDao;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.hibernate.PersistentObjectException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class ContactDaoTest {

    @Autowired
    IContactDao contactDao;

    @Autowired
    IUserDao userDao;

    public User userOwner;
    public User userContact;
    public String name = "new contact";

    @Before
    public void beforeTests() {
        userOwner = userDao.getUserById(1);
        userContact = userDao.getUserById(2);
    }

    /* Create contact
     * Expected not null */
    @Test
    public void createContactTest() {

        Contact contact = contactDao.getContactByUsers(userOwner, userContact);
        assertNull(contact);

        contactDao.createContact(userOwner, userContact, name);

        contact = contactDao.getContactByUsers(userOwner, userContact);
        assertNotNull(contact);
        assertEquals(userOwner, contact.getOwnerUser());
        assertEquals(userContact, contact.getContactUser());
        assertEquals(name, contact.getContactName());
    }

    /* Create contact
     * Illegal arguments
     * Expected null */
    @Test
    public void createContactIllegalArgumentsTest() {

        assertNull(contactDao.createContact(null, userContact, name));
        assertNull(contactDao.createContact(userOwner, null, name));
        assertNull(contactDao.createContact(userOwner, userContact, null));

        assertNull(contactDao.createContact(null, null, name));
        assertNull(contactDao.createContact(null, userContact, null));
        assertNull(contactDao.createContact(userOwner, null, null));

        assertNull(contactDao.createContact(null, null, null));
    }

    /* Update contact data
     * Contact already exists in DB
     * Contact should be updated */
    @Test
    public void updateContactTest() {

        int contactId = 5;
        Contact sourceContact = contactDao.getContact(contactId);
        assertNotNull(sourceContact);

        User newUserOwner = sourceContact.getContactUser();
        User newUserContact = sourceContact.getOwnerUser();
        String newName = sourceContact.getContactName() + "_new";

        sourceContact.setOwnerUser(newUserOwner);
        sourceContact.setContactUser(newUserContact);
        sourceContact.setContactName(newName);
        assertEquals(newUserOwner, sourceContact.getOwnerUser());
        assertEquals(newUserContact, sourceContact.getContactUser());
        assertEquals(newName, sourceContact.getContactName());

        assertTrue(contactDao.updateContact(sourceContact));

        sourceContact = contactDao.getContact(contactId);
        assertNotNull(sourceContact);
        assertEquals(newUserOwner, sourceContact.getOwnerUser());
        assertEquals(newUserContact, sourceContact.getContactUser());
        assertEquals(newName, sourceContact.getContactName());
    }

    /* Update contact
     * Contact doesn't exist in DB.
     * Expected PersistentObjectException */
    @Test(expected = PersistentObjectException.class)
    public void updateContactNotFoundTest() {
        Contact sourceContact = new Contact();
        sourceContact.setId(-1);
        contactDao.updateContact(sourceContact);
    }

    /* Update contact
     * Illegal arguments.
     * Expected false */
    @Test
    public void updateContactIllegalArgumentsTest() {
        assertFalse(contactDao.updateContact(null));
    }

    /* Delete contact from DB
     * Contact already exists in DB
     * Expected true */
    @Test
    public void deleteContactTest() {

        int contactId = 6;
        Contact contact = contactDao.getContact(contactId);
        assertNotNull(contact);
        assertTrue(contactDao.deleteContact(contact));
        assertNull(contactDao.getContact(contactId));
    }

    /* Delete contact
     * Contact doesn't exist in DB
     * Expected false */
    @Test
    public void deleteContactNotFoundTest() {
        Contact contact = new Contact();
        contact.setId(-1);
        assertTrue(contactDao.deleteContact(contact));
    }

    /* Delete contact
     * Illegal arguments.
     * Expected false */
    @Test
    public void deleteContactIllegalArgumentsTest() {
        assertFalse(contactDao.deleteContact(null));
    }

    /* Get contact from DB
     * Contact already exists in DB.
     * Expected not null */
    @Test
    public void getContactTest() {

        Integer contactId = 3;
        User userOwner = userDao.getUserById(3);
        User userContact = userDao.getUserById(2);
        String name = "Sirosh";

        assertNotNull(userOwner);
        assertNotNull(userContact);

        Contact contact = contactDao.getContact(contactId);
        assertNotNull(contact);
        assertEquals(contactId, contact.getId());
        assertEquals(userOwner, contact.getOwnerUser());
        assertEquals(userContact, contact.getContactUser());
        assertEquals(name, contact.getContactName());
    }

    /* Get contact
     * Contact doesn't exist in DB.
     * Expected null */
    @Test
    public void getContactNotFoundTest() {
        assertNull(contactDao.getContact(-1));
    }

    /* Get contact by users
     * Contact already exists in DB
     * Expected not null */
    @Test
    public void getContactByUsersTest() {

        Integer contactId = 2;
        User userOwner = userDao.getUserById(3);
        User userContact = userDao.getUserById(1);
        String name = "Serdyikov";

        assertNotNull(userOwner);
        assertNotNull(userContact);

        Contact contact = contactDao.getContactByUsers(userOwner, userContact);
        assertNotNull(contact);
        assertEquals(contactId, contact.getId());
        assertEquals(userOwner, contact.getOwnerUser());
        assertEquals(userContact, contact.getContactUser());
        assertEquals(name, contact.getContactName());
    }

    /* Get contact by users
     * Contact doesn't exist in DB.
     * Expected null */
    @Test
    public void getContactByUsersNotFoundTest() {

        User userOwner = userDao.getUserById(5);
        User userContact = userDao.getUserById(1);

        assertNotNull(userOwner);
        assertNotNull(userContact);

        assertNull(contactDao.getContactByUsers(userOwner, userContact));
    }

    /* Get contact by users
     * Illegal arguments.
     * Expected null */
    @Test
    public void getContactByUsersIllegalArgumentsTest() {
        assertNull(contactDao.getContactByUsers(null, userContact));
        assertNull(contactDao.getContactByUsers(userOwner, null));
        assertNull(contactDao.getContactByUsers(null, null));
    }

    /* Get list of contact from DB
     * Contacts already exists in DB.
     * Expected not null */
    @Test
    public void getContactsTest() {

        User userOwner = userDao.getUserById(3);
        assertNotNull(userOwner);

        Contact findContact1 = contactDao.getContact(2);
        Contact findContact2 = contactDao.getContact(3);
        Contact findContact3 = contactDao.getContact(7);
        assertNotNull(findContact1);
        assertNotNull(findContact2);
        assertNotNull(findContact3);

        List<Contact> contactList = contactDao.getContacts(userOwner);
        assertNotNull(contactList);
        assertNotEquals(0, contactList.size());
        assertTrue(contactList.contains(findContact1));
        assertTrue(contactList.contains(findContact2));
        assertTrue(contactList.contains(findContact3));
    }

    /* Get list of contact from DB
     * Contacts not found
     * Expected empty list */
    @Test
    public void getContactsNotFoundTest() {
        User userOwner = new User();
        userOwner.setId(-1);
        List<Contact> contactList = contactDao.getContacts(userOwner);
        assertNotNull(contactList);
        assertEquals(contactList.size(), 0);
    }

    /* Get list of contact from DB
     * Illegal arguments.
     * Expected null */
    @Test
    public void getContactsIllegalArgumentsTest() {
        assertNull(contactDao.getContacts(null));
    }
}
