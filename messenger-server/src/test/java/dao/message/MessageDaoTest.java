package dao.message;

import dao.user.IUserDao;
import dao.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class MessageDaoTest {

    @Inject
    IMessageDao messageDao;

    @Inject
    IUserDao userDao;

    public User userSender;
    public User userReceiver;
    public String msgText = "new message text";

    @Before
    public void beforeTests() {
        userSender = userDao.getUserById(3);
        userReceiver = userDao.getUserById(1);
    }

    /** Create message
     * Message should be created (not null) */
    @Test
    public void createMessageTest(){

        Date currentDate = new Date();

        Message msg = messageDao.createMessage(currentDate, userSender, userReceiver, msgText);
        assertNotNull(msg);

        assertEquals(currentDate, msg.getMessageDate());
        assertEquals(userSender, msg.getUserSender());
        assertEquals(userReceiver, msg.getUserReceiver());
        assertEquals(msgText, msg.getText());
    }

    /** Create message
     * Incorrect date.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void createMessageIncorrectDateTest() throws IllegalArgumentException {
        messageDao.createMessage(null, userSender, userReceiver, msgText);
    }

    /** Create message
     * Incorrect userSender.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void createMessageIncorrectUserSenderTest() throws IllegalArgumentException {
        messageDao.createMessage(new Date(), null, userReceiver, msgText);
    }

    /** Create message
     * Incorrect userReceiver.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void createMessageIncorrectUserReceiverTest() throws IllegalArgumentException {
        messageDao.createMessage(new Date(), userSender, null, msgText);
    }

    /** Create message
     * Incorrect messageText.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void createMessageIncorrectMessageTextTest() throws IllegalArgumentException {
        messageDao.createMessage(new Date(), userSender, userReceiver, null);
    }

    /** Update message data
     * Message already exists in DB
     * Message should be updated */
    @Test
    public void updateMessageTest(){

        int messageId = 6;

        Message sourceMessage = messageDao.getMessage(userSender, messageId);
        assertNotNull("Message should not be null! Check the test data.", sourceMessage);

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

        try {
            messageDao.updateMessage(userSender, 3, sourceMessage);

            sourceMessage = messageDao.getMessage(userSender, messageId);

            assertEquals(newDate, sourceMessage.getMessageDate());
            assertEquals(newUserSender, sourceMessage.getUserSender());
            assertEquals(newUserReceiver, sourceMessage.getUserReceiver());
            assertEquals(newText, sourceMessage.getText());
            assertEquals(newSeen, sourceMessage.isSeen());

        } catch (MessageNotFoundException e){
            fail(String.format("Message with id %s was not found! Check the test data.", messageId));
        }
    }

    /** Update message
     * Message doesn't exist in DB.
     * Expected MessageNotFoundException exception */
    @Test(expected = MessageNotFoundException.class)
    public void updateMessageNotFoundTest() throws MessageNotFoundException{
        Message sourceMessage = messageDao.getMessage(userSender, 3);
        messageDao.updateMessage(userSender, -1, sourceMessage);
    }

    /** Update message
     * Incorrect userSender.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void updateMessageIncorrectUserSenderTest() throws MessageNotFoundException, IllegalArgumentException {
        Message sourceMessage = messageDao.getMessage(userSender, 3);
        messageDao.updateMessage(null, 3, sourceMessage);
    }

    /** Update message
     * Incorrect sourceMessage.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void updateMessageIncorrectSourceMessageTest() throws MessageNotFoundException, IllegalArgumentException {
        messageDao.updateMessage(userSender, 3, null);
    }

    /** Delete message from DB
     * Message already exists in DB
     * Message should be delete */
    @Test
    public void deleteMessageTest(){

        int messageId = 7;
        assertNotNull("Check the test data", messageDao.getMessage(userSender, messageId));

        try {

            messageDao.deleteMessage(userSender, messageId);
            assertNull(messageDao.getMessage(userSender, messageId));

        } catch (MessageNotFoundException e){
            fail(String.format("Message with id %s was not found! Check the test data.", messageId));
        }
    }

    /** Delete message
     * Message doesn't exist in DB
     * Expected MessageNotFoundException exception */
    @Test(expected = MessageNotFoundException.class)
    public void deleteMessageNotFoundTest() throws MessageNotFoundException {
        messageDao.deleteMessage(userSender, -1);
    }

    /** Delete message
     * Incorrect userSender.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void deleteMessageIncorrectUserSenderTest() throws MessageNotFoundException, IllegalArgumentException {
        messageDao.deleteMessage(null, 3);
    }

    /** Get message from DB
     * Message already exists in DB.
     * Message should be get (not null) */
    @Test
    public void getMessageTest(){

        Integer messageId = 1;
        String text = "Hi!";

        Message message = messageDao.getMessage(userSender, messageId);
        assertNotNull(message);
        assertEquals(messageId, message.getId());
        assertEquals(userSender, message.getUserSender());
        assertEquals(userReceiver, message.getUserReceiver());
        assertEquals(text, message.getText());
    }

    /** Get message
     * Message doesn't exist in DB. Should return null */
    @Test
    public void getMessageNullIfNotFoundTest() {
        Message message = messageDao.getMessage(userSender, -1);
        assertNull(message);
    }

    /** Get message
     * Incorrect userSender.
     * Expected IllegalArgumentException exception */
    @Test(expected = IllegalArgumentException.class)
    public void getMessageIncorrectUserSenderTest() throws IllegalArgumentException {
        Message message = messageDao.getMessage(null, 1);
    }

    /** Get list of messages from DB
     * Messages already exists in DB.
     * Message list should be get (not null) */
    @Test
    public void getMessagesTest(){

        Message findMessage1 = messageDao.getMessage(userSender, 1);
        Message findMessage2 = messageDao.getMessage(userSender, 2);
        Message findMessage3 = messageDao.getMessage(userSender, 3);

        assertNotNull("Check the test data if message not found", findMessage1);
        assertNotNull("Check the test data if message not found", findMessage2);
        assertNotNull("Check the test data if message not found", findMessage3);

        List<Message> messageList = messageDao.getMessages(userSender, userReceiver);
        assertNotNull(messageList);
        assertNotEquals(0, messageList.size());
        assertTrue(messageList.contains(findMessage1));
        assertTrue(messageList.contains(findMessage2));
        assertTrue(messageList.contains(findMessage3));
    }

}
