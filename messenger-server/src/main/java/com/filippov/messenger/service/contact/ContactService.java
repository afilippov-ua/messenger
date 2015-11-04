package com.filippov.messenger.service.contact;

import com.filippov.messenger.entity.contact.Contact;
import com.filippov.messenger.dao.contact.IContactDao;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService implements IContactService {

    @Autowired
    IContactDao contactDao;

    @Autowired
    IUserDao userDao;

    @Transactional(readOnly = true)
    public List<Contact> getContacts(int userId) {

        User ownerUser = userDao.getUserById(userId);
        if (ownerUser == null) {
            return null;
        } else {
            return contactDao.getContacts(ownerUser);
        }
    }

    @Transactional(readOnly = true)
    public Contact getContact(int id) {
        return contactDao.getContact(id);
    }

    @Transactional
    public boolean deleteContact(int id) {

        Contact contact = contactDao.getContact(id);
        if (contact == null)
            return false;

        contactDao.deleteContact(contact);
        return true;
    }

    @Transactional
    public boolean addContact(int ownerId, int contactId, String name) {

        if (name == null)
            return false;

        User ownerUser = userDao.getUserById(ownerId);
        User contactUser = userDao.getUserById(contactId);

        if (ownerUser != null
                && contactUser != null
                && contactDao.getContactByUsers(ownerUser, contactUser) == null) {
            return (contactDao.createContact(ownerUser, contactUser, name) != null);
        }

        return false;
    }
}
