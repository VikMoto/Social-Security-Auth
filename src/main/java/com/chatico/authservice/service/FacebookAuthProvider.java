package com.chatico.authservice.service;

import com.chatico.authservice.domain.FacebookAuthClient;
import com.chatico.authservice.domain.FacebookUserInfo;
import com.chatico.authservice.domain.UserChat;
import org.springframework.stereotype.Service;

@Service

public class FacebookAuthProvider {
    private final UserchatService userChatService;
    private final FacebookAuthClient facebookAuthClient;

    public FacebookAuthProvider(UserchatService userChatService, FacebookAuthClient facebookAuthClient) {
        this.userChatService = userChatService;
        this.facebookAuthClient = facebookAuthClient;
    }

    public UserChat registerUser(String facebookAccessToken) {
        // Отримання інформації про користувача з Facebook
        FacebookUserInfo facebookUserInfo = facebookAuthClient.getUserInfo(facebookAccessToken);

        // Перевірка, чи користувач з Facebook ID вже існує в системі
        if (userChatService.isUserExistsByFacebookId(facebookUserInfo.getId())) {
            throw new RuntimeException("Користувач з Facebook ID вже зареєстрований");
        }

        // Створення нового користувача
        UserChat userChat = new UserChat();
        userChat.setFacebookId(facebookUserInfo.getId());
        userChat.setEmail(facebookUserInfo.getEmail());
        userChat.setUsername(facebookUserInfo.getName());

        // Збереження користувача в базі даних
        return userChatService.saveUserChat(userChat);
    }

    public UserChat loginUser(String facebookAccessToken) {
        // Отримання інформації про користувача з Facebook
        FacebookUserInfo facebookUserInfo = facebookAuthClient.getUserInfo(facebookAccessToken);

        // Перевірка, чи користувач з Facebook ID вже існує в системі
        UserChat user = userChatService.getUserByFacebookId(facebookUserInfo.getId());
        if (user != null) {
            return user;
        }

        throw new RuntimeException("Користувач не знайдений");
    }
}
