package com.filippov.messenger.controller.contact;

import com.filippov.messenger.entity.contact.Contact;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.service.contact.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController implements IContactController {

    @Autowired
    IContactService contactService;

    @Transactional
    @RequestMapping(
            value = "/{ownerId}/contacts/{contactId}",
            method = RequestMethod.POST)
    public ResponseEntity createContact(
            @PathVariable("ownerId") Integer ownerId,
            @PathVariable("contactId") Integer contactId,
            @RequestParam("name") String name) {
        if (contactService.createContact(ownerId, contactId, name) == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(
            value = "/{userId}/contacts/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Contact> getContact(@PathVariable("id") Integer id) {
        Contact contact = contactService.getContact(id);
        if (contact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(contact, HttpStatus.OK);
        }
    }

    @Transactional
    @RequestMapping(
            value = "/{userId}/contacts",
            method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getContacts(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(contactService.getContacts(userId), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity updateContact(@PathVariable("id") Integer id,
                                     @RequestBody Contact sourceContact) {

        if (contactService.updateContact(id, sourceContact))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @RequestMapping(
            value = "/{ownerId}/contacts/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteContact(@PathVariable("id") Integer id) {
        if (contactService.deleteContact(id))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
