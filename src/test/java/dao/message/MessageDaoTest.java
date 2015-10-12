package dao.message;

import dao.user.IUserDao;
import dao.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-dao-config.xml"})
@Transactional
public class MessageDaoTest {

    @Inject
    IMessageDao messageDao;

    @Inject
    IUserDao userDao;

    /** Create message
     * Message should be created (not null) */
    @Test
    public void createMessageTest(){

        Date currentDate = new Date();
        User userSender = userDao.getUserById(3);
        User userReceiver = userDao.getUserById(1);
        String msgText = "message text";

        Message msg = messageDao.createMessage(currentDate, userSender, userReceiver, msgText);
        assertNotNull(msg);

        assertEquals(currentDate, msg.getMessageDate());
        assertEquals(userSender, msg.getUserSender());
        assertEquals(userReceiver, msg.getUserReceiver());
        assertEquals(msgText, msg.getText());
    }

    @Test
    public void updateMessageTest(){

    }

    @Test
    public void deleteMessageTest(){

    }

    @Test
    public void getMessageTest(){

    }

}
