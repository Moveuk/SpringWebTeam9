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

    private boolean isNotAlreadyLikeContent(User user, Contents contents) {
        return likeRepository.findByUserAndContents(user, contents).isEmpty();
    }

    public boolean likeContent(User user, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();

        if (isNotAlreadyLikeContent(user, contents)) {
            likeRepository.save(new Like(user, contents));
            return true;
        }
        return false;
    }

    //    private boolean isNotAlreadyLikeComment(User user, Comments comments) {
//        return likeRepository.findByUserAndComments(user, comments).isEmpty();
//    }

    public void disLikeContent(User user, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();
        Like like = likeRepository.findByUserAndContents(user, contents).orElseThrow();
        likeRepository.delete(like);
    }

//    public boolean likeComment(User user, Long commentsId) {
//        Comments comments = commentsRepository.findById(commentsId).orElseThrow();
//
//        if(isNotAlreadyLike(user, comments)) {
//            likeRepository.save(new Like(user, comments));
//            return true;
//        }
//        return false;
//    }

//    public void disLikeComment(User user, Long commentsId) {
//        Comments comments = commentsRepository.findById(commentsId).orElseThrow();
//        Like like = likeRepository.findByUserAndComments(user, comments).orElseThrow();
//        likeRepository.delete(like);
//    }


}