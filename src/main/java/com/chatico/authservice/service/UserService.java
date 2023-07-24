//package com.chatico.authservice.service;
//
//
//import com.chatico.authservice.domain.Provider;
//import com.chatico.authservice.domain.UserChat;
//import com.chatico.authservice.repositiry.UserChatRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserChatService {
//
//	@Autowired
//	private UserChatRepository repo;
//
//	public void processOAuthPostLogin(String username) {
//		UserChat existUser = repo.findByEmailFetchRoes(username);
//
//		if (existUser == null) {
//			UserChat newUser = new UserChat();
//			newUser.setEmail(username);
//			newUser.setUsername(username);
//			newUser.setPassword("************************");
//			newUser.setProvider(Provider.GOOGLE);
//			newUser.setEnabled(true);
//
//			repo.save(newUser);
//
//			System.out.println("Created new user: " + username);
//		}
//
//	}
//
//	public boolean isUserExistsByFacebookId(String id) {
//
//	}
//}
