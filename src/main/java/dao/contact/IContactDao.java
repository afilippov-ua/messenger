package dao.contact;

import dao.user.User;

import java.util.List;

public interface IContactDao {

    public List<Contact> getContacts(User ownerUser);

    public Contact getContact(int id);

    public Contact createContact(User ownerUser, User contactUser);

    public void updateContact(int id, Contact sourceMessage) throws ContactNotFoundException;

    public void deleteContact(int id) throws ContactNotFoundException;
}
