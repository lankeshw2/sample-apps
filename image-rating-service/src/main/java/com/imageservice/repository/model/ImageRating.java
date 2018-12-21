package com.imageservice.repository.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ImageRating {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  imageRatingId;

    private Long imageId;

    private double ratedValue;

    private String comment;

    private String userId;

    public Long getImageRatingId() {
        return imageRatingId;
    }

    public void setImageRatingId(Long imageRatingId) {
        this.imageRatingId = imageRatingId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public double getRatedValue() {
        return ratedValue;
    }

    public void setRatedValue(double ratedValue) {
        this.ratedValue = ratedValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
