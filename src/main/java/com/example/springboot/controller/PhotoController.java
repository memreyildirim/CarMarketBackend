package com.example.springboot.controller;

import com.example.springboot.model.Car;
import com.example.springboot.model.Photo;
import com.example.springboot.service.CarService;
import com.example.springboot.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4160")
@RestController
@RequestMapping("/api/v1/photo")
public class PhotoController {

    private static final Logger logger = Logger.getLogger(PhotoController.class.getName());


    @Autowired
    private PhotoService photoService;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Photo upload(@RequestParam("file") MultipartFile file,
                        @RequestParam("carId") Long carId) throws IOException {
        return photoService.savePhoto(file, carId);
    }

/*    @PostMapping(value = "/upload")
    public Photo upload(@RequestBody Photo photo) throws IOException {
        logger.info("Uploading photo");
        return null;
        //return photoService.savePhoto(file, photo.getCar());
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhoto(@PathVariable Long id) {
        Photo photo = photoService.findPhotoById(id);
        return ResponseEntity.ok(photo);
    }

}
