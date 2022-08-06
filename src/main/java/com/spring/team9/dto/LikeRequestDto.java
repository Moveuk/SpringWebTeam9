package com.spring.team9.dto;

import com.spring.team9.model.Contents;
import com.spring.team9.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeRequestDto {
    private String username;
    private Contents contents;
//    private Comments commtents;



}