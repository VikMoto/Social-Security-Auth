package com.chatico.authservice.service;




import com.chatico.authservice.domain.UserChat;
import com.chatico.authservice.dto.UserChatDto;
import com.chatico.authservice.repositiry.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserchatService {
    private final UserChatRepository userChatRepository;


    public UserChat saveUserChat (UserChat userChat) {
        UserChat saved = userChatRepository.save(userChat);
        return saved;
    }
    public boolean isUserExists(String email) {
        return userChatRepository.findByEmailFetchRoes(email).isEnabled();
    }

    public UserChat getUserByEmail(String email) {
        return userChatRepository.findByEmailFetchRoes(email);
    }

    public boolean isUserExistsByAppleID(String id) {
        return userChatRepository.findByAppleId(id).isEnabled();
    }

    public UserChat getUserByAppleID(String id) {
        return userChatRepository.findByAppleId(id);
    }

    public boolean isUserExistsByFacebookId(String id) {
       return userChatRepository.findByFacebookId(id).isEnabled();
    }

    public UserChat getUserByFacebookId(String id) {
        return userChatRepository.findByFacebookId(id);
    }

    public boolean isUserExistsByGoogleId(String id) {
       return userChatRepository.findByGoogleId(id).isEnabled();
    }

    public UserChat getUserByGoogleId(String id) {
        return userChatRepository.findByGoogleId(id);
    }
}
