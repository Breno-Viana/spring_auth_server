package br.com.gdev.spring_auth_server.services;

import br.com.gdev.spring_auth_server.model.dtos.Subject;
import br.com.gdev.spring_auth_server.model.dtos.Token;
import br.com.gdev.spring_auth_server.security.ApplicationSpringAuthUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    Token generateUserDetailsToken(UserDetails details);

    Subject getSubject(Token token);

    String publicKey();

    Token genNew(Token token, ApplicationSpringAuthUserDetails principal);

    boolean checkCode(String codeValue);
}
