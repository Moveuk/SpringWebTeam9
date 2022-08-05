package com.spring.team9.model;

import com.spring.team9.dto.LikeRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "comment_id")
    private Long commentId;

    @Builder
    public Like(Long userId, Long contentId, Long commentId) {
        this.userId = userId;
        this.contentId = contentId;
        this.commentId = commentId;
    }

}
