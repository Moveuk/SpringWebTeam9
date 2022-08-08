package com.spring.team9.repository;

import com.spring.team9.model.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ContentsRepository extends JpaRepository<Contents, Long> {
    List<Contents> findAllByOrderByCreatedAtDesc();

}