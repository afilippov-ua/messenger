package com.filippov.messenger.controller.file;

import com.filippov.messenger.controller.user.UserController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


@Controller
@RequestMapping(value = "/api/files")
public class FileUploadController implements IFileUploadController {

    @Value( "${image.upload.path}" )
    private String imagePath;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> fileUpload(@RequestParam(value = "image") final MultipartFile uploadedFile) {
        logger.trace("/api/files (POST) - method 'fileUpload', uploadedFile size: " + uploadedFile.getSize() + ", uploadedFile name: " + uploadedFile.getOriginalFilename());

        if (!uploadedFile.getContentType().equalsIgnoreCase("image/jpeg")
                && !uploadedFile.getContentType().equalsIgnoreCase("image/png")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!uploadedFile.isEmpty()) {
            try(BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(imagePath + "/")))) {

//                byte[] bytes = uploadedFile.getBytes();
//                stream.write(bytes);
//                logger.debug("Creating uploadedFile: " + filePath);
//                logger.debug("You successfully uploaded " + uploadedFile.getOriginalFilename() + "!");
                return new ResponseEntity<>("url/newImage.jpg", HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}