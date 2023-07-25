package com.chatico.authservice.mapper;


import com.chatico.authservice.domain.UserChat;
import com.chatico.authservice.dto.UserChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserChatMapper implements Mapper<UserChat, UserChatDto> {

    @Override
    public UserChatDto mapEntityToDto(UserChat source) throws RuntimeException {
        if (isNull(source)) {
            return null;
        }
        UserChatDto target = new UserChatDto(
                source.getId(),
                source.getUsername(),
                source.getUserPic());

        return target;
    }

//
    @Override
    public UserChat mapDtoToEntity(UserChatDto source) {
        if (isNull(source)) {
            return null;
        }
        UserChat target = new UserChat();

        target.setUsername(source.name());
        target.setId(source.id());
        target.setUserPic(source.userPic());

        return target;
    }


}
