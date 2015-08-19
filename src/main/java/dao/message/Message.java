package dao.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import dao.user.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Messages")
public class Message {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "message_date")
    private Date messageDate;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User userSender;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "receiver_id")
    private User userReceiver;

    @Column(name = "text")
    private String text;

    @Column(name = "seen")
    private boolean seen;

    // default constructor
    public Message(){};

    public Message(Date messageDate, User sender, User receiver, String text){

        setMessageDate(messageDate);
        setUserSender(sender);
        setUserReceiver(receiver);
        setText(text);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public User getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(User userReceiver) {
        this.userReceiver = userReceiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void loadValues(Message sourceMessage){
        setMessageDate(sourceMessage.getMessageDate());
        setUserSender(sourceMessage.getUserSender());
        setUserReceiver(sourceMessage.getUserReceiver());
        setText(sourceMessage.getText());
    }

    @JsonProperty("userSender")
    public Integer getIdUserSender(){
        return userSender.getId();
    }

    @JsonProperty("userReceiver")
    public Integer getIdUserReceiver(){
        return userReceiver.getId();
    }

    @JsonProperty("messageDate")
    public String getMessageDateView(){
        return messageDate.toString();
    }
}
