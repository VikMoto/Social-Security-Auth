package com.chatico.authservice.service;


import com.chatico.authservice.domain.UserChat;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
//@RequiredArgsConstructor
public class EmailAuthProvider {
    private final UserchatService userchatService;
    private final PasswordEncoder passwordEncoder;

    public EmailAuthProvider(UserchatService userchatService, PasswordEncoder passwordEncoder) {
        this.userchatService = userchatService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserChat registerUser(String email, String password) {
        // Перевірка, чи користувач з вказаним email вже існує в системі
        if (userchatService.isUserExists(email)) {
            throw new RuntimeException("Користувач з таким email вже зареєстрований");
        }

        // Створення нового користувача
        UserChat userchat = new UserChat();
        userchat.setEmail(email);
        userchat.setPassword(passwordEncoder.encode(password));

        // Збереження користувача в базі даних
        return userchatService.saveUserChat(userchat);
    }

    public UserChat loginUser(String email, String password) {
        // Отримання користувача за email
        UserChat user = userchatService.getUserByEmail(email);

        // Перевірка пароля
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        throw new RuntimeException("Неправильні дані для входу");
    }
}
