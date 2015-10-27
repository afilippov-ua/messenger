package com.filippov.messenger.dao.message;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.message.Message;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MessageDao extends AbstractDao implements IMessageDao {

    public List<Message> getMessages(User userSender,
                                     User userReceiver) throws IllegalArgumentException {

        if (userSender == null)
            throw new IllegalArgumentException("argument \"userSender\" is not valid");

        if (userReceiver == null)
            throw new IllegalArgumentException("argument \"userReceiver\" is not valid");

        Query query = getSession().createQuery("from Message " +
                "where (userSender = :userSender or userReceiver =:userSender) " +
                "and (userSender = :userReceiver or userReceiver = :userReceiver)");
        query.setParameter("userSender", userSender);
        query.setParameter("userReceiver", userReceiver);

        return (List<Message>)query.list();

    }

    public Message getMessage(User user, int messageId) throws IllegalArgumentException {

        if (user == null)
            throw new IllegalArgumentException("argument \"user\" is not valid");

        Query query = getSession().createQuery("from Message " +
                "where id = :id " +
                "and (userSender = :currentUser or userReceiver = :currentUser)");
        query.setParameter("currentUser", user);
        query.setParameter("id", messageId);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (Message)result.get(0);
        }
    }

    public Message createMessage(Date messageDate,
                                 User sender,
                                 User receiver,
                                 String messageText) throws IllegalArgumentException {

        if (messageDate == null)
            throw new IllegalArgumentException("argument \"messageDate\" is not valid");

        if (sender == null)
            throw new IllegalArgumentException("argument \"sender\" is not valid");

        if (receiver == null)
            throw new IllegalArgumentException("argument \"receiver\" is not valid");

        if (messageText == null)
            throw new IllegalArgumentException("argument \"messageText\" is not valid");

        Message newMessage = new Message(messageDate, sender, receiver, messageText);
        save(newMessage);

        return newMessage;
    }

    public void updateMessage(User user,
                              int messageId,
                              Message sourceMessage) throws MessageNotFoundException, IllegalArgumentException {

        if (user == null)
            throw new IllegalArgumentException("argument \"user\" is not valid");

        if (sourceMessage == null)
            throw new IllegalArgumentException("argument \"sourceMessage\" is not valid");

        Message currentMessage = getMessage(user, messageId);
        if (currentMessage == null)
            throw new MessageNotFoundException("Message with id" + messageId + " was not found");

        currentMessage.loadValues(sourceMessage);

        persist(currentMessage);
    }

    public void deleteMessage(User user,
                              int messageId) throws MessageNotFoundException, IllegalArgumentException {

        if (user == null)
            throw new IllegalArgumentException("argument \"user\" is not valid");

        Message currentMessage = getMessage(user, messageId);
        if (currentMessage == null)
            throw new MessageNotFoundException("Message with id " + messageId + " was not found");

        delete(currentMessage);
    }
}
