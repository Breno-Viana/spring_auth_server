package br.com.gdev.spring_auth_server.security;

import br.com.gdev.spring_auth_server.infra.exception.BadCredentialException;
import br.com.gdev.spring_auth_server.model.entities.Users;
import br.com.gdev.spring_auth_server.security.models.ApplicationSpringAuthUserDetails;
import br.com.gdev.spring_auth_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationUserDetailsServiceImpl implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users userByEmail = service.getUserByEmail(email);
        if (userByEmail == null) throw new BadCredentialException("bad credentials");
        return new ApplicationSpringAuthUserDetails(userByEmail);

    }
}
