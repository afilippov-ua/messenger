package com.filippov.messenger.dao.message;

import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.entity.message.Message;

import java.util.List;

public interface IMessageDao {
    Message createMessage(Message message);

    Message getMessage(User user, Integer messageId);

    List<Message> getMessages(User userSender, User userReceiver, Message firstMessage);

    boolean updateMessage(Message message);

    boolean deleteMessage(Message message);
}
