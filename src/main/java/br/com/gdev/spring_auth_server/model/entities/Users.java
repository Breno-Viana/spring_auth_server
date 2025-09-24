package br.com.gdev.spring_auth_server.model.entities;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "USERS")
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "username must not no be blank")
    private String username;

    @NotBlank(message = "pass must not be blank")
    @Size(min = 8, message = "pass must not be less than 8 length")
    private String pass_hash;

    @Column(unique = true)
    @NotBlank(message = "email must not be blank")
    @Email
    private String email;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn
    private ProfilePhoto profile_photo;

    @NotNull
    private LocalDate birth_day;

    @Column
    private String role;

    @Column
    @NotNull
    @CreatedDate
    private LocalDateTime create_at;
}
