package com.filippov.messenger.controller.contact;

import com.filippov.messenger.entity.contact.Contact;
import com.filippov.messenger.service.contact.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/api/contacts")
public class ContactController implements IContactController {

    @Autowired
    IContactService contactService;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createContact(
            @RequestParam("ownerId") Integer ownerId,
            @RequestParam("contactId") Integer contactId,
            @RequestParam("name") String name) {
        if (contactService.createContact(ownerId, contactId, name) == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contact> getContact(@PathVariable("id") Integer id) {
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
        return new ResponseEntity<>(contactService.getContacts(ownerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateContact(@PathVariable("id") Integer id,
                                        @RequestBody Contact sourceContact) {

        if (contactService.updateContact(id, sourceContact))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteContact(@PathVariable("id") Integer id) {
        if (contactService.deleteContact(id))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
