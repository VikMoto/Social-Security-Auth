package com.chatico.authservice.repositiry;


import com.chatico.authservice.domain.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactsRepository extends JpaRepository<UserChat, Long> {
}
