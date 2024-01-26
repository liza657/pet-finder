package com.example.petfinder.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, UUID> {
    ConfirmationToken findByToken(String confirmationToken);
    @Query(value = """
            SELECT t FROM ConfirmationToken t INNER JOIN User u
            ON t.user.id = u.id
            WHERE u.email = :email AND (t.isExpired = false OR t.isRevoked = false)
            """)
    List<ConfirmationToken> findAllValidTokensForUser(@Param("email") String email);
}
