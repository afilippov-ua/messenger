package com.filippov.messenger.dao.contact;

import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.contact.Contact;

import java.util.List;

public interface IContactDao {

    public List<Contact> getContacts(User ownerUser);

    public Contact getContact(int id);

    public Contact getContactByUsers(User ownerUser, User contactUser);

    public Contact createContact(User ownerUser, User contactUser, String name);

    public boolean updateContact(Contact contact);

    public boolean deleteContact(Contact contact);
}
