package dao.message;

import dao.user.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MessageDao implements IMessageDao {

    @Autowired
    private SessionFactory hibernateSessionFactory;

    private Session getSession() {
        return hibernateSessionFactory.getCurrentSession();
    }

    private void save(Object entity) {
        getSession().save(entity);
    }

    private void delete(Object entity) {
        getSession().delete(entity);
    }

    private void merge(Object entity) {
        getSession().merge(entity);
    }

    private void persist(Object entity) {
        getSession().persist(entity);
    }

    public List<Message> getMessages(User userSender, User userReceiver) {

        if (userSender == null || userReceiver == null)
            return null;

        Query query = getSession().createQuery("from Message where (userSender = :userSender or userReceiver =:userSender) and (userSender = :userReceiver or userReceiver = :userReceiver)");
        query.setParameter("userSender", userSender);
        query.setParameter("userReceiver", userReceiver);

        return (List<Message>)query.list();

    }

    public Message getMessage(User user, int messageId) {

        if (user == null)
            return null;

        Query query = getSession().createQuery("from Message where id = :id and (userSender = :currentUser or userReceiver = :currentUser)");
        query.setParameter("currentUser", user);
        query.setParameter("id", messageId);

        List result = query.list();

        if (result.isEmpty()) {
            return null;
        } else {
            return (Message)result.get(0);
        }
    }

    public Message createMessage(Date messageDate, User sender, User receiver, String messageText) {

        if (sender == null || receiver == null || messageDate == null || messageText == null)
            return null;

        Message newMessage = new Message(messageDate, sender, receiver, messageText);
        save(newMessage);

        return newMessage;
    }

    public void updateMessage(User user, int messageId, Message sourceMessage) throws MessageNotFoundException {

        if (user != null || sourceMessage != null) {

            Message currentMessage = getMessage(user, messageId);
            if (currentMessage == null)
                throw new MessageNotFoundException("Message with id" + messageId + " was not found");

            currentMessage.loadValues(sourceMessage);

            persist(currentMessage);
        }

    }

    public void deleteMessage(User user, int messageId) throws MessageNotFoundException {

        Message currentMessage = getMessage(user, messageId);
        if (currentMessage == null)
            throw new MessageNotFoundException("Message with id " + messageId + " was not found");

        delete(currentMessage);
    }
}
