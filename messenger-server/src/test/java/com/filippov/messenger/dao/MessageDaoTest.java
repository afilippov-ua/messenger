package com.filippov.messenger.dao;

import com.filippov.messenger.dao.message.IMessageDao;
import com.filippov.messenger.entity.message.Message;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.user.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class MessageDaoTest {

    @Autowired
    private IMessageDao messageDao;

    @Autowired
    private IUserDao userDao;

    public User testUserSender, testUserReceiver;
    public Message testMessage1, testMessage2, testMessage3;
    public ArrayList<Message> testMessageList;

    @Before
    public void setup() {
        testUserSender = userDao.createUser(new User("test user sender", "12345"));
        testUserReceiver = userDao.createUser(new User("test user receiver", "12345"));
        assertNotNull(testUserSender);
        assertNotNull(testUserReceiver);

        Date currentDate = new Date(new Date().getTime() - 100);
        testMessage1 = messageDao.createMessage(
                new Message(currentDate, testUserSender, testUserReceiver, "test message 1"));

        currentDate = new Date(currentDate.getTime() + 1);
        testMessage2 = messageDao.createMessage(
                new Message(currentDate, testUserSender, testUserReceiver, "test message 2"));

        currentDate = new Date(currentDate.getTime() + 1);
        testMessage3 = messageDao.createMessage(
                new Message(currentDate, testUserSender, testUserReceiver, "test message 3"));

        testMessageList = new ArrayList<Message>(33);
        testMessageList.add(0, testMessage1);
        testMessageList.add(1, testMessage2);
        testMessageList.add(2, testMessage3);

        for (int i = 3; i < 33; i++) {
            currentDate = new Date(currentDate.getTime() + 1);
            testMessageList.add(i, messageDao.createMessage(new Message(currentDate,
                                                                        testUserSender,
                                                                        testUserReceiver,
                                                                        "test message " + (i+1))));
        }
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
    public void getLast30MessagesTest() {
        List<Message> messageList = messageDao.getMessages(
                testUserSender,
                testUserReceiver,
                null);
        assertNotNull(messageList);
        assertEquals(30, messageList.size()); // batch = 30

        for (int i = 0; i < 30; i++) {
            // testMessageList have 33 elements. we need last 30
            assertTrue(messageList.contains(testMessageList.get(i+3)));
        }
    }

    /* Test method: "getMessages()" */
    @Test
    public void getPreviousMessagesTest() {
        List<Message> messageList = messageDao.getMessages(
                testUserSender,
                testUserReceiver,
                testMessageList.get(3));
        assertNotNull(messageList);

        // testMessageList have 33 elements
        // we need previous 3
        assertEquals(3, messageList.size());

        for (int i = 0; i < 3; i++) {
            assertTrue(messageList.contains(testMessageList.get(i)));
        }
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
