package br.com.gdev.spring_auth_server.model.repositories;

import br.com.gdev.spring_auth_server.model.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

//    @Query("select u from Users u where u.email = :email")
    Optional<Users> findByEmail(String email);


    void deleteUsersByIdOrEmail(String id, @NotBlank(message = "email must not be blank") @Email String email);
}
