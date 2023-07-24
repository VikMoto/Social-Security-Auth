package com.chatico.authservice.dto;


import lombok.Builder;

@Builder
public record UserChatDto(Long id, String name) {

}
