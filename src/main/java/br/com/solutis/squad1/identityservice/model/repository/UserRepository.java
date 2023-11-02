package br.com.solutis.squad1.identityservice.model.repository;

import br.com.solutis.squad1.identityservice.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    User findUserByUsername(String username);

    User findUserByUsernameAndDeletedFalse(String username);

    List<User> findAllByDeletedFalse();

    @Query(
            "SELECT u FROM User u LEFT JOIN FETCH u.address a WHERE u.deleted = false AND u.username = :username"
    )
    User findWithAddressByUsernameAndDeletedFalse(@Param("username") String username);
}
