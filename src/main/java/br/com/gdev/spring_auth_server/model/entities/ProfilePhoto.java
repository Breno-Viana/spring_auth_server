package br.com.gdev.spring_auth_server.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "user")
public class ProfilePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String file_name;

    private String file_path;

    @OneToOne(mappedBy = "profilePhoto")
    private Users user;
}
