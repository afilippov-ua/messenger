package com.filippov.messenger.dao.message;

import com.filippov.messenger.dao.AbstractDao;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.message.Message;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class MessageDao extends AbstractDao implements IMessageDao {

    public Message createMessage(Message message) {
        save(message);
        return message;
    }

    public Message getMessage(User user, Integer messageId) {
        Query query = getSession().createQuery("from Message " +
                "where id = :id " +
                "and (userSender = :currentUser or userReceiver = :currentUser)");
        query.setParameter("currentUser", user);
        query.setParameter("id", messageId);
        return (Message) query.uniqueResult();
    }

    public List<Message> getMessages(User userSender, User userReceiver, Message firstMessage) {
        Query query = getSession().createQuery("from Message " +
                "where (userSender = :userSender or userReceiver =:userSender) " +
                "and (userSender = :userReceiver or userReceiver = :userReceiver) " +
                "and messageDate < :firstMessageDate " +
                "order by id desc");
        query.setParameter("userSender", userSender);
        query.setParameter("userReceiver", userReceiver);
        query.setParameter("firstMessageDate", (firstMessage == null) ? new Date() : firstMessage.getMessageDate());
        query.setMaxResults(30);

        List<Message> list = (List<Message>) query.list();
        Collections.reverse(list);

        return list;
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
