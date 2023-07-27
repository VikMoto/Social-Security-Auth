package com.chatico.authservice.repositiry;



import com.chatico.authservice.domain.Role;
import com.chatico.authservice.domain.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    @Query("select distinct u from UserChat u left join fetch u.roles where u.email=:email")
    @Query("select distinct u from UserChat u left join fetch u.roles where u.email = ?1")
    UserChat findByEmailFetchRoes(String email);
}
