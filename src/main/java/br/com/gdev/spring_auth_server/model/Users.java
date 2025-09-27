package br.com.gdev.spring_auth_server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "USERS")
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "username must not no be blank")
    private String userName;

    @NotBlank(message = "pass must not be blank")
    @Size(min = 8, message = "pass must not be less than 8 length")
    private String passHash;

    @Column(unique = true)
    @NotBlank(message = "email must not be blank")
    @Email
    private String email;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            optional = true
    )
    @JoinColumn(nullable = true)
    private ProfilePhoto profilePhoto;

    @NotNull
    private LocalDate birthDate;

    @Column
    private String role;

    @Column
    @NotNull
    @CreatedDate
    private LocalDateTime create_at;



}
