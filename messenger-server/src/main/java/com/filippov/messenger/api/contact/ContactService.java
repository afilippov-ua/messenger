package com.filippov.messenger.api.contact;

import com.filippov.messenger.entity.contact.Contact;
import com.filippov.messenger.dao.contact.ContactNotFoundException;
import com.filippov.messenger.dao.contact.IContactDao;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ContactService implements IContactService {

    @Autowired
    IContactDao contactDao;

    @Autowired
    IUserDao userDao;

    @Transactional
    @RequestMapping(
            value = "/{userId}/contacts",
            method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getContacts(@PathVariable("userId") Integer userId) {

        User ownerUser = userDao.getUserById(userId);
        if (ownerUser == null) {
             return new ResponseEntity<List<Contact>>(HttpStatus.NOT_FOUND);
        } else {
            List<Contact> contacts = contactDao.getContacts(ownerUser);
            return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
        }
    }

    @Transactional
    @RequestMapping(
            value = "/{userId}/contacts/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Contact> getContact(@PathVariable("id") Integer id) {

        Contact contact = contactDao.getContact(id);
        if (contact == null){
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Contact>(contact, HttpStatus.OK);
        }
    }

    @Transactional
    @RequestMapping(
            value = "/{ownerId}/contacts/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteContact(@PathVariable("id") Integer id) {

        try {
            contactDao.deleteContact(id);
            return  new ResponseEntity(HttpStatus.OK);
        } catch (ContactNotFoundException e) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @RequestMapping(
            value = "/{ownerId}/contacts/{contactId}",
            method = RequestMethod.POST)
    public ResponseEntity addContact(
            @PathVariable("ownerId") Integer ownerId,
            @PathVariable("contactId") Integer contactId,
            @RequestParam("name") String name) {

        User ownerUser = userDao.getUserById(ownerId);
        User contactUser = userDao.getUserById(contactId);

        if (ownerUser == null || contactUser == null) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (contactDao.getContactByUsers(ownerUser, contactUser) == null) {
            contactDao.createContact(ownerUser, contactUser, name);
        }
        return  new ResponseEntity(HttpStatus.OK);
    }
}
