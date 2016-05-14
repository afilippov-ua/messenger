package com.filippov.messenger.controller.file;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadController {
    ResponseEntity<String> fileUpload(MultipartFile userImage);
}
