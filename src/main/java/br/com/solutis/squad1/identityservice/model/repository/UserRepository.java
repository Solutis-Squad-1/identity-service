package br.com.solutis.squad1.identityservice.model.repository;

import br.com.solutis.squad1.identityservice.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    User findUserByUsername(String username);

    List<User> findAllByDeletedFalse();
}
