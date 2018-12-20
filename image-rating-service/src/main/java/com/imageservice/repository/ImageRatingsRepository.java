package com.imageservice.repository;

import com.imageservice.repository.model.ImageRating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRatingsRepository extends  CrudRepository<ImageRating, Long> {

    @Override
    Iterable<ImageRating> findAll();

    @Override
    <S extends ImageRating> S save(S s);

    List<ImageRating> findByImageId(Long imageId);
}
