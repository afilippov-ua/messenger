package com.filippov.messenger.dao;

import com.filippov.messenger.entity.contact.Contact;
import com.filippov.messenger.dao.contact.ContactNotFoundException;
import com.filippov.messenger.dao.contact.IContactDao;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
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

    /** Create contact
     * Contact should be created (not null) */
    @Test
    public void createContactTest(){

        Contact contact = contactDao.getContactByUsers(userOwner, userContact);
        assertNull("Check test data!", contact);

        contactDao.createContact(userOwner, userContact, name);

        contact = contactDao.getContactByUsers(userOwner, userContact);
        assertNotNull(contact);
        assertEquals(userOwner, contact.getOwnerUser());
        assertEquals(userContact, contact.getContactUser());
        assertEquals(name, contact.getContactName());
    }

    /** Create contact
     * Incorrect userOwner.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void createContactIncorrectUserOwnerTest() throws IllegalArgumentException {
        contactDao.createContact(null, userContact, name);
    }

    /** Create contact
     * Incorrect userContact.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void createContactIncorrectUserContactTest() throws IllegalArgumentException {
        contactDao.createContact(userOwner, null, name);
    }

    /** Create contact
     * Incorrect name.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void createContactIncorrectNameTest() throws IllegalArgumentException {
        contactDao.createContact(userOwner, userContact, null);
    }

    /** Update contact data
     * Contact already exists in DB
     * Contact should be updated */
    @Test
    public void updateContactTest(){

        int contactId = 5;
        Contact sourceContact = contactDao.getContact(contactId);
        assertNotNull("Check test data!", sourceContact);

        User newUserOwner = sourceContact.getContactUser();
        User newUserContact = sourceContact.getOwnerUser();
        String newName = sourceContact.getContactName() + "_new";

        sourceContact.setOwnerUser(newUserOwner);
        sourceContact.setContactUser(newUserContact);
        sourceContact.setContactName(newName);
        assertEquals(newUserOwner, sourceContact.getOwnerUser());
        assertEquals(newUserContact, sourceContact.getContactUser());
        assertEquals(newName, sourceContact.getContactName());

        try {
            contactDao.updateContact(contactId, sourceContact);

            sourceContact = contactDao.getContact(contactId);
            assertNotNull("Check test data!", sourceContact);
            assertEquals(newUserOwner, sourceContact.getOwnerUser());
            assertEquals(newUserContact, sourceContact.getContactUser());
            assertEquals(newName, sourceContact.getContactName());

        } catch (ContactNotFoundException e){
            fail(String.format("Contact with id %s was not found! Check the test data.", contactId));
        }
    }

    /** Update contact
     * Contact doesn't exist in DB.
     * Expected ContactNotFoundException exception */
    @Test(expected = ContactNotFoundException.class)
    public void updateContactNotFoundTest() throws ContactNotFoundException{
        Contact sourceContact = contactDao.getContact(3);
        assertNotNull("Check test data!", sourceContact);
        contactDao.updateContact(-1, sourceContact);
    }

    /** Update contact
     * Incorrect sourceContact.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void updateContactIncorrectSourceContactTest() throws ContactNotFoundException, IllegalArgumentException {
        contactDao.updateContact(3, null);
    }

    /** Delete contact from DB
     * Contact already exists in DB
     * Contact should be delete */
    @Test
    public void deleteContactTest(){

        int contactId = 6;
        assertNotNull("Check the test data", contactDao.getContact(contactId));

        try {
            contactDao.deleteContact(contactId);
            assertNull(contactDao.getContact(contactId));
        } catch (ContactNotFoundException e){
            fail(String.format("Contact with id %s was not found! Check the test data.", contactId));
        }
    }

    /** Delete contact
     * Contact doesn't exist in DB
     * Expected ContactNotFoundException exception */
    @Test(expected = ContactNotFoundException.class)
    public void deleteMessageNotFoundTest() throws ContactNotFoundException {
        contactDao.deleteContact(-1);
    }

    /** Get contact from DB
     * Contact already exists in DB.
     * Contact should be get (not null) */
    @Test
    public void getContactTest(){

        Integer contactId = 3;
        User userOwner = userDao.getUserById(3);
        User userContact = userDao.getUserById(2);
        String name = "Sirosh";

        assertNotNull("Check the test data", userOwner);
        assertNotNull("Check the test data", userContact);

        Contact contact = contactDao.getContact(contactId);
        assertNotNull(contact);
        assertEquals(contactId, contact.getId());
        assertEquals(userOwner, contact.getOwnerUser());
        assertEquals(userContact, contact.getContactUser());
        assertEquals(name, contact.getContactName());
    }

    /** Get contact
     * Contact doesn't exist in DB. Should return null */
    @Test
    public void getContactNullIfNotFoundTest() {
        Contact contact = contactDao.getContact(-1);
        assertNull(contact);
    }

    /** Get contact by users
     * Contact already exists in DB
     * Contact should be get (not null) */
    @Test
    public void getContactByUsersTest(){

        Integer contactId = 2;
        User userOwner = userDao.getUserById(3);
        User userContact = userDao.getUserById(1);
        String name = "Serdyikov";

        assertNotNull("Check the test data", userOwner);
        assertNotNull("Check the test data", userContact);

        Contact contact = contactDao.getContactByUsers(userOwner, userContact);
        assertNotNull(contact);
        assertEquals(contactId, contact.getId());
        assertEquals(userOwner, contact.getOwnerUser());
        assertEquals(userContact, contact.getContactUser());
        assertEquals(name, contact.getContactName());
    }

    /** Get contact by users
     * Contact doesn't exist in DB. Should return null */
    @Test
    public void getContactByUsersNullIfNotFoundTest() {

        User userOwner = userDao.getUserById(5);
        User userContact = userDao.getUserById(1);

        assertNotNull("Check the test data", userOwner);
        assertNotNull("Check the test data", userContact);

        Contact contact = contactDao.getContactByUsers(userOwner, userContact);
        assertNull(contact);
    }

    /** Get contact by users
     * Incorrect userOwner.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void getContactByUsersIncorrectUserOwnerTest() throws IllegalArgumentException {
        contactDao.getContactByUsers(null, userContact);
    }

    /** Get contact by users
     * Incorrect userContact.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void getContactByUsersIncorrectUserContactTest() throws IllegalArgumentException {
        contactDao.getContactByUsers(userOwner, null);
    }

    /** Get list of contact from DB
     * Contacts already exists in DB.
     * Contact list should be get (not null) */
    @Test
    public void getContactsTest(){

        User userOwner = userDao.getUserById(3);
        assertNotNull("Check the test data", userOwner);

        Contact findContact1 = contactDao.getContact(2);
        Contact findContact2 = contactDao.getContact(3);
        Contact findContact3 = contactDao.getContact(7);

        List<Contact> contactList = contactDao.getContacts(userOwner);
        assertNotNull(contactList);
        assertNotEquals(0, contactList.size());
        assertTrue(contactList.contains(findContact1));
        assertTrue(contactList.contains(findContact2));
        assertTrue(contactList.contains(findContact3));
    }
}
