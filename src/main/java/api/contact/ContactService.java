package api.contact;

import dao.contact.Contact;
import dao.contact.IContactDao;
import dao.user.IUserDao;
import dao.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
