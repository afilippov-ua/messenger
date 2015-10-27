package com.filippov.messenger.api.message;

import com.filippov.messenger.dao.message.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMessageService {

    public ResponseEntity<List<Message>> getMessages(String senderId, String receiverId);

    public ResponseEntity<Message> getMessage(Integer userId, Integer messageId);

    public ResponseEntity createMessage(Integer senderId, Integer receiverId, String messageText);

    public ResponseEntity deleteMessage(Integer userId, Integer id);

    public ResponseEntity updateMessage(Integer userId, Integer messageId, Message sourceMessage);

}
