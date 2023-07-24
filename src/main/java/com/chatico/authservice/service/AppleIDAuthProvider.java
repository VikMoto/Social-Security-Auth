package com.chatico.authservice.service;


import com.chatico.authservice.domain.AppleIDAuthClient;
import com.chatico.authservice.domain.AppleIDUserInfo;
import com.chatico.authservice.domain.UserChat;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class AppleIDAuthProvider {
    private final UserchatService userchatService;
    private final AppleIDAuthClient appleIDAuthClient;

    public AppleIDAuthProvider(UserchatService userchatService, AppleIDAuthClient appleIDAuthClient) {
        this.userchatService = userchatService;
        this.appleIDAuthClient = appleIDAuthClient;
    }

    public UserChat registerUser(String appleIDAuthorizationCode) {
        // Отримання інформації про користувача з Apple
        AppleIDUserInfo appleIDUserInfo = appleIDAuthClient.getUserInfo(appleIDAuthorizationCode);

        // Перевірка, чи користувач з Apple ID вже існує в системі
        if (userchatService.isUserExistsByAppleID(appleIDUserInfo.getId())) {
            throw new RuntimeException("Користувач з Apple ID вже зареєстрований");
        }

        // Створення нового користувача
        UserChat userChat = new UserChat();
        userChat.setAppleID(appleIDUserInfo.getId());
        userChat.setEmail(appleIDUserInfo.getEmail());
        userChat.setUsername(appleIDUserInfo.getName());

        // Збереження користувача в базі даних
        return userchatService.saveUserChat(userChat);
    }

    public UserChat loginUser(String appleIDAuthorizationCode) {
        // Отримання інформації про користувача з Apple
        AppleIDUserInfo appleIDUserInfo = appleIDAuthClient.getUserInfo(appleIDAuthorizationCode);

        // Перевірка, чи користувач з Apple ID вже існує в системі
        UserChat userChat = userchatService.getUserByAppleID(appleIDUserInfo.getId());
        if (userChat != null) {
            return userChat;
        }

        throw new RuntimeException("Користувач не знайдений");
    }
}
