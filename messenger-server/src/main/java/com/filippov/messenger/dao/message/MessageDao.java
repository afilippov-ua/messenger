package com.filippov.messenger.dao.message;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.message.Message;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MessageDao extends AbstractDao implements IMessageDao {

    public Message createMessage(Date messageDate,
                                 User sender,
                                 User receiver,
                                 String messageText) {
        Message newMessage = new Message(messageDate, sender, receiver, messageText);
        save(newMessage);
        return newMessage;
    }

    public Message getMessage(User user, Integer messageId) {
        Query query = getSession().createQuery("from Message " +
                "where id = :id " +
                "and (userSender = :currentUser or userReceiver = :currentUser)");
        query.setParameter("currentUser", user);
        query.setParameter("id", messageId);
        return (Message) query.uniqueResult();
    }

    public List<Message> getMessages(User userSender, User userReceiver) {
        Query query = getSession().createQuery("from Message " +
                "where (userSender = :userSender or userReceiver =:userSender) " +
                "and (userSender = :userReceiver or userReceiver = :userReceiver)");
        query.setParameter("userSender", userSender);
        query.setParameter("userReceiver", userReceiver);

        return (List<Message>) query.list();
    }

    public boolean updateMessage(Message message) {
        persist(message);
        return true;
    }

    public boolean deleteMessage(Message message) {
        delete(message);
        return true;
    }
}
