package com.filippov.messenger.dao;

import com.filippov.messenger.entity.contact.Contact;
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

    private User testUserOwner1, testUserOwner2;
    private User testUserContact1, testUserContact2;
    private Contact testContact1, testContact2;

    @Before
    public void setup() {
        testUserOwner1 = userDao.createUser(new User("test user owner 1", "12345"));
        testUserOwner2 = userDao.createUser(new User("test user owner 2", "12345"));
        assertNotNull(testUserOwner1);
        assertNotNull(testUserOwner2);

        testUserContact1 = userDao.createUser(new User("test user contact 1", "12345"));
        testUserContact2 = userDao.createUser(new User("test user contact 2", "12345"));
        assertNotNull(testUserContact1);
        assertNotNull(testUserContact2);

        testContact1 = new Contact(testUserOwner1, testUserContact1);
        testContact1.setContactName("test contact name 1");
        testContact1 = contactDao.createContact(testContact1);
        assertNotNull(testContact1);

        testContact2 = new Contact(testUserOwner1, testUserContact2);
        testContact2.setContactName("test contact name 2");
        testContact2 = contactDao.createContact(testContact2);
        assertNotNull(testContact2);
    }

    /* Test method: "createContact()" */
    @Test
    public void createContactTest() {
        final String name = "new contact";

        User userOwner = userDao.createUser(new User("test user owner", "12345"));
        User userContact = userDao.createUser(new User("test user contact", "12345"));

        Contact contact = new Contact(userOwner, userContact);
        contact.setContactName(name);
        assertNull(contact.getId());
        contact = contactDao.createContact(contact);
        assertNotNull(contact);
        assertNotNull(contact.getId());
        assertEquals(userOwner, contact.getOwnerUser());
        assertEquals(userContact, contact.getContactUser());
        assertEquals(name, contact.getContactName());

        Contact createdContact = contactDao.getContactByUsers(
                userOwner,
                userContact);
        assertNotNull(createdContact);
        assertEquals(contact, createdContact);
    }

    /* Test method: "getContact()" */
    @Test
    public void getContactByIdTest() {
        Contact contact = contactDao.getContact(testContact1.getId());
        assertNotNull(contact);
        assertEquals(testContact1, contact);
    }

    /* Test method: "getContactByUsers()" */
    @Test
    public void getContactByUsersTest() {
        Contact contact = contactDao.getContactByUsers(
                testContact1.getOwnerUser(),
                testContact1.getContactUser());
        assertNotNull(contact);
        assertEquals(testContact1, contact);
    }

    /* Test method: "getContacts()" */
    @Test
    public void getContactsTest() {
        List<Contact> contactList = contactDao.getContacts(testUserOwner1);
        assertNotNull(contactList);
        assertEquals(2, contactList.size());
        assertTrue(contactList.contains(testContact1));
        assertTrue(contactList.contains(testContact2));
    }

    /* Test method: "updateContacts()" */
    @Test
    public void updateContactTest() {
        final String newName = "name";

        assertNotEquals(newName, testContact1.getContactName());
        assertNotEquals(testUserOwner2, testContact1.getOwnerUser());
        assertNotEquals(testUserContact2, testContact1.getContactUser());
        testContact1.setContactName(newName);
        testContact1.setOwnerUser(testUserOwner2);
        testContact1.setContactUser(testUserContact2);

        assertTrue(contactDao.updateContact(testContact1));

        Contact contact = contactDao.getContact(testContact1.getId());
        assertNotNull(contact);
        assertEquals(testUserOwner2, contact.getOwnerUser());
        assertEquals(testUserContact2, contact.getContactUser());
        assertEquals(newName, contact.getContactName());
    }

    /* Test method: "deleteContacts()" */
    @Test
    public void deleteContactTest() {
        assertTrue(contactDao.deleteContact(testContact1));
        assertNull(contactDao.getContact(testContact1.getId()));
    }
}