package com.filippov.messenger.service.contact;

import com.filippov.messenger.entity.contact.Contact;

import java.util.List;

public interface IContactService {

    public List<Contact> getContacts(int ownerId);

    public Contact getContact(int id);

    public boolean deleteContact(int id);

    public boolean addContact(int ownerId, int contactId, String name);

}
