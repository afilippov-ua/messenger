package com.filippov.messenger.service.file;

import com.filippov.messenger.entity.user.User;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
    String persistUserAvatar(User user, MultipartFile uploadedFile);

    boolean convertPngToJpg(String from, String to);
}
