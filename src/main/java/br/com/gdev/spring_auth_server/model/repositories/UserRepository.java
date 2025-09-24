package br.com.gdev.spring_auth_server.model.repositories;

import br.com.gdev.spring_auth_server.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

//    @Query("select u from Users u where u.email = :email")
    Optional<Users> findByEmail(String email);
}
