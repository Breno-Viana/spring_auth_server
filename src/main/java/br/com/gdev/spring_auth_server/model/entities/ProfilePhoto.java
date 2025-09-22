package br.com.gdev.spring_auth_server.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfilePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String file_name;

    private String file_path;

    @OneToOne(mappedBy = "profile_photo")
    private Users user;
}
