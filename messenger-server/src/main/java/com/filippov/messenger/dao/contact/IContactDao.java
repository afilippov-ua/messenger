package com.filippov.messenger.dao.contact;

import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.contact.Contact;

import java.util.List;

public interface IContactDao {

    public List<Contact> getContacts(User ownerUser) throws IllegalArgumentException;

    public Contact getContact(int id);

    public Contact getContactByUsers(User ownerUser, User contactUser) throws IllegalArgumentException;

    public Contact createContact(User ownerUser, User contactUser, String name) throws IllegalArgumentException;

    public void updateContact(int id, Contact sourceContact) throws ContactNotFoundException, IllegalArgumentException;

    public void deleteContact(int id) throws ContactNotFoundException;
}
