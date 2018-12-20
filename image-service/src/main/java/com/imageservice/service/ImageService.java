package com.imageservice.service;

import com.imageservice.repository.ImageRepository;
import com.imageservice.repository.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
public class ImageService {

    @Autowired
    ImageRepository respository;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadImage(@RequestParam("fileName") MultipartFile uploadedFile, Principal principle) throws IOException {


        byte[] fileContents = uploadedFile.getBytes();

        String imageName = uploadedFile.getOriginalFilename();

        FileOutputStream file = new FileOutputStream("fileUploads" + File.separator + uploadedFile.getOriginalFilename());
        file.write(fileContents);
        file.close();

        Image image = new Image();
        image.setImageName(imageName);
        image.setImagePath(imageName);
       image.setUser(principle.getName());
        //Save the image infomation.

        respository.save(image);

        return "File uploaded successfully!";

    }


    @RequestMapping(value = "/images")
    public List<Image> getAllImages() {

        List<Image> images = new ArrayList<>();

        for (Image image : respository.findAll()) {

            images.add(image);

        }

        return images;

    }


    @RequestMapping(value = "/images/{image}")
    public String getImage(String image) {


        return "Uploaded successfully";


    }

}
