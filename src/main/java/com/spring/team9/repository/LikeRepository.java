package com.spring.team9.repository;

import com.spring.team9.model.Contents;
import com.spring.team9.model.Like;
import com.spring.team9.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUsernameAndContents(String username, Contents contents);
//    Optional<Like> findByUserAndComments(User user, Comments comments);

}