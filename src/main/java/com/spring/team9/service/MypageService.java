package com.spring.team9.service;

import com.spring.team9.dto.MypageResponseDto;
import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.model.Like;
import com.spring.team9.repository.CommentRepository;
import com.spring.team9.repository.ContentsRepository;
import com.spring.team9.repository.LikeRepository;
import com.spring.team9.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final ContentsRepository contentsRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public MypageResponseDto getMyPageList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userid = userDetails.getUser().getId();

        List<Contents> content = contentsRepository.findAllByUserId(userid);
        List<Comment> comment = commentRepository.findAllByUserIdAndParent(userid, null);
        List<Comment> reply = commentRepository.findAllByUserIdAndParentNotNull(userid);
        List<Like> contentLike = likeRepository.findAllByUserIdAndCommentCommentId(userid, null);
        List<Like> commentLike = likeRepository.findAllByUserIdAndContentsId(userid, null);

        return MypageResponseDto.builder()
                .contentList(content)
                .commentList(comment)
                .replyList(reply)
                .likeContentList(contentLike)
                .likeCommentList(commentLike)
                .build();
    }
}
