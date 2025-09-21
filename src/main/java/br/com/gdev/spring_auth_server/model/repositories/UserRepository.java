package br.com.gdev.spring_auth_server.model.repositories;

import br.com.gdev.spring_auth_server.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
}
