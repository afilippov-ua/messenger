package com.filippov.messenger.entity.contact;

import com.filippov.messenger.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Contacts")
public class Contact implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    User ownerUser;

    @ManyToOne
    @JoinColumn(name = "contactId")
    User contactUser;

    @Column(name = "contactName")
    private String contactName;

    public Contact(User ownerUser, User contactUser){
        setOwnerUser(ownerUser);
        setContactUser(contactUser);
    }

    public Contact() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public User getContactUser() {
        return contactUser;
    }

    public void setContactUser(User contactUser) {
        this.contactUser = contactUser;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String text) {
        this.contactName = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != null ? !id.equals(contact.id) : contact.id != null) return false;
        if (ownerUser != null ? !ownerUser.equals(contact.ownerUser) : contact.ownerUser != null) return false;
        if (contactUser != null ? !contactUser.equals(contact.contactUser) : contact.contactUser != null) return false;
        return !(contactName != null ? !contactName.equals(contact.contactName) : contact.contactName != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", ownerUser.Id=" + ownerUser.getId() +
                ", contactUser.Id=" + contactUser.getId() +
                ", contactName='" + contactName + '\'' +
                '}';
    }
}
