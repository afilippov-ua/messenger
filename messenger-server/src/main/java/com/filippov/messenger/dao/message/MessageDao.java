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

    public List<Message> getMessages(User userSender, User userReceiver) {
        if (userSender == null || userReceiver == null)
            return null;

        Query query = getSession().createQuery("from Message " +
                "where (userSender = :userSender or userReceiver =:userSender) " +
                "and (userSender = :userReceiver or userReceiver = :userReceiver)");
        query.setParameter("userSender", userSender);
        query.setParameter("userReceiver", userReceiver);

        return (List<Message>) query.list();
    }

    public Message getMessage(User user, Integer messageId) {
        if (user == null || messageId == null)
            return null;

        Query query = getSession().createQuery("from Message " +
                "where id = :id " +
                "and (userSender = :currentUser or userReceiver = :currentUser)");
        query.setParameter("currentUser", user);
        query.setParameter("id", messageId);
        return (Message) query.uniqueResult();
    }

    public Message createMessage(Date messageDate,
                                 User sender,
                                 User receiver,
                                 String messageText) {
        if (messageDate == null || sender == null || receiver == null || messageText == null)
            return null;

        Message newMessage = new Message(messageDate, sender, receiver, messageText);
        save(newMessage);
        return newMessage;
    }

    public boolean updateMessage(Message message) {
        if (message == null)
            return false;

        persist(message);
        return true;
    }

    public boolean deleteMessage(Message message) {
        if (message == null)
            return false;

        delete(message);
        return true;
    }
}
