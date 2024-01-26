package com.example.petfinder.model.entity;

import com.example.petfinder.model.enums.Role;
import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.token.AccessToken;
import com.example.petfinder.token.ConfirmationToken;
import com.example.petfinder.token.ResetPasswordToken;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birtDate;

    @Column(name = "biography", nullable = false)
    private String biography;

    @Column(name = "`password`", nullable = false)
    private String password;

    private Boolean isExpired;

    private Boolean isLocked;

    private Boolean isCredentialsExpired;

    private Boolean isEnabled;

    @OneToMany(
            mappedBy = "owner",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @ToString.Exclude
    @JsonManagedReference
    private Set<Animal> animals;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "sex", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AccessToken> tokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ResetPasswordToken> resetPasswordTokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ConfirmationToken> confirmationTokens;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    private Avatar avatar;

    public User(String email, String password, String firstName, String lastName, Role role, List<AccessToken> tokens) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.tokens = tokens;
        this.isExpired = false;
        this.isLocked = false;
        this.isCredentialsExpired = false;
        this.isEnabled = false;
    }

    public User(String email, String password, String firstName, String lastName, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isExpired = false;
        this.isLocked = false;
        this.isCredentialsExpired = false;
        this.isEnabled = true;
    }

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
