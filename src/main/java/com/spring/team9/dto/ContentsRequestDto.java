package com.spring.team9.dto;

import com.spring.team9.model.Contents;
import lombok.*;

@Getter
@Setter
@ToString
public class ContentsRequestDto {

    private Long userId;
    private String title;
    private String author;
    private String contents;
    private String imgUrl;

    public Contents toContents() {
        return Contents.builder()
                .userId(userId)
                .title(title)
                .author(author)
                .contents(contents)
                .imgUrl(imgUrl)
                .build();
    }
}