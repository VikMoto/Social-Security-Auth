package com.chatico.authservice.repositiry;



import com.chatico.authservice.domain.UserChat;
import com.fasterxml.jackson.databind.DatabindContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserChatRepository extends JpaRepository<UserChat, Long> {
//    @Query("select distinct u from UserChat u left join fetch u.roles where u.email=:email")
    @Query("select distinct u from UserChat u left join fetch u.roles where u.email = ?1")
    UserChat findByEmailFetchRoes(String email);

    @Query("select  u.AppleID from UserChat u where u.AppleID =:id")
    UserChat findByAppleId(String  id);
    @Query("select  u.FacebookId from UserChat u where u.FacebookId =:id")
    UserChat findByFacebookId(String id);

    @Query("select  u.GoogleId from UserChat u where u.GoogleId =:id")
    UserChat findByGoogleId(String id);
}
