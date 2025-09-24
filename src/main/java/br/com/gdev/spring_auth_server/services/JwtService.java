package br.com.gdev.spring_auth_server.services;

import br.com.gdev.spring_auth_server.model.dtos.Subject;
import br.com.gdev.spring_auth_server.model.dtos.Token;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    Token generateToken(UserDetails details);

    Subject getSubject(Token token);
}
