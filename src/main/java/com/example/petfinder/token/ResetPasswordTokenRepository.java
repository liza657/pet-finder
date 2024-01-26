package com.example.petfinder.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, UUID> {
    @Query(value = """
            SELECT t FROM ResetPasswordToken t INNER JOIN User u
            ON t.user.id = u.id
            WHERE u.email = :email AND (t.isExpired = false OR t.isRevoked = false)
            """)
    List<ResetPasswordToken> findAllValidTokensForUser(@Param("email") String email);

    Optional<ResetPasswordToken> findByToken(String token);

}
