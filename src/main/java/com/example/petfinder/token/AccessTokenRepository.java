package com.example.petfinder.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccessTokenRepository extends JpaRepository<AccessToken, UUID> {

    @Query(value = """
            SELECT t FROM AccessToken t INNER JOIN User u
            ON t.user.id = u.id
            WHERE u.email = :email AND (t.isExpired = false OR t.isRevoked = false)
            """)
    List<AccessToken> findAllValidTokensForUser(@Param("email") String email);

    Optional<AccessToken> findByToken(String token);

}
