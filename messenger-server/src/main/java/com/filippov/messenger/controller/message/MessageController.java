package com.filippov.messenger.controller.message;

import com.filippov.messenger.entity.message.Message;
import com.filippov.messenger.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/api/messages")
public class MessageController implements IMessageController {

    @Autowired
    private IMessageService messageService;

    @Transactional
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMessage(@RequestHeader("userId") Integer senderId,
                                        @RequestHeader("receiverId") Integer receiverId,
                                        @RequestBody String messageText) {
        try {
            messageText = URLDecoder.decode(messageText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (messageService.createMessage(senderId, receiverId, messageText) == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{messageId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getMessage(@RequestParam("userId") Integer userId,
                                              @PathVariable("messageId") Integer messageId) {
        Message msg = messageService.getMessage(userId, messageId);
        if (msg == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessages(@RequestParam("senderId") Integer senderId,
                                                     @RequestParam("receiverId") Integer receiverId,
                                                     @RequestParam(value = "firstMessageId", required = false) Integer firstMessageId) {
        return new ResponseEntity<>(messageService.getMessages(senderId, receiverId, firstMessageId), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/{messageId}", method = RequestMethod.PUT)
    public ResponseEntity updateMessage(@RequestParam("userId") Integer userId,
                                        @PathVariable("messageId") Integer messageId,
                                        @RequestBody Message sourceMessage) {
        if (messageService.updateMessage(userId, messageId, sourceMessage))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(value = "/{messageId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMessage(@RequestParam("userId") Integer userId,
                                        @PathVariable("messageId") Integer messageId) {
        if (messageService.deleteMessage(userId, messageId))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
