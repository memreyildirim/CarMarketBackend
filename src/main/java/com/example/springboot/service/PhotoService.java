package com.example.springboot.service;

import com.example.springboot.model.Car;
import com.example.springboot.model.Photo;
import com.example.springboot.repository.CarRepository;
import com.example.springboot.repository.PhotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PhotoService {

    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    private String uploadDir = "C:/Users/mey19/uploads";
    ;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private CarRepository carRepository;



    public Photo savePhoto(MultipartFile file, Long carId) throws IOException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path imagePath = Paths.get(uploadDir).resolve(fileName);
        Files.createDirectories(Paths.get(uploadDir));
        Files.write(imagePath, file.getBytes());

        String publicUrl = "/uploads/" + fileName; // ← sadece bu satır değişti

        Photo photo = new Photo();
        photo.setFilePath(publicUrl);
        photo.setUploadedAt(LocalDateTime.now());
        photo.setCar(car);

        return photoRepository.save(photo);

    }


    public Photo findPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        photoRepository.deleteById(id);
    }


}
