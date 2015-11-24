package com.filippov.messenger.dao.contact;

import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.contact.Contact;

import java.util.List;

public interface IContactDao {
    Contact createContact(User ownerUser, User contactUser, String name);

    Contact getContact(Integer id);

    List<Contact> getContacts(User ownerUser);

    Contact getContactByUsers(User ownerUser, User contactUser);

    boolean updateContact(Contact contact);

    boolean deleteContact(Contact contact);
}
