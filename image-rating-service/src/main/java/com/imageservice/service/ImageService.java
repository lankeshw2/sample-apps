package com.imageservice.service;

import com.imageservice.repository.ImageRatingsRepository;
import com.imageservice.repository.model.ImageRating;
import com.imageservice.service.dto.AverageRatingByImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ImageService {

    @Autowired
    ImageRatingsRepository repository;

    @RequestMapping(value = "/ratings/{imageId}", method = RequestMethod.GET, produces = "application/json")
    public List<ImageRating> getAllRatingsForImage(@PathVariable("imageId") Long imageId) throws IOException {


        return repository.findByImageId(imageId);

    }

    @RequestMapping(value = "/ratings/average/{imageId}", method = RequestMethod.GET)
    public double getAverageRatingForImage(@PathVariable("imageId") Long imageId) throws IOException {


        return getAverageRatingByImageId(imageId);

    }

    private double getAverageRatingByImageId(@PathVariable("imageId") Long imageId) {
        List<ImageRating> imageRatings = repository.findByImageId(imageId);


        double average = imageRatings.stream().mapToDouble(ImageRating::getRatedValue).average().orElse(0.0);


        return average;
    }


    @RequestMapping(value = "/ratings/submit/{imageId}", method = RequestMethod.POST, consumes = "application/json")
    public String saveRating(@PathVariable("imageId") Long imageId, @RequestBody ImageRating rating, Principal principal) throws IOException {

        rating.setImageId(imageId);
        rating.setUserId(principal.getName());

        repository.save(rating);


        return "Your rating was saved";

    }


    @RequestMapping(value = "/ratings/top", method = RequestMethod.GET, produces = "application/json")
    public List<AverageRatingByImage> getTopRatedImages() {


        Iterable<ImageRating> ratings = repository.findAll();
        List<ImageRating> imageRatingList = new ArrayList<>();
        ratings.forEach(rating -> imageRatingList.add(rating));

        //Group by image id calculate the average rating for each image.
        Map<Long, Double> imageIdByAverageRating = imageRatingList.stream().collect(Collectors.groupingBy(ImageRating::getImageId, Collectors.averagingDouble(ImageRating::getRatedValue)));


        Map<Integer, Double> imageIdToAverageRatingMap = new HashMap<>();

        List<AverageRatingByImage> topRatings = new ArrayList<>();

        imageIdByAverageRating.entrySet().stream().sorted(Map.Entry.comparingByValue((o1, o2) -> o2.compareTo(o1))).forEach(entry -> {
            AverageRatingByImage image = new AverageRatingByImage();
            image.setImageId(entry.getKey());
            image.setRating(entry.getValue());
            topRatings.add(image);
        });


        return topRatings;

    }


}
