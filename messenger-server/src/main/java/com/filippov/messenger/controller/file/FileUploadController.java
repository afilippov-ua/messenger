package com.filippov.messenger.controller.file;

import com.filippov.messenger.controller.user.UserController;
import com.filippov.messenger.entity.user.User;
import com.filippov.messenger.service.file.IFileUploadService;
import com.filippov.messenger.service.user.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping(value = "/api/files")
public class FileUploadController implements IFileUploadController {

    @Autowired
    private IUserService userService;

    @Autowired
    IFileUploadService fileUploadService;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Override
    @RequestMapping(value = "/avatars", method = RequestMethod.POST)
    public ResponseEntity<String> fileUpload(@RequestParam(value = "image") final MultipartFile uploadedFile) {
        logger.trace("/api/files (POST) - method 'fileUpload', uploadedFile size: " + uploadedFile.getSize() + ", uploadedFile name: " + uploadedFile.getOriginalFilename());

        if (!uploadedFile.getContentType().equalsIgnoreCase("image/jpeg")
                && !uploadedFile.getContentType().equalsIgnoreCase("image/png")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        if (user != null && !uploadedFile.isEmpty()) {
            String imageURL = fileUploadService.persistUserAvatar(user, uploadedFile);
            if (imageURL != null)
                return new ResponseEntity<>(imageURL, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}