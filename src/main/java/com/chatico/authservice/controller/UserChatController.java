package com.chatico.authservice.controller;



import com.chatico.authservice.domain.Provider;
import com.chatico.authservice.domain.Role;
import com.chatico.authservice.domain.UserChat;
import com.chatico.authservice.dto.UserChatDto;
import com.chatico.authservice.dto.UserchatRegDto;
import com.chatico.authservice.repositiry.RoleRepository;
import com.chatico.authservice.repositiry.UserChatRepository;
import com.chatico.authservice.service.UserchatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Log4j2
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserChatController {
    private final static int DELAY = 100;
    private final UserChatRepository userChatRepository;
    private final RoleRepository roleRepository;
    private final UserchatService userchatService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/list")
    public List<UserChat> list() {
        return new ArrayList<>(userChatRepository.findAll());
    }

    @PostMapping()
    public UserChat createUserChat(@RequestBody UserchatRegDto userchatRegDto) {
        UserChat userChat = UserChat.builder()
                .username(userchatRegDto.getUsername())
                .email(userchatRegDto.getEmail())
                .password("{bcrypt}" + bCryptPasswordEncoder.encode(userchatRegDto.getPassword()))
                .userPic(userchatRegDto.getUserPic())
                .gender(userchatRegDto.getGender())
                .birthday(userchatRegDto.getBirthday())
                .locale(null)
                .lastVisit(LocalDateTime.now())
                .build();
        // Assuming you have a list of role names in the UserchatRegDto, you can add them to the userChat entity
        UserChat userChatSaved = userChatRepository.save(userChat);

        log.info("userChatSaved {}", userChatSaved);
        Set<Role> roleNames = userchatRegDto.getRoles();
        Set<Role> rolesToSave = new HashSet<>();

        if (roleNames != null) {
            rolesToSave = roleNames;
        }
        log.info("rolesToSave {}", rolesToSave);

        roleRepository.saveAll(rolesToSave);

        log.info("userChatSaved2 {}", userChatSaved);
        return userChatRepository.findById(userChatSaved.getId()).get();
    }

    @GetMapping("/{id}")
    public UserChatDto getUserChatWithMessages(@PathVariable("id")  Long id) throws InterruptedException {
        UserChat userChat = userChatRepository.findById(id).orElseThrow();
        UserChatDto userChatDto = UserChatDto.builder()
                .id(userChat.getId())
                .name(userChat.getUsername())
                .build();
        log.info("waiting {}ms", DELAY);
//        Thread.sleep(DELAY += 50);
        log.info("responding with error");
        return userChatDto;
//        throw new RuntimeException("Unexpected error");
    }
}
