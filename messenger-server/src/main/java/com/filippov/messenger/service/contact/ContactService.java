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
    private IContactDao contactDao;

    @Autowired
    private IUserDao userDao;

    @Transactional
    public Contact createContact(Integer ownerId, Integer contactId, String name) {
        if (ownerId == null || contactId == null || name == null)
            return null;

        User ownerUser = userDao.getUserById(ownerId);
        User contactUser = userDao.getUserById(contactId);

        if (ownerUser != null
                && contactUser != null
                && contactDao.getContactByUsers(ownerUser, contactUser) == null) {
            Contact contact = new Contact(ownerUser, contactUser);
            contact.setContactName(name);
            return contactDao.createContact(contact);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Contact getContact(Integer id) {
        if (id == null)
            return null;

        return contactDao.getContact(id);
    }

    @Transactional(readOnly = true)
    public List<Contact> getContacts(Integer userId) {
        if (userId == null)
            return null;

        User ownerUser = userDao.getUserById(userId);
        if (ownerUser == null) {
            return null;
        } else {
            return contactDao.getContacts(ownerUser);
        }
    }

    @Transactional
    public boolean updateContact(Integer id, Contact sourceContact) {
        if (id == null || sourceContact == null)
            return false;

        Contact contact = contactDao.getContact(id);
        if (contact == null)
            return false;
        contact.setContactName(sourceContact.getContactName());
        return contactDao.updateContact(contact);
    }

    @Transactional
    public boolean deleteContact(Integer id) {
        if (id == null)
            return false;

        Contact contact = contactDao.getContact(id);
        if (contact == null)
            return false;

        contactDao.deleteContact(contact);
        return true;
    }
}
