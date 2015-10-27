package com.filippov.messenger.api.message;

import com.filippov.messenger.dao.message.IMessageDao;
import com.filippov.messenger.entity.message.Message;
import com.filippov.messenger.dao.message.MessageNotFoundException;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class MessageService implements IMessageService {

    @Inject
    IMessageDao messageDao;

    @Inject
    IUserDao userDao;

    @Transactional
    @RequestMapping(value = "/{userId}/messages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMessage(@PathVariable("userId") Integer senderId,
                                        @RequestParam("receiverId") Integer receiverId,
                                        @RequestBody String messageText) {

        User userSender = userDao.getUserById(senderId);
        User userReceiver = userDao.getUserById(receiverId);

        if (userSender != null && userReceiver != null) {
            messageDao.createMessage(new Date(), userSender, userReceiver, messageText);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @RequestMapping(value = "/{senderId}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessages(@PathVariable("senderId") String senderId,
                                                     @RequestParam("receiverId") String receiverId) {

        User userSender = userDao.getUserById(Integer.parseInt(senderId));
        User userReceiver = userDao.getUserById(Integer.parseInt(receiverId));

        List<Message> messageList = messageDao.getMessages(userSender, userReceiver);
        return new ResponseEntity<List<Message>>(messageList, HttpStatus.OK);

    }

    @Transactional
    @RequestMapping(value = "/{userId}/messages/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Message> getMessage(@PathVariable("userId") Integer userId,
                                              @PathVariable("messageId") Integer messageId) {

        User user = userDao.getUserById(userId);
        if (user == null)
            return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);

        Message msg = messageDao.getMessage(user, messageId);
        if (msg == null)
            return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Message>(msg, HttpStatus.OK);

    }

    @Transactional
    @RequestMapping(value = "/{userId}/messages/{messageId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMessage(@PathVariable("userId") Integer userId,
                                        @PathVariable("messageId") Integer messageId) {

        User user = userDao.getUserById(userId);
        if (user == null)
            return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);

        try {
            messageDao.deleteMessage(user, messageId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (MessageNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @Transactional
    @RequestMapping(value = "/{userId}/messages/{messageId}", method = RequestMethod.PUT)
    public ResponseEntity updateMessage(@PathVariable("userId") Integer userId,
                                        @PathVariable("messageId") Integer messageId,
                                        @RequestBody Message sourceMessage) {

        User user = userDao.getUserById(userId);
        if (user == null)
            return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);

        Message currentMessage = messageDao.getMessage(user, messageId);
        if (currentMessage == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        currentMessage.loadValues(sourceMessage);
        try {
            messageDao.updateMessage(user, messageId, currentMessage);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (MessageNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

}
