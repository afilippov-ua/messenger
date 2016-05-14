package com.filippov.messenger.controller.contact;

import com.filippov.messenger.entity.contact.Contact;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IContactController {
    ResponseEntity<Integer> createContact(Integer ownerId, Integer contactId, String name);

    ResponseEntity<Contact> getContact(Integer id);

    ResponseEntity<List<Contact>> getContacts(Integer ownerId);

    ResponseEntity updateContact(Integer id, Contact contact);

    ResponseEntity deleteContact(Integer id);
}
