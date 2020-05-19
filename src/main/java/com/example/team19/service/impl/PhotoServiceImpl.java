package com.example.team19.service.impl;

import com.example.team19.dto.PhotoDTO;
import com.example.team19.model.Photo;
import com.example.team19.repository.PhotoRepository;
import com.example.team19.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public Photo save(Photo photo) {
        return photoRepository.save(photo);
    }
}
