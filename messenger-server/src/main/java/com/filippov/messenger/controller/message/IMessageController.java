package com.filippov.messenger.controller.message;

import com.filippov.messenger.entity.message.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMessageController {

    public ResponseEntity<List<Message>> getMessages(Integer senderId, Integer receiverId);

    public ResponseEntity<Message> getMessage(Integer userId, Integer messageId);

    public ResponseEntity createMessage(Integer senderId, Integer receiverId, String messageText);

    public ResponseEntity deleteMessage(Integer userId, Integer id);

    public ResponseEntity updateMessage(Integer userId, Integer messageId, Message sourceMessage);

}
