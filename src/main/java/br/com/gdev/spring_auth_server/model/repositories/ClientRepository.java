package br.com.gdev.spring_auth_server.model.repositories;

import br.com.gdev.spring_auth_server.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
