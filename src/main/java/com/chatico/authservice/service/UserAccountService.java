package com.chatico.authservice.service;

import com.chatico.authservice.domain.Role;
import com.chatico.authservice.domain.RoleTypes;
import com.chatico.authservice.domain.UserChat;
import com.chatico.authservice.dto.UserchatRegDto;
import com.chatico.authservice.mapper.UserChatMapperReg;
import com.chatico.authservice.repositiry.UserChatRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.chatico.authservice.ulils.Constants.ADMIN_PASSWORD;
import static com.chatico.authservice.ulils.Constants.ADMIN_USERNAME;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService implements UserDetailsService {

//    private final UserChatRepository userChatRepository;
//    private final UserChatMapperReg userChatMapperReg;
//    private final BCryptPasswordEncoder passwordEncoder;

    @Value(value = "${" + ADMIN_USERNAME + "}")
    private String username;
    @Value(value = "${" + ADMIN_PASSWORD + "}")
    private String password;

    @PostConstruct
    private void setup() {
        log.info("started service:{} setup", UserAccountService.class.getSimpleName());
//        UserchatRegDto accountDto = UserchatRegDto.of(
//                username,
//                username,
//                passwordEncoder.encode(password),
//                null,
//                true,
//                true,
//                true,
//                true,
//                Arrays.stream(RoleTypes.values())
//                        .map(RoleTypes::name)
//                        .map(Role::new)
//                        .collect(Collectors.toSet()),
//                UserChat.Gender.MALE,
//                "uk_UA",
//                LocalDate.parse("1990-02-02")
//                );
//        userChatRepository.save(userChatMapperReg.mapDtoToEntity(accountDto));
        log.info("service:{} setup finished", UserAccountService.class.getSimpleName());

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserChat userChat = userChatRepository.findByEmailFetchRoes(username);
//        return (UserDetails) userChatMapperReg.mapEntityToDto(userChat);
//        return (UserDetails) userChatMapperReg.mapEntityToDto((UserChat) null);
        return (UserDetails)  null;
    }

    public UserchatRegDto createUser(UserDetails user) {
//        UserChat userAccountEntity = userChatMapperReg.mapDtoToEntity((UserchatRegDto) user);
//        encodePassword(userAccountEntity);
////        UserChat saved = userChatRepository.save(userAccountEntity);
//        return userChatMapperReg.mapEntityToDto((UserChat) null);
        return  null;
    }

    public void updateUser(UserDetails user) {
//        UserChat userAccountEntityByUsername = userChatRepository.findByEmailFetchRoes(user.getUsername());
//        UserChat userAccountEntity = userChatMapperReg.mapDtoToEntity((UserchatRegDto) user);
//        encodePassword(userAccountEntity);
//        BeanUtils.copyProperties(userAccountEntity, userAccountEntityByUsername);
//        userChatRepository.save(userAccountEntityByUsername);
    }

//    public void deleteUser(String username) {
//        userChatRepository.deleteByUsername(username);
//    }

    private void encodePassword(UserChat userChat) {
//        userChat.setPassword(passwordEncoder.encode(userChat.getPassword()));
    }

    public List<UserchatRegDto> all() {
//        return userChatMapperReg.mapEntityToDto(userChatRepository.findAll());
        return new ArrayList<>();
    }
}
