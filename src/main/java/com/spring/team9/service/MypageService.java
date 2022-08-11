package com.spring.team9.service;

import com.spring.team9.dto.MypageResponseDto;
import com.spring.team9.model.Comment;
import com.spring.team9.model.Contents;
import com.spring.team9.repository.CommentRepository;
import com.spring.team9.repository.ContentsRepository;
import com.spring.team9.repository.LikeRepository;
import com.spring.team9.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Contents> contentLikeList = likeRepository.findAllByUserIdAndCommentCommentId(userid, null).stream()
                        .map(like -> contentsRepository.findById(like.getContents().getId())
                                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")))
                .collect(Collectors.toList());

        List<Comment> commentLikeList = likeRepository.findAllByUserIdAndContentsId(userid, null).stream()
                .map(like -> commentRepository.findByCommentId(like.getComment().getCommentId())
                        .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")))
                .collect(Collectors.toList());

        return MypageResponseDto.builder()
                .contentList(content)
                .commentList(comment)
                .replyList(reply)
                .likeContentList(contentLikeList)
                .likeCommentList(commentLikeList)
                .build();
    }
}
