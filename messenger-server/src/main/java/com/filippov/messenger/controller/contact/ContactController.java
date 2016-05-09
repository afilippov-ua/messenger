package com.filippov.messenger.controller.contact;

import com.filippov.messenger.entity.contact.Contact;
import com.filippov.messenger.service.contact.IContactService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/api/contacts")
public class ContactController implements IContactController {

    @Autowired
    private IContactService contactService;

    private static Logger logger = Logger.getLogger(ContactController.class.getName());

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createContact(
            @RequestHeader("ownerId") Integer ownerId,
            @RequestHeader("contactId") Integer contactId,
            @RequestHeader("name") String name) {
        logger.trace(String.format("/api/contacts (POST) - method \"createContact\", ownerId: \"%d\", contactId: \"%d\", name: \"%s\"", ownerId, contactId, name));
        try {
            name = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (contactService.createContact(ownerId, contactId, name) == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contact> getContact(@PathVariable("id") Integer id) {
        logger.trace(String.format("/api/contacts/%d (GET) - method \"getContact\"", id));
        Contact contact = contactService.getContact(id);
        if (contact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(contact, HttpStatus.OK);
        }
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getContacts(@RequestParam("ownerId") Integer ownerId) {
        logger.trace(String.format("/api/contacts (GET) - method \"getContacts\", ownerId: \"%d\"", ownerId));
        return new ResponseEntity<>(contactService.getContacts(ownerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateContact(@PathVariable("id") Integer id,
                                        @RequestBody Contact sourceContact) {
        logger.trace(String.format("/api/contacts/%d (PUT) - method \"updateContact\", sourceContact: %s", id, sourceContact));
        if (contactService.updateContact(id, sourceContact))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteContact(@PathVariable("id") Integer id) {
        logger.trace(String.format("/api/contacts/%d (DELETE) - method \"deleteContact\"", id));
        if (contactService.deleteContact(id))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
