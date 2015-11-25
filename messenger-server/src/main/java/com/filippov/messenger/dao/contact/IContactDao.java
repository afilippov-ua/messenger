package com.filippov.messenger.dao.contact;

import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.contact.Contact;

import java.util.List;

public interface IContactDao {
    Contact createContact(Contact contact);

    Contact getContact(Integer id);

    Contact getContactByUsers(User ownerUser, User contactUser);

    List<Contact> getContacts(User ownerUser);

    boolean updateContact(Contact contact);

    boolean deleteContact(Contact contact);
}
