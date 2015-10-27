package com.filippov.messenger.dao.message;

import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.message.Message;

import java.util.Date;
import java.util.List;

public interface IMessageDao {

    public List<Message> getMessages(User userSender, User userReceiver) throws IllegalArgumentException;

    public Message getMessage(User user, int messageId) throws IllegalArgumentException;

    public Message createMessage(Date messageDate, User sender, User receiver, String messageText) throws IllegalArgumentException;

    public void updateMessage(User user, int messageId, Message sourceMessage) throws MessageNotFoundException, IllegalArgumentException;

    public void deleteMessage(User user, int messageId) throws MessageNotFoundException, IllegalArgumentException;
}
