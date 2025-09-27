package br.com.gdev.spring_auth_server.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


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

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn
    private Users owner;
}
