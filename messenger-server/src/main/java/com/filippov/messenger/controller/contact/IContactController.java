package com.filippov.messenger.controller.contact;

import com.filippov.messenger.entity.contact.Contact;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IContactController {

    public ResponseEntity<List<Contact>> getContacts(Integer ownerId);

    public ResponseEntity<Contact> getContact(Integer id);

    public ResponseEntity deleteContact(Integer id);

    public ResponseEntity addContact(Integer ownerId, Integer contactId, String name);

}
