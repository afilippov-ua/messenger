package com.filippov.messenger.entity.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filippov.messenger.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Messages")
public class Message implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "messageDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private User userSender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User userReceiver;

    @Column(name = "text")
    private String text;

    @Column(name = "seen")
    private boolean seen;

    public Message(Date messageDate, User sender, User receiver, String text){
        setMessageDate(messageDate);
        setUserSender(sender);
        setUserReceiver(receiver);
        setText(text);
    }

    public Message() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (seen != message.seen) return false;
        if (!id.equals(message.id)) return false;
        if (messageDate != null ? !messageDate.equals(message.messageDate) : message.messageDate != null) return false;
        if (userSender != null ? !userSender.equals(message.userSender) : message.userSender != null) return false;
        if (userReceiver != null ? !userReceiver.equals(message.userReceiver) : message.userReceiver != null)
            return false;
        return !(text != null ? !text.equals(message.text) : message.text != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageDate=" + messageDate +
                ", userSender.Id=" + userSender.getId() +
                ", userReceiver.Id=" + userReceiver.getId() +
                ", text='" + text + '\'' +
                ", seen=" + seen +
                '}';
    }
}
