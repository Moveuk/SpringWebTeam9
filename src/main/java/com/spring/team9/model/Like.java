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

    @Column(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Contents contents;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "comment_id")
//    private Comments comments;

    public Like(String username, Contents contents) {
        this.username = username;
        this.contents = contents;
    }

//    public Like(User user, Comments comments) {
//        this.user = user;
//        this.comments = comments;
//    }

}