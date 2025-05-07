package com.red.stevo.chemsales.Helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SaveImage {

    public String saveImage(MultipartFile file) {
        String fileName;
        String path = "src/main/resources/images";


        if (file.getName().equalsIgnoreCase("defaultImage")){
            fileName =  file.getName();

            if(new File(path+"/"+fileName).exists()) return path+"/"+fileName;

        }else if(containsUUID(file.getName())) {
            return  path+"/"+file.getName();
        }else
            fileName =  UUID.randomUUID() + file.getName();

        try {
            file.transferTo(new File(path, fileName));
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
