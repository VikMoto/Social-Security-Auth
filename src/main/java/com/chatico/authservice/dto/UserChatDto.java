package com.chatico.authservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;


@Builder
public record UserChatDto(Long id, String name, String userPic) {

}
