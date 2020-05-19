package com.example.team19.repository;

import com.example.team19.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository  extends JpaRepository<Photo,Long> {
}
