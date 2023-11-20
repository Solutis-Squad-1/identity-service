package br.com.solutis.squad1.identityservice.model.repository;

import br.com.solutis.squad1.identityservice.model.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    /**
     * Delete all OTPs by username
     * @param username
     */
    @Modifying
    @Query(
            "UPDATE Otp o SET o.deleted = true, o.deletedAt = CURRENT_TIMESTAMP WHERE o.deleted = false AND o.user.username = :username"
    )
    void deleteAllByUsername(@Param("username") String username);

    /**
     * Find OTP by code and username
     * @param code
     * @param username
     * @return Otp
     */
    @Query(
            "SELECT o FROM Otp o WHERE o.deleted = false AND o.user.username = :username AND o.code = :code"
    )
    Otp findByCodeAndUsername(@Param("code") String code, @Param("username") String username);
}
