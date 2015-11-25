package com.filippov.messenger.dao;

import com.filippov.messenger.dao.message.IMessageDao;
import com.filippov.messenger.entity.message.Message;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
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

    public User testUserSender, testUserReceiver;
    public Message testMessage1, testMessage2, testMessage3;

    @Before
    public void setup() {
        testUserSender = userDao.createUser(new User("test user sender", "12345"));
        testUserReceiver = userDao.createUser(new User("test user receiver", "12345"));
        assertNotNull(testUserSender);
        assertNotNull(testUserReceiver);

        testMessage1 = messageDao.createMessage(
                new Message(new Date(), testUserSender, testUserReceiver, "test message 1"));
        testMessage2 = messageDao.createMessage(
                new Message(new Date(), testUserSender, testUserReceiver, "test message 2"));
        testMessage3 = messageDao.createMessage(
                new Message(new Date(), testUserSender, testUserReceiver, "test message 3"));
    }

    /* Test method: "createMessage()" */
    @Test
    public void createMessageTest() {
        final Date currentDate = new Date();
        final String text = "message text";

        Message message = new Message(currentDate, testUserSender, testUserReceiver, text);
        assertNull(message.getId());
        message = messageDao.createMessage(message);
        assertNotNull(message);
        assertNotNull(message.getId());

        Message newMessage = messageDao.getMessage(testUserSender, message.getId());
        assertNotNull(newMessage);
        assertEquals(message, newMessage);
    }

    /* Test method: "getMessage()" */
    @Test
    public void getMessageTest() {
        Message message = messageDao.getMessage(
                testMessage1.getUserSender(),
                testMessage1.getId());
        assertNotNull(message);
        assertEquals(message, testMessage1);
    }

    /* Test method: "getMessages()" */
    @Test
    public void getMessagesTest() {
        List<Message> messageList = messageDao.getMessages(
                testUserSender,
                testUserReceiver);
        assertNotNull(messageList);
        assertEquals(3, messageList.size());
        assertTrue(messageList.contains(testMessage1));
        assertTrue(messageList.contains(testMessage2));
        assertTrue(messageList.contains(testMessage3));
    }

    /* Test method: "updateMessage()" */
    @Test
    public void updateMessageTest() {
        final Date newDate = new Date();
        final String newText = "new text for test message";

        assertNotEquals(newDate, testMessage1.getMessageDate());
        assertNotEquals(testUserReceiver, testMessage1.getIdUserSender());
        assertNotEquals(testUserSender, testMessage1.getIdUserReceiver());
        assertNotEquals(newText, testMessage1.getText());
        testMessage1.setMessageDate(newDate);
        testMessage1.setUserSender(testUserReceiver);
        testMessage1.setUserReceiver(testUserSender);
        testMessage1.setText(newText);

        assertTrue(messageDao.updateMessage(testMessage1));

        Message updatedMessage = messageDao.getMessage(testUserReceiver, testMessage1.getId());
        assertNotNull(updatedMessage);
        assertEquals(testMessage1, updatedMessage);
    }

    /* Test method: "deleteMessage()" */
    @Test
    public void deleteMessageTest() {
        assertTrue(messageDao.deleteMessage(testMessage1));
        assertNull(messageDao.getMessage(testMessage1.getUserSender(), testMessage1.getId()));
    }
}
