package dao.contact;

import dao.user.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "Contacts")
public class Contact {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "owner_id")
    User ownerUser;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "contact_id")
    User contactUser;

    @Column(name = "contact_name")
    private String contactName;

    // default constructor
    public Contact(){};

    public Contact(User ownerUser, User contactUser){
        setOwnerUser(ownerUser);
        setContactUser(contactUser);
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

    public void loadValues(Contact sourceContact){
        setOwnerUser(sourceContact.getOwnerUser());
        setContactUser(sourceContact.getContactUser());
    }
}
