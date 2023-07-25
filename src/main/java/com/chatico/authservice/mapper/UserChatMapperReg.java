package com.chatico.authservice.mapper;


import com.chatico.authservice.domain.UserChat;
import com.chatico.authservice.dto.UserchatRegDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserChatMapperReg implements Mapper<UserChat, UserchatRegDto> {

    @Override
    public UserchatRegDto mapEntityToDto(UserChat source) throws RuntimeException {
        if (isNull(source)) {
            return null;
        }
        UserchatRegDto target = new UserchatRegDto();
        target.setUsername(source.getEmail());
        target.setPassword(source.getPassword());
        target.setIsAccountNonExpired(source.getIsAccountNonExpired());
        target.setIsAccountNonLocked(source.getIsAccountNonLocked());
        target.setIsCredentialsNonExpired(source.getIsCredentialsNonExpired());
        target.setIsEnabled(source.getIsEnabled());
        target.setRoles(source.getRoles());
        return target;
    }

//
    @Override
    public UserChat mapDtoToEntity(UserchatRegDto source) {
        if (isNull(source)) {
            return null;
        }
        UserChat target = new UserChat();
        target.setPassword(source.getPassword());
        target.setUsername(source.getUsername());
        target.setIsAccountNonExpired(source.getIsAccountNonExpired());
        target.setIsAccountNonLocked(source.getIsAccountNonLocked());
        target.setIsCredentialsNonExpired(source.getIsCredentialsNonExpired());
        target.setIsEnabled(source.getIsEnabled());
        target.setRoles(source.getRoles());
        return target;
    }


}
