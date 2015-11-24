package com.filippov.messenger.dao.message;

import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.message.Message;

import java.util.Date;
import java.util.List;

public interface IMessageDao {

    public List<Message> getMessages(User userSender, User userReceiver);

    public Message getMessage(User user, Integer messageId);

    public Message createMessage(Date messageDate, User sender, User receiver, String messageText);

    public boolean updateMessage(Message message);

    public boolean deleteMessage(Message message);
}
