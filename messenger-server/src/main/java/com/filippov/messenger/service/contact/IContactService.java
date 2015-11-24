package com.filippov.messenger.service.contact;

import com.filippov.messenger.entity.contact.Contact;

import java.util.List;

public interface IContactService {
    Contact createContact(Integer ownerId, Integer contactId, String name);

    Contact getContact(Integer id);

    List<Contact> getContacts(Integer ownerId);

    boolean updateContact(Integer id, Contact sourceContact);

    boolean deleteContact(Integer id);
}
