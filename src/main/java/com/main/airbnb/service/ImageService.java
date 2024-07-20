package com.main.airbnb.service;

import com.main.airbnb.entity.Image;
import com.main.airbnb.entity.Property;
import com.main.airbnb.exception.PropertyNotFoundException;
import com.main.airbnb.payload.ImageDto;
import com.main.airbnb.repository.ImageRepository;
import com.main.airbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImageService {

    private ImageRepository imageRepository;
    private PropertyRepository propertyRepository;
    private S3Service s3Service;


    @Autowired
    public ImageService(ImageRepository imageRepository, PropertyRepository propertyRepository, S3Service s3Service) {
        this.imageRepository = imageRepository;
        this.propertyRepository = propertyRepository;
        this.s3Service = s3Service;
    }


    public ImageDto addImageForProperty(String propertyId, String fileUrl,String fileName) {
         Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new PropertyNotFoundException("Property Not Found")
        );
         Image image = imageDtoToEntity(property, fileUrl,fileName);
         Image savedImage = imageRepository.save(image);
         ImageDto imageDto = imageEntityToDto(savedImage);
         return imageDto;
    }







    private ImageDto imageEntityToDto(Image savedImage) {
        ImageDto image = new ImageDto();
        image.setId(savedImage.getId());
        image.setFileName(savedImage.getFileName());
        image.setPropertyName(savedImage.getProperty().getPropertyName());
        image.setImageUrl(savedImage.getImageUrl());
        return image;
    }

    private Image imageDtoToEntity(Property property, String fileUrl,String fileName) {
        Image image = new Image();
        String imageId = UUID.randomUUID().toString();
        image.setId(imageId);
        image.setFileName(fileName);
        image.setProperty(property);
        image.setImageUrl(fileUrl);
        return image;
    }

    public String deleteImage(String imageId) {
         Image image = imageRepository.findById(imageId).get();
         String fileName = image.getFileName();
         String msg = s3Service.deleteImage(fileName);
        imageRepository.deleteById(imageId);
        return msg;
    }
}
