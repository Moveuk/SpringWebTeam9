package com.spring.team9.dto;

import com.spring.team9.model.Contents;
import lombok.Getter;

@Getter
public class LikeRequestDto {
    private Long userId;
    private Contents contents;



}