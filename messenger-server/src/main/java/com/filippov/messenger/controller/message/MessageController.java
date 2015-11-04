package com.filippov.messenger.controller.message;

import com.filippov.messenger.entity.message.Message;
import com.filippov.messenger.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class MessageController implements IMessageController {

    @Autowired
    IMessageService messageService;

    @Transactional
    @RequestMapping(
            value = "/{userId}/messages",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMessage(@PathVariable("userId") Integer senderId,
                                        @RequestParam("receiverId") Integer receiverId,
                                        @RequestBody String messageText) {

        if (messageService.createMessage(senderId, receiverId, messageText) == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(
            value = "/{senderId}/messages",
            method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessages(@PathVariable("senderId") Integer senderId,
                                                     @RequestParam("receiverId") Integer receiverId) {

        return new ResponseEntity<>(messageService.getMessages(senderId, receiverId), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(
            value = "/{userId}/messages/{messageId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<Message> getMessage(@PathVariable("userId") Integer userId,
                                              @PathVariable("messageId") Integer messageId) {

        Message msg = messageService.getMessage(userId, messageId);
        if (msg == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(
            value = "/{userId}/messages/{messageId}",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteMessage(@PathVariable("userId") Integer userId,
                                        @PathVariable("messageId") Integer messageId) {

        if (messageService.deleteMessage(userId, messageId))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(
            value = "/{userId}/messages/{messageId}",
            method = RequestMethod.PUT)
    public ResponseEntity updateMessage(@PathVariable("userId") Integer userId,
                                        @PathVariable("messageId") Integer messageId,
                                        @RequestBody Message sourceMessage) {

        if (messageService.updateMessage(userId, messageId, sourceMessage))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
