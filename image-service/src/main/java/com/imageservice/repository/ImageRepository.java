package com.imageservice.repository;

import com.imageservice.repository.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends  CrudRepository<Image, Long> {

    @Override
    Iterable<Image> findAll();

    @Override
    <S extends Image> S save(S s);
}
