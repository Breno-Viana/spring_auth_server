package br.com.gdev.spring_auth_server.model.entities;


import br.com.gdev.spring_auth_server.model.utils.Scopes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false)
    @NotBlank
    private String client_name;

    @Column(nullable = false)
    @NotBlank
    private String client_id;

    @Column(nullable = false)
    @NotBlank
    private String client_secret;

    @Column(nullable = false)
    @NotBlank
    private String redirect_uri;

    @Column(nullable = false)
    private String scopes;

    @CreatedDate
    private LocalDateTime create_At;
}
