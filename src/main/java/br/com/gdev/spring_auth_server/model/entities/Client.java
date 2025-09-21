package br.com.gdev.spring_auth_server.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String client_id;

    @Column(nullable = false)
    private String client_secret;

    @Column(nullable = false)
    private String redirect_uri;

    @Column
    private String scopes;
}
