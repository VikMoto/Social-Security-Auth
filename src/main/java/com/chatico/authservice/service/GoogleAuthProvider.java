package com.chatico.authservice.service;

import com.chatico.authservice.dto.GoogleAuthClient;
import com.chatico.authservice.dto.GoogleUserInfo;
import com.chatico.authservice.domain.UserChat;
import org.springframework.stereotype.Service;

@Service

public class GoogleAuthProvider {
    private final UserchatService userChatService;
    private final GoogleAuthClient googleAuthClient;

    public GoogleAuthProvider(UserchatService userChatService, GoogleAuthClient googleAuthClient) {
        this.userChatService = userChatService;
        this.googleAuthClient = googleAuthClient;
    }

    public UserChat registerUser(String googleIdToken) {
        // Отримання інформації про користувача з Google
        GoogleUserInfo googleUserInfo = googleAuthClient.getUserInfo(googleIdToken);

        // Перевірка, чи користувач з Google ID вже існує в системі
        if (userChatService.isUserExistsByGoogleId(googleUserInfo.getId())) {
            throw new RuntimeException("Користувач з Google ID вже зареєстрований");
        }

        // Створення нового користувача
        UserChat user = new UserChat();
        user.setGoogleId(googleUserInfo.getId());
        user.setEmail(googleUserInfo.getEmail());
        user.setUsername(googleUserInfo.getName());

        // Збереження користувача в базі даних
        return userChatService.saveUserChat(user);
    }

    public UserChat loginUser(String googleIdToken) {
        // Отримання інформації про користувача з Google
        GoogleUserInfo googleUserInfo = googleAuthClient.getUserInfo(googleIdToken);

        // Перевірка, чи користувач з Google ID вже існує в системі
        UserChat user = userChatService.getUserByGoogleId(googleUserInfo.getId());
        if (user != null) {
            return user;
        }

        throw new RuntimeException("Користувач не знайдений");
    }
}
