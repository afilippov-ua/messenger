package com.filippov.messenger.controller.message;

import com.filippov.messenger.entity.message.Message;
import com.filippov.messenger.service.message.IMessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/api/messages")
public class MessageController implements IMessageController {

    @Autowired
    private IMessageService messageService;

    private static final Logger logger = Logger.getLogger(MessageController.class.getName());

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createMessage(@RequestHeader("userId") Integer senderId,
                                        @RequestHeader("receiverId") Integer receiverId,
                                        @RequestBody String messageText) {
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("/api/messages (POST) - method \"createMessage\", userId: \"%d\", receiverId: \"%d\", messageText: \"%s\"", senderId, receiverId, messageText));
        }
        try {
            messageText = URLDecoder.decode(messageText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        }

        Message newMessage = messageService.createMessage(senderId, receiverId, messageText);
        if (newMessage == null)
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(newMessage.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getMessage(@RequestParam("userId") Integer userId,
                                              @PathVariable("messageId") Integer messageId) {
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("/api/messages/%d (GET) - method \"getMessage\", messageId: \"%d\"", userId, messageId));
        }
        Message msg = messageService.getMessage(userId, messageId);
        if (msg == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessages(@RequestParam("senderId") Integer senderId,
                                                     @RequestParam("receiverId") Integer receiverId,
                                                     @RequestParam(value = "firstMessageId", required = false) Integer firstMessageId) {
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("/api/messages (GET) - method \"getMessages\", senderId: \"%d\", receiverId: \"%d\", firstMessageId: \"%d\"", senderId, receiverId, firstMessageId));
        }
        return new ResponseEntity<>(messageService.getMessages(senderId, receiverId, firstMessageId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.PUT)
    public ResponseEntity updateMessage(@RequestParam("userId") Integer userId,
                                        @PathVariable("messageId") Integer messageId,
                                        @RequestBody Message sourceMessage) {
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("/api/messages/%d (PUT) - method \"updateMessage\", userId: \"%d\", sourceMessage: %s", messageId, userId, sourceMessage));
        }
        if (messageService.updateMessage(userId, messageId, sourceMessage))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMessage(@RequestParam("userId") Integer userId,
                                        @PathVariable("messageId") Integer messageId) {
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("/api/messages/%d (DELETE) - method \"deleteMessage\", userId: \"%d\"", messageId, userId));
        }
        if (messageService.deleteMessage(userId, messageId))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
