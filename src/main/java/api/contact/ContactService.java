package api.contact;

import dao.contact.Contact;
import dao.contact.ContactNotFoundException;
import dao.contact.IContactDao;
import dao.user.IUserDao;
import dao.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ContactService implements IContactService {

    @Inject
    IContactDao contactDao;

    @Inject
    IUserDao userDao;

    @Transactional
    @RequestMapping(value = "/{userId}/contacts", method = RequestMethod.GET)
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
    @RequestMapping(value = "/{userId}/contacts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contact> getContact(@PathVariable("id") Integer id) {

        Contact contact = contactDao.getContact(id);
        if (contact == null){
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Contact>(contact, HttpStatus.OK);
        }

    }

    @Transactional
    @RequestMapping(value = "/{ownerId}/contacts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteContact(@PathVariable("id") Integer id) {

        try {
            contactDao.deleteContact(id);
            return  new ResponseEntity(HttpStatus.OK);
        } catch (ContactNotFoundException e) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @Transactional
    @RequestMapping(value = "/{ownerId}/contacts/{contactId}", method = RequestMethod.POST)
    public ResponseEntity addContact(@PathVariable("ownerId") Integer ownerId, @PathVariable("contactId") Integer contactId, @RequestParam("name") String name) {

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
