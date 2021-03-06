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
    private IMessageDao messageDao;

    @Autowired
    private IUserDao userDao;

    @Override
    @Transactional
    public Message createMessage(Integer senderId, Integer receiverId, String messageText) {
        if (senderId == null || receiverId == null || messageText == null)
            return null;

        User userSender = userDao.getUserById(senderId);
        User userReceiver = userDao.getUserById(receiverId);

        if (userSender != null && userReceiver != null)
            return messageDao.createMessage(new Message(new Date(), userSender, userReceiver, messageText));
        else
            return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Message getMessage(Integer userId, Integer messageId) {
        if (userId == null || messageId == null)
            return null;

        User user = userDao.getUserById(userId);
        if (user == null)
            return null;

        return messageDao.getMessage(user, messageId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getMessages(Integer senderId, Integer receiverId, Integer firstMessageId) throws IllegalArgumentException {
        if (senderId == null || receiverId == null)
            return null;

        User userSender = userDao.getUserById(senderId);
        User userReceiver = userDao.getUserById(receiverId);

        if (userSender == null || userReceiver == null)
            return null;

        Message firstMessage = null;
        if (firstMessageId != null)
            firstMessage = messageDao.getMessage(userSender, firstMessageId);

        return messageDao.getMessages(userSender, userReceiver, firstMessage);
    }

    @Override
    @Transactional
    public boolean updateMessage(Integer userId, Integer messageId, Message sourceMessage) {
        if (userId == null || messageId == null || sourceMessage == null)
            return false;

        User user = userDao.getUserById(userId);
        if (user == null)
            return false;

        Message currentMessage = messageDao.getMessage(user, messageId);
        if (currentMessage == null)
            return false;

        currentMessage.setMessageDate(sourceMessage.getMessageDate());
        currentMessage.setSeen(sourceMessage.isSeen());
        currentMessage.setText(sourceMessage.getText());
        return messageDao.updateMessage(currentMessage);
    }

    @Override
    @Transactional
    public boolean deleteMessage(Integer userId, Integer messageId) {
        if (userId == null || messageId == null)
            return false;

        User user = userDao.getUserById(userId);
        if (user == null)
            return false;

        Message message = messageDao.getMessage(user, messageId);
        if (message == null)
            return false;

        return messageDao.deleteMessage(message);
    }
}
