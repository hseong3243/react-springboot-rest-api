package com.programmers.ticketing.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageRepository {

    @Value("${file.dir}")
    private String fileDir;

    public String uploadImage(MultipartFile image) throws IOException {
        String ext = extractExt(image.getOriginalFilename());
        String randomFileName = UUID.randomUUID().toString();
        String storeFileName = randomFileName + "." + ext;
        image.transferTo(new File(fileDir + storeFileName));
        return storeFileName;
    }

    private String extractExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos + 1);
    }
}
