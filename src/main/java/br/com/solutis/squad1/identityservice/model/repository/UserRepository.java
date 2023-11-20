package br.com.solutis.squad1.identityservice.model.repository;

import br.com.solutis.squad1.identityservice.model.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find UserDetails by username
     * @param username
     * @return UserDetails
     */
    UserDetails findByUsername(String username);

    /**
     * Find User by username
     * @param username
     * @return User
     */
    User findUserByUsername(String username);

    /**
     * Find User by username when deleted is false
     * @param username
     * @return User
     */
    User findUserByUsernameAndDeletedFalse(String username);

    /**
     * Find User with address by username when deleted is false
     * @param username
     * @return User
     */
    @Query(
            "SELECT u FROM User u LEFT JOIN FETCH u.address a WHERE u.deleted = false AND u.username = :username"
    )
    User findWithAddressByUsernameAndDeletedFalse(@Param("username") String username);
}
