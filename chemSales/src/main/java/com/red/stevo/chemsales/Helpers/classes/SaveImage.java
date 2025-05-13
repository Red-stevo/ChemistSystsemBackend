package com.red.stevo.chemsales.Helpers.classes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class SaveImage {

    public String saveImage(MultipartFile file) {
        String fileName;
        String path = "src/main/resources/images";
        System.out.println(file.getName());


        if (file.getOriginalFilename().toLowerCase().contains("defaultImage.png".toLowerCase())) {

            System.out.println("File name matched.");

            fileName = file.getOriginalFilename();

            try {
                if(new File(path + "/" + fileName).exists())
                    return path + "/" + fileName;
                else
                    log.info("Default Image does not exist.");

            } catch (Exception e) {
                log.info("Default image does not exist.");
            }
        } else if(containsUUID(file.getName())) {
            return  path+"/"+file.getOriginalFilename();
        }else
            fileName =  UUID.randomUUID() + file.getOriginalFilename();

        System.out.println("file name  :"+fileName);

        System.out.println("saving the file.");

        try {
            Files.copy(file.getInputStream(), Path.of(path).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return path+"/"+fileName;
    }


    private boolean containsUUID(String input) {
        Pattern pattern = Pattern.compile(
                "([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public void deleteImage(String path){
        new File(path).delete();
    }

}
