package com.example.petfinder.token;

import com.example.petfinder.model.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString()
@Table(name = "`reset_password_token`")
@Entity
public class ResetPasswordToken {

    @Id
    @UuidGenerator
    private UUID id;
    @Column(length = 600)

    private String token;

    private Boolean isRevoked;

    private Boolean isExpired;

    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @JsonBackReference
    private User user;


    public ResetPasswordToken(String token, Boolean isRevoked, Boolean isExpired, User user) {
        this.token = token;
        this.isRevoked = isRevoked;
        this.isExpired = isExpired;
        this.user = user;
        this.createdAt = LocalDate.now();
    }


    public ResetPasswordToken() {
        this.createdAt = LocalDate.now();
    }
}
