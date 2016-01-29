package com.filippov.messenger.service.message;

import com.filippov.messenger.entity.message.Message;

import java.util.List;

public interface IMessageService {
    Message createMessage(Integer senderId, Integer receiverId, String messageText);

    Message getMessage(Integer userId, Integer messageId);

    List<Message> getMessages(Integer senderId, Integer receiverId, Integer firstMessageId);

    boolean updateMessage(Integer userId, Integer messageId, Message sourceMessage);

    boolean deleteMessage(Integer userId, Integer id);
}
