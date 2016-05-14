package com.filippov.messenger.service.file;

import com.filippov.messenger.controller.user.UserController;
import com.filippov.messenger.entity.user.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class FileUploadService implements IFileUploadService {

    @Value("${image.upload.path}")
    private String globalUploadImagePath;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Override
    public String persistUserAvatar(User user, MultipartFile uploadedFile) {
        String fileNameJPG = user.getId() + "_original.jpg";
        String filePathJPG = globalUploadImagePath + "/" + fileNameJPG;
        if (uploadedFile.getContentType().equalsIgnoreCase("image/jpeg")) {
            if (persistJPGImage(filePathJPG, uploadedFile))
                return "/app/resources/images/avatars/" + fileNameJPG;
        } else if (uploadedFile.getContentType().equalsIgnoreCase("image/png")) {
            String fileNamePNG = user.getId() + "_original.png";
            String filePathPNG = globalUploadImagePath + "/" + fileNamePNG;
            if (persistPNGImage(filePathPNG, filePathJPG, uploadedFile))
                return "/app/resources/images/avatars/" + fileNameJPG;
        }
        return null;
    }

    private boolean persistPNGImage(String filePathPNG, String filePathJPG, MultipartFile uploadedFile) {
        try (OutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(new File(filePathPNG)))) {
            byte[] bytes = uploadedFile.getBytes();
            stream.write(bytes);
            if (convertPngToJpg(filePathPNG, filePathJPG)) {
                logger.debug(String.format("File: %s uploaded to: %s", uploadedFile.getOriginalFilename(), filePathJPG));
                if (new File(filePathPNG).delete()) {
                    logger.debug(String.format("File: %s uploaded to: %s", uploadedFile.getOriginalFilename(), filePathJPG));
                } else {
                    logger.error(String.format("File deleted error: %s", filePathPNG));
                }
                return true;
            }
        } catch (Exception e) {
            logger.error(String.format("Upload file IO error (fileName: '%s', file size: %d)", uploadedFile.getOriginalFilename(), uploadedFile.getSize()), e);
        }
        return false;
    }

    private boolean persistJPGImage(String filePathJPG, MultipartFile uploadedFile) {
        try (OutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(new File(filePathJPG)))) {
            byte[] bytes = uploadedFile.getBytes();
            stream.write(bytes);
            logger.debug(String.format("File: %s uploaded to: %s", uploadedFile.getOriginalFilename(), filePathJPG));
            return true;
        } catch (Exception e) {
            logger.error(String.format("persist JPG file error (fileName: '%s', file size: %d)", uploadedFile.getOriginalFilename(), uploadedFile.getSize()), e);
            return false;
        }
    }

    @Override
    public boolean convertPngToJpg(String from, String to) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(from));
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            ImageIO.write(newBufferedImage, "jpg", new File(to));
            return true;
        } catch (IOException e) {
            logger.error(String.format("Error PNG to JPG converting from: %s to %s", from, to), e);
            return false;
        }
    }

    @PostConstruct
    private void initGlobalFilesDirectories() {
        File pathDirFile = new File(globalUploadImagePath);
        pathDirFile.mkdirs();
    }
}
