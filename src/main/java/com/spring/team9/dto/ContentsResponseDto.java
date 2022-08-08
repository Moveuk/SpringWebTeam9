package com.spring.team9.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.team9.model.Contents;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentsResponseDto {
    private Long id;
    private String title;
    private String name;
    private String contents;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    private int countLike;

    @Builder
    public ContentsResponseDto(Contents content, int countLike) {
        this.id = content.getId();
        this.title = content.getTitle();
        this.name = content.getAuthor();
        this.contents = content.getContents();
        this.modifiedAt = content.getModifiedAt();
        this.countLike = countLike;
    }


}
