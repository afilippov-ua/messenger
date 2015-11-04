package com.filippov.messenger.service.message;

import com.filippov.messenger.entity.message.Message;

import java.util.List;

public interface IMessageService {

    public List<Message> getMessages(int senderId, int receiverId);

    public Message getMessage(int userId, int messageId);

    public Message createMessage(int senderId, int receiverId, String messageText);

    public boolean deleteMessage(int userId, int id);

    public boolean updateMessage(int userId, int messageId, Message sourceMessage);

}
