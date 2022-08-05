package com.spring.team9.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeRequestDto {
    private Long userId;
    private Long contentId;
    private Long commentId;

}
