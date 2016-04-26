package com.filippov.messenger.service;

import com.filippov.messenger.dao.contact.IContactDao;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.contact.Contact;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.service.contact.ContactService;
import com.filippov.messenger.service.contact.IContactService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
@ContextConfiguration(locations = "classpath*:test-service-config.xml")
@Transactional
public class ContactServiceTest {

    @TestSubject
    private IContactService contactService = new ContactService();

    @Mock(fieldName = "userDao")
    private IUserDao mockUserDao;

    @Mock(fieldName = "contactDao")
    private IContactDao mockContactDao;

    private User testUser1, testUser2, testUser3;
    private Contact testContact1, testContact2, testContact3;

    @Before
    public void setup() {
        testUser1 = new User("user 1", "12345");
        testUser1.setId(1);
        testUser1.setName("user name 1");

        testUser2 = new User("user 2", "12345");
        testUser2.setId(2);
        testUser2.setName("user name 2");

        testUser3 = new User("user 3", "12345");
        testUser3.setId(3);
        testUser3.setName("user name 3");

        testContact1 = new Contact(testUser1, testUser2);
        testContact1.setId(1);
        testContact1.setContactName("contact 1-2");

        testContact2 = new Contact(testUser2, testUser1);
        testContact2.setId(2);
        testContact2.setContactName("contact 2-1");

        testContact3 = new Contact(testUser1, testUser3);
        testContact3.setId(3);
        testContact3.setContactName("contact 1-3");
    }

    /* Test method: "createContact()" */
    @Test
    public void createContactTest() {
        Contact contact = new Contact(testUser3, testUser2);
        contact.setContactName("new contact name");
        expect(mockUserDao.getUserById(testUser3.getId())).andReturn(testUser3).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(testUser2).once();
        expect(mockContactDao.getContactByUsers(testUser3, testUser2)).andReturn(null).once();
        expect(mockContactDao.createContact(contact)).andReturn(contact).once();
        replay(mockUserDao);
        replay(mockContactDao);

        assertEquals(contact, contactService.createContact(
                testUser3.getId(),
                testUser2.getId(),
                contact.getContactName()));
        verify(mockUserDao);
        verify(mockContactDao);
    }

    /* Test method: "createContact()"
    * Contact already exists */
    @Test
    public void createContactContactAlreadyExistsTest() {
        Contact contact = new Contact(testUser3, testUser2);
        contact.setContactName("new contact name");
        expect(mockUserDao.getUserById(testUser3.getId())).andReturn(testUser3).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(testUser2).once();
        expect(mockContactDao.getContactByUsers(testUser3, testUser2)).andReturn(contact).once();
        replay(mockUserDao);
        replay(mockContactDao);

        assertNull(contactService.createContact(
                testUser3.getId(),
                testUser2.getId(),
                contact.getContactName()));
        verify(mockUserDao);
        verify(mockContactDao);
    }

    /* Test method: "createContact()"
    * User owner not found */
    @Test
    public void createContactUserOwnerNotFoundTest() {
        expect(mockUserDao.getUserById(testUser3.getId())).andReturn(null).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(testUser2).once();
        replay(mockUserDao);
        replay(mockContactDao);

        assertNull(contactService.createContact(
                testUser3.getId(),
                testUser2.getId(),
                "new contact name"));
        verify(mockUserDao);
        verify(mockContactDao);
    }

    /* Test method: "createContact()"
    * User contact not found */
    @Test
    public void createContactUserContactNotFoundTest() {
        expect(mockUserDao.getUserById(testUser3.getId())).andReturn(testUser3).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockContactDao);

        assertNull(contactService.createContact(
                testUser3.getId(),
                testUser2.getId(),
                "new contact name"));
        verify(mockUserDao);
        verify(mockContactDao);
    }

    /* Test method: "createContact()"
    * Illegal arguments */
    @Test
    public void createContactIllegalArgumentsTest() {
        replay(mockUserDao);
        replay(mockContactDao);

        assertNull(contactService.createContact(null, testUser2.getId(), "new contact name"));
        verify(mockUserDao);
        verify(mockContactDao);

        reset(mockUserDao);
        reset(mockContactDao);
        replay(mockUserDao);
        replay(mockContactDao);

        assertNull(contactService.createContact(testUser3.getId(), null, "new contact name"));
        verify(mockUserDao);
        verify(mockContactDao);

        reset(mockUserDao);
        reset(mockContactDao);
        replay(mockUserDao);
        replay(mockContactDao);

        assertNull(contactService.createContact(testUser3.getId(), testUser2.getId(), null));
        verify(mockUserDao);
        verify(mockContactDao);
    }

    /* Test method: "getContact()" */
    @Test
    public void getContactTest() {
        expect(mockContactDao.getContact(testContact1.getId())).andReturn(testContact1).once();
        replay(mockContactDao);

        assertEquals(testContact1, contactService.getContact(testContact1.getId()));
        verify(mockContactDao);
    }

    /* Test method: "getContact()"
    * Contact not found */
    @Test
    public void getContactContactNotFoundTest() {
        expect(mockContactDao.getContact(testContact1.getId())).andReturn(null).once();
        replay(mockContactDao);

        assertNull(contactService.getContact(testContact1.getId()));
        verify(mockContactDao);
    }

    /* Test method: "getContact()"
    * Illegal arguments */
    @Test
    public void getContactIllegalArgumentsTest() {
        replay(mockContactDao);

        assertNull(contactService.getContact(null));
        verify(mockContactDao);
    }

    /* Test method: "getContacts()" */
    @Test
    public void getContactsTest() {
        List<Contact> list = new ArrayList<>(3);
        list.add(testContact1);
        list.add(testContact2);
        list.add(testContact3);
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockContactDao.getContacts(testUser1)).andReturn(list).once();
        replay(mockUserDao);
        replay(mockContactDao);

        list = contactService.getContacts(testUser1.getId());
        verify(mockUserDao);
        verify(mockContactDao);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertTrue(list.contains(testContact1));
        assertTrue(list.contains(testContact2));
        assertTrue(list.contains(testContact3));
    }

    /* Test method: "getContacts()"
    * User not found */
    @Test
    public void getContactsUserNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockContactDao);

        assertNull(contactService.getContacts(testUser1.getId()));
        verify(mockUserDao);
        verify(mockContactDao);
    }

    /* Test method: "getContacts()"
    * Illegal arguments */
    @Test
    public void getContactsIllegalArgumentsTest() {
        replay(mockUserDao);
        replay(mockContactDao);

        assertNull(contactService.getContacts(null));
        verify(mockUserDao);
        verify(mockContactDao);
    }

    /* Test method: "updateContact()" */
    @Test
    public void updateContactTest() {
        expect(mockContactDao.getContact(testContact1.getId())).andReturn(testContact1).once();
        expect(mockContactDao.updateContact(testContact1)).andReturn(true).once();
        replay(mockContactDao);

        assertTrue(contactService.updateContact(testContact1.getId(), testContact1));
        verify(mockContactDao);
    }

    /* Test method: "updateContact()"
    * Contact not found */
    @Test
    public void updateContactContactNotFoundTest() {
        expect(mockContactDao.getContact(testContact1.getId())).andReturn(null).once();
        replay(mockContactDao);

        assertFalse(contactService.updateContact(testContact1.getId(), testContact1));
        verify(mockContactDao);
    }

    /* Test method: "updateContact()"
    * Illegal arguments */
    @Test
    public void updateContactIllegalArgumentsTest() {
        replay(mockContactDao);

        assertFalse(contactService.updateContact(null, testContact1));
        verify(mockContactDao);

        reset(mockContactDao);
        replay(mockContactDao);

        assertFalse(contactService.updateContact(testContact1.getId(), null));
        verify(mockContactDao);
    }

    /* Test method: "deleteContact()" */
    @Test
    public void deleteContactTest() {
        expect(mockContactDao.getContact(testContact1.getId())).andReturn(testContact1).once();
        expect(mockContactDao.deleteContact(testContact1)).andReturn(true).once();
        replay(mockContactDao);

        assertTrue(contactService.deleteContact(testContact1.getId()));
        verify(mockContactDao);
    }

    /* Test method: "deleteContact()"
    * Contact not found */
    @Test
    public void deleteContactContactNotFoundTest() {
        expect(mockContactDao.getContact(testContact1.getId())).andReturn(null).once();
        replay(mockContactDao);

        assertFalse(contactService.deleteContact(testContact1.getId()));
        verify(mockContactDao);
    }

    /* Test method: "deleteContact()"
    * Illegal arguments */
    @Test
    public void deleteContactIllegalArgumentsTest() {
        replay(mockContactDao);

        assertFalse(contactService.deleteContact(null));
        verify(mockContactDao);
    }
}
