package com.filippov.messenger.service.message;

import com.filippov.messenger.dao.message.IMessageDao;
import com.filippov.messenger.entity.message.Message;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MessageService implements IMessageService {

    @Autowired
    IMessageDao messageDao;

    @Autowired
    IUserDao userDao;

    @Transactional
    public Message createMessage(int senderId, int receiverId, String messageText) {

        User userSender = userDao.getUserById(senderId);
        User userReceiver = userDao.getUserById(receiverId);

        if (userSender != null && userReceiver != null)
            return messageDao.createMessage(new Date(), userSender, userReceiver, messageText);
        else
            return null;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessages(int senderId, int receiverId) throws IllegalArgumentException {

        User userSender = userDao.getUserById(senderId);
        User userReceiver = userDao.getUserById(receiverId);

        if (userSender == null || userReceiver == null)
            return null;

        return messageDao.getMessages(userSender, userReceiver);
    }

    @Transactional(readOnly = true)
    public Message getMessage(int userId, int messageId) {

        User user = userDao.getUserById(userId);
        if (user == null)
            return null;

        return messageDao.getMessage(user, messageId);
    }

    @Transactional
    public boolean deleteMessage(int userId, int messageId) {

        User user = userDao.getUserById(userId);
        if (user == null)
            return false;

        Message message = messageDao.getMessage(user, messageId);
        if (message == null)
            return false;

       return messageDao.deleteMessage(message);
    }

    @Transactional
    public boolean updateMessage(int userId, int messageId, Message sourceMessage) {

        if (sourceMessage == null)
            return false;

        User user = userDao.getUserById(userId);
        if (user == null)
            return false;

        Message currentMessage = messageDao.getMessage(user, messageId);
        if (currentMessage == null)
            return false;

        currentMessage.loadValues(sourceMessage);
        return messageDao.updateMessage(currentMessage);
    }
}
