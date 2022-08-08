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

    private boolean isNotAlreadyLikeContent(String username, Contents contents) {
        return likeRepository.findByUsernameAndContents(username, contents).isEmpty();
    }

    public boolean likeContent(String username, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();

        if (isNotAlreadyLikeContent(username, contents)) {
            likeRepository.save(new Like(username, contents));
            return true;
        }
        return false;
    }

    //    private boolean isNotAlreadyLikeComment(User user, Comments comments) {
//        return likeRepository.findByUserAndComments(user, comments).isEmpty();
//    }

    public void disLikeContent(String username, Long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElseThrow();
        Like like = likeRepository.findByUsernameAndContents(username, contents).orElseThrow();
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