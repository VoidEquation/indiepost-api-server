package com.indiepost.service;

import com.indiepost.model.ImageSet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by jake on 8/17/16.
 */
public interface ImageService {

    void save(ImageSet imageSet);

    List<ImageSet> saveUploadedImages(MultipartFile[] multipartFiles) throws IOException;

    ImageSet findById(Long id);

    List<ImageSet> findAll(int page, int maxResults);

    void update(ImageSet imageSet);

    void delete(ImageSet imageSet) throws IOException;

    Long deleteById(Long id) throws IOException;
}
