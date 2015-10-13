package dao.contact;

public class ContactNotFoundException extends Exception {

    public ContactNotFoundException(String msg) {
        super(msg);
    }

    public ContactNotFoundException(){
        super("Contact not found!");
    }

}
