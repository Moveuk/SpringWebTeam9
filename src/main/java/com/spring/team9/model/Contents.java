package com.spring.team9.model;

import com.spring.team9.dto.ContentsRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.

public class Contents extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 반드시 값을 가지도록 합니다.
    @Column(name = "content_title", nullable = false)
    private String title;

    @Column(name = "content_author", nullable = false)
    private String author;

    @Column(name = "content_contents", nullable = false)
    private String contents;

    @Column(name = "content_imgurl")
    private String imgUrl;


    //img를 포함한 게시글
    @Builder
    public Contents(Long userId, String title, String author, String contents, String imgUrl) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.imgUrl = imgUrl;
    }

    public void update(String title, String author, String contents, String imgUrl) {
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.imgUrl = imgUrl;
    }
}