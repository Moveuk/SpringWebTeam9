package com.spring.team9.service;

import com.spring.team9.dto.LikeRequestDto;
import com.spring.team9.model.Contents;
import com.spring.team9.model.Like;
import com.spring.team9.model.User;
import com.spring.team9.repository.ContentsRepository;
import com.spring.team9.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final ContentsRepository contentsRepository;

    public boolean likecontent(User user, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();

        if(isNotAlreadyLikeContent(user, contents)) {
            likeRepository.save(new Like(user, contents));
            return true;
        }
        return false;
    }

//    public boolean likecomment(User user, Long commentsId) {
//        Contents contents = contentsRepository.findById(commentsId).orElseThrow();
//
//        if(isNotAlreadyLike(user, comments)) {
//            likeRepository.save(new Like(user, comments));
//            return true;
//        }
//        return false;
//    }

    private boolean isNotAlreadyLikeContent(User user, Contents contents) {
        return likeRepository.findByUserAndContents(user, contents).isEmpty();
    }

//    private boolean isNotAlreadyLikeComment(User user, Comments comments) {
//        return likeRepository.findByUserAndComments(user, comments).isEmpty();
//    }


}