package com.red.stevo.chemsales.Helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class SaveImage {

    public String saveImage(MultipartFile file) {
        String path = "src/main/resources/images";
        String fileName =  UUID.randomUUID() + file.getOriginalFilename();

        try {
            file.transferTo(new File(path, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return path+"/"+fileName;
    }
}
