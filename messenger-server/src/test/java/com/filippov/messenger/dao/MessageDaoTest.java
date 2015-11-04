package com.filippov.messenger.dao;

import com.filippov.messenger.dao.message.IMessageDao;
import com.filippov.messenger.entity.message.Message;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.hibernate.PersistentObjectException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class MessageDaoTest {

    @Autowired
    IMessageDao messageDao;

    @Autowired
    IUserDao userDao;

    public User userSender;
    public User userReceiver;
    public String msgText = "new message text";

    @Before
    public void beforeTests() {
        userSender = userDao.getUserById(3);
        userReceiver = userDao.getUserById(1);
    }

    /* Create message
     * Expected not null */
    @Test
    public void createMessageTest() {

        Date currentDate = new Date();

        Message msg = messageDao.createMessage(currentDate, userSender, userReceiver, msgText);
        assertNotNull(msg);

        assertEquals(currentDate, msg.getMessageDate());
        assertEquals(userSender, msg.getUserSender());
        assertEquals(userReceiver, msg.getUserReceiver());
        assertEquals(msgText, msg.getText());
    }

    /* Create message
     * Illegal arguments
     * Expected null */
    @Test
    public void createMessageIncorrectDateTest() {

        assertNull(messageDao.createMessage(null, userSender, userReceiver, msgText));
        assertNull(messageDao.createMessage(new Date(), null, userReceiver, msgText));
        assertNull(messageDao.createMessage(new Date(), userSender, null, msgText));
        assertNull(messageDao.createMessage(new Date(), userSender, userReceiver, null));
        assertNull(messageDao.createMessage(null, null, null, null));
    }

    /* Update message data
     * Message already exists in DB
     * Expected true */
    @Test
    public void updateMessageTest() {

        int messageId = 6;

        Message sourceMessage = messageDao.getMessage(userSender, messageId);
        assertNotNull(sourceMessage);

        Date newDate = new Date();
        User newUserSender = sourceMessage.getUserReceiver();
        User newUserReceiver = sourceMessage.getUserSender();
        String newText = "new text";
        boolean newSeen = !sourceMessage.isSeen();

        sourceMessage.setMessageDate(newDate);
        sourceMessage.setUserSender(newUserSender);
        sourceMessage.setUserReceiver(newUserReceiver);
        sourceMessage.setText(newText);
        sourceMessage.setSeen(newSeen);

        assertEquals(newDate, sourceMessage.getMessageDate());
        assertEquals(newUserSender, sourceMessage.getUserSender());
        assertEquals(newUserReceiver, sourceMessage.getUserReceiver());
        assertEquals(newText, sourceMessage.getText());
        assertEquals(newSeen, sourceMessage.isSeen());

        assertTrue(messageDao.updateMessage(sourceMessage));

        sourceMessage = messageDao.getMessage(userSender, messageId);

        assertEquals(newDate, sourceMessage.getMessageDate());
        assertEquals(newUserSender, sourceMessage.getUserSender());
        assertEquals(newUserReceiver, sourceMessage.getUserReceiver());
        assertEquals(newText, sourceMessage.getText());
        assertEquals(newSeen, sourceMessage.isSeen());
    }

    /* Update message
     * Message doesn't exist in DB.
     * Expected PersistentObjectException */
    @Test(expected = PersistentObjectException.class)
    public void updateMessageNotFoundTest() {

        Message sourceMessage = new Message();
        sourceMessage.setId(-1);
        messageDao.updateMessage(sourceMessage);
    }

    /* Update message
     * Illegal arguments.
     * Expected false */
    @Test
    public void updateMessageIllegalArgumentsTest() {
        assertFalse(messageDao.updateMessage(null));
    }

    /* Delete message from DB
     * Message already exists in DB
     * Expected true */
    @Test
    public void deleteMessageTest() {

        int messageId = 7;
        Message message = messageDao.getMessage(userSender, messageId);
        assertNotNull(message);

        assertTrue(messageDao.deleteMessage(message));
        assertNull(messageDao.getMessage(userSender, messageId));
    }

    /* Delete message
     * Message doesn't exist in DB
     * Expected true */
    @Test
    public void deleteMessageNotFoundTest() {

        Message message = new Message();
        message.setId(-1);
        assertTrue(messageDao.deleteMessage(message));
    }

    /* Delete message
     * Illegal arguments
     * Expected false */
    @Test
    public void deleteMessageIllegalArgumentsTest() {
        assertFalse(messageDao.deleteMessage(null));
    }

    /* Get message from DB
     * Message already exists in DB.
     * Expected not null */
    @Test
    public void getMessageTest() {

        Integer messageId = 1;
        String text = "Hi!";

        Message message = messageDao.getMessage(userSender, messageId);
        assertNotNull(message);
        assertEquals(messageId, message.getId());
        assertEquals(userSender, message.getUserSender());
        assertEquals(userReceiver, message.getUserReceiver());
        assertEquals(text, message.getText());
    }

    /* Get message
     * Message doesn't exist in DB.
     * Expected null */
    @Test
    public void getMessageNotFoundTest() {
        assertNull(messageDao.getMessage(userSender, -1));
    }

    /* Get message
     * Illegal arguments
     * Expected null */
    @Test
    public void getMessageIllegalArgumentsTest() {
        assertNull(messageDao.getMessage(null, 1));
    }

    /* Get list of messages from DB
     * Messages already exists in DB.
     * Expected not null (list of messages) */
    @Test
    public void getMessagesTest() {

        Message findMessage1 = messageDao.getMessage(userSender, 1);
        Message findMessage2 = messageDao.getMessage(userSender, 2);
        Message findMessage3 = messageDao.getMessage(userSender, 3);

        assertNotNull(findMessage1);
        assertNotNull(findMessage2);
        assertNotNull(findMessage3);

        List<Message> messageList = messageDao.getMessages(userSender, userReceiver);
        assertNotNull(messageList);
        assertNotEquals(0, messageList.size());
        assertTrue(messageList.contains(findMessage1));
        assertTrue(messageList.contains(findMessage2));
        assertTrue(messageList.contains(findMessage3));
    }

    /* Get list of messages from DB
     * Illegal arguments
     * Expected null */
    @Test
    public void getMessagesIllegalArgumentsTest() {

        assertNull(messageDao.getMessages(null, userReceiver));
        assertNull(messageDao.getMessages(userSender, null));
        assertNull(messageDao.getMessages(null, null));
    }
}
