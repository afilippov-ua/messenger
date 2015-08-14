package dao.message;

import dao.user.User;

import java.util.Date;
import java.util.List;

public interface IMessageDao {

    public List<Message> getMessages(User userSender, User userReceiver);

    public Message getMessage(User user, int messageId);

    public Message createMessage(Date messageDate, User sender, User receiver, String messageText);

    public void updateMessage(User user, int messageId, Message sourceMessage) throws MessageNotFoundException;

    public void deleteMessage(User user, int messageId) throws MessageNotFoundException;
}
