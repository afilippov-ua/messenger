package com.filippov.messenger.controller.message;

import com.filippov.messenger.entity.message.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMessageController {
    ResponseEntity createMessage(Integer senderId, Integer receiverId, String messageText);

    ResponseEntity<Message> getMessage(Integer userId, Integer messageId);

    ResponseEntity<List<Message>> getMessages(Integer senderId, Integer receiverId);

    ResponseEntity updateMessage(Integer userId, Integer messageId, Message sourceMessage);

    ResponseEntity deleteMessage(Integer userId, Integer id);
}
