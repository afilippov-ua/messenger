package dao.message;

public class MessageNotFoundException extends Exception {

    public MessageNotFoundException(String msg) {
        super(msg);
    }

    public MessageNotFoundException(){
        super("Message not found!");
    }

}
