package com.filippov.messenger.service;

import com.filippov.messenger.dao.message.IMessageDao;
import com.filippov.messenger.dao.user.IUserDao;
import com.filippov.messenger.entity.message.Message;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.service.message.IMessageService;
import com.filippov.messenger.service.message.MessageService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
@ContextConfiguration(locations = "classpath*:test-service-config.xml")
@Transactional
public class MessageServiceTest {

    @TestSubject
    IMessageService messageService = new MessageService();

    @Mock(fieldName = "messageDao")
    IMessageDao mockMessageDao;

    @Mock(fieldName = "userDao")
    IUserDao mockUserDao;

    private User testUser1, testUser2;
    private Message testMessage1, testMessage2, testMessage3;

    @Before
    public void setup() {
        testUser1 = new User("user1", "12345");
        testUser1.setId(1);
        testUser2 = new User("user", "12345");
        testUser2.setId(2);

        testMessage1 = new Message(new Date(), testUser1, testUser2, "message 1 text");
        testMessage1.setId(1);
        testMessage2 = new Message(new Date(), testUser1, testUser2, "message 2 text");
        testMessage2.setId(2);
        testMessage3 = new Message(new Date(), testUser1, testUser2, "message 3 text");
        testMessage3.setId(3);
    }

    /* Test method: "createMessage()" */
    @Test
    public void createMessageTest() {
        Message message = new Message(new Date(), testUser2, testUser1, "new message");
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(testUser2).once();
        expect(mockMessageDao.createMessage(anyObject(Message.class))).andReturn(message).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        Message newMessage = messageService.createMessage(
                testUser2.getId(),
                testUser1.getId(),
                message.getText());
        verify(mockUserDao);
        verify(mockMessageDao);
        assertNotNull(newMessage);
        assertNotNull(newMessage.getMessageDate());
        assertEquals(message.getUserSender(), newMessage.getUserSender());
        assertEquals(message.getUserReceiver(), newMessage.getUserReceiver());
        assertEquals(message.getText(), newMessage.getText());
    }

    /* Test method: "createMessage()"
    * User sender not found */
    @Test
    public void createMessageUserSenderNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(null).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(testUser2).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertNull(messageService.createMessage(testUser1.getId(), testUser2.getId(), "text"));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "createMessage()"
    * User receiver not found */
    @Test
    public void createMessageUserReceiverNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertNull(messageService.createMessage(testUser1.getId(), testUser2.getId(), "text"));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "createMessage()"
    * Illegal arguments */
    @Test
    public void createMessageIllegalArgumentsTest() {
        replay(mockMessageDao);
        assertNull(messageService.createMessage(null, testUser2.getId(), "text"));
        verify(mockMessageDao);

        reset(mockMessageDao);
        replay(mockMessageDao);
        assertNull(messageService.createMessage(testUser1.getId(), null, "text"));
        verify(mockMessageDao);

        reset(mockMessageDao);
        replay(mockMessageDao);
        assertNull(messageService.createMessage(testUser1.getId(), testUser2.getId(), null));
        verify(mockMessageDao);
    }

    /* Test method: "getMessage()" */
    @Test
    public void getMessageTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockMessageDao.getMessage(eq(testUser1), eq(testMessage1.getId())))
                .andReturn(testMessage1).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertEquals(testMessage1, messageService.getMessage(testUser1.getId(), testMessage1.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "getMessage()"
    * User not found */
    @Test
    public void getMessageUserNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertNull(messageService.getMessage(testUser1.getId(), testMessage1.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "getMessage()"
    * Illegal arguments */
    @Test
    public void getMessageIllegalArgumentsTest() {
        replay(mockUserDao);
        replay(mockMessageDao);

        assertNull(messageService.getMessage(null, testMessage1.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);

        reset(mockUserDao);
        reset(mockMessageDao);
        replay(mockUserDao);
        replay(mockMessageDao);

        assertNull(messageService.getMessage(testUser1.getId(), null));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "getMessages()" */
    @Test
    public void getMessagesTest() {
        List<Message> list = new ArrayList<>(3);
        list.add(testMessage1);
        list.add(testMessage2);
        list.add(testMessage3);
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(testUser2).once();
        expect(mockMessageDao.getMessages(testUser1, testUser2)).andReturn(list).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        list = messageService.getMessages(testUser1.getId(), testUser2.getId());
        verify(mockUserDao);
        verify(mockMessageDao);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertTrue(list.contains(testMessage1));
        assertTrue(list.contains(testMessage2));
        assertTrue(list.contains(testMessage3));
    }

    /* Test method: "getMessages()"
    * User sender not found */
    @Test
    public void getMessagesUserSenderNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(null).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(testUser2).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertNull(messageService.getMessages(testUser1.getId(), testUser2.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "getMessages()"
   * User receiver not found */
    @Test
    public void getMessagesUserReceiverNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockUserDao.getUserById(testUser2.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertNull(messageService.getMessages(testUser1.getId(), testUser2.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "getMessages()"
   * Illegal arguments */
    @Test
    public void getMessagesIllegalArgumentsTest() {
        replay(mockUserDao);
        replay(mockMessageDao);

        assertNull(messageService.getMessages(null, testUser2.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);

        reset(mockUserDao);
        reset(mockMessageDao);
        replay(mockUserDao);
        replay(mockMessageDao);

        assertNull(messageService.getMessages(testUser1.getId(), null));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "updateMessage()" */
    @Test
    public void updateMessageTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockMessageDao.getMessage(testUser1, testMessage1.getId())).andReturn(testMessage1).once();
        expect(mockMessageDao.updateMessage(testMessage1)).andReturn(true).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertTrue(messageService.updateMessage(testUser1.getId(), testMessage1.getId(), testMessage1));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "updateMessage()"
    * User not found */
    @Test
    public void updateMessageUserNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertFalse(messageService.updateMessage(testUser1.getId(), testMessage1.getId(), testMessage1));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "updateMessage()"
    * Message not found */
    @Test
    public void updateMessageMessageNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockMessageDao.getMessage(testUser1, testMessage1.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertFalse(messageService.updateMessage(testUser1.getId(), testMessage1.getId(), testMessage1));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "updateMessage()"
    * Illegal arguments */
    @Test
    public void updateMessageIllegalArgumentsTest() {
        replay(mockUserDao);
        replay(mockMessageDao);

        assertFalse(messageService.updateMessage(null, testMessage1.getId(), testMessage1));
        verify(mockUserDao);
        verify(mockMessageDao);

        reset(mockUserDao);
        reset(mockMessageDao);
        replay(mockUserDao);
        replay(mockMessageDao);

        assertFalse(messageService.updateMessage(testUser1.getId(), null, testMessage1));
        verify(mockUserDao);
        verify(mockMessageDao);

        reset(mockUserDao);
        reset(mockMessageDao);
        replay(mockUserDao);
        replay(mockMessageDao);

        assertFalse(messageService.updateMessage(testUser1.getId(), testMessage1.getId(), null));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "deleteMessage()" */
    @Test
    public void deleteMessageTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockMessageDao.getMessage(testUser1, testMessage1.getId())).andReturn(testMessage1).once();
        expect(mockMessageDao.deleteMessage(testMessage1)).andReturn(true).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertTrue(messageService.deleteMessage(testUser1.getId(), testMessage1.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "deleteMessage()"
     * User not found */
    @Test
    public void deleteMessageUserNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertFalse(messageService.deleteMessage(testUser1.getId(), testMessage1.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "deleteMessage()"
     * Message not found */
    @Test
    public void deleteMessageMessageNotFoundTest() {
        expect(mockUserDao.getUserById(testUser1.getId())).andReturn(testUser1).once();
        expect(mockMessageDao.getMessage(testUser1, testMessage1.getId())).andReturn(null).once();
        replay(mockUserDao);
        replay(mockMessageDao);

        assertFalse(messageService.deleteMessage(testUser1.getId(), testMessage1.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);
    }

    /* Test method: "deleteMessage()"
     * Illegal arguments */
    @Test
    public void deleteMessageIllegalArgumentsTest() {
        replay(mockUserDao);
        replay(mockMessageDao);

        assertFalse(messageService.deleteMessage(null, testMessage1.getId()));
        verify(mockUserDao);
        verify(mockMessageDao);

        reset(mockUserDao);
        reset(mockMessageDao);
        replay(mockUserDao);
        replay(mockMessageDao);

        assertFalse(messageService.deleteMessage(testUser1.getId(), null));
        verify(mockUserDao);
        verify(mockMessageDao);
    }
}
