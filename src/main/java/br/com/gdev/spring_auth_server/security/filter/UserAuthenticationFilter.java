package br.com.gdev.spring_auth_server.security.filter;

import br.com.gdev.spring_auth_server.infra.exception.TokenNotFoundException;
import br.com.gdev.spring_auth_server.model.dtos.Subject;
import br.com.gdev.spring_auth_server.model.dtos.Token;
import br.com.gdev.spring_auth_server.model.Users;
import br.com.gdev.spring_auth_server.model.repositories.UserRepository;
import br.com.gdev.spring_auth_server.security.ApplicationSpringAuthUserDetails;
import br.com.gdev.spring_auth_server.security.utils.SecurityInformationGetter;
import br.com.gdev.spring_auth_server.security.utils.SecurityUrlSettings;
import br.com.gdev.spring_auth_server.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter implements SecurityInformationGetter {
    private final UserRepository repository;
    private final JwtService service;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (checkIfEndpointIsNotPublic(request)){
            String token = recoveryBearerToken(request);

            System.out.println("IS VALID ==>  "+service.checkCode(token));
            if (token != null&&service.checkCode(token)){
                Subject subject = service.getSubject(new Token(null,token));
                Optional<Users> byEmail = repository.findByEmail(subject.subject());

                if (byEmail.isPresent()){
                    ApplicationSpringAuthUserDetails userDetails = new ApplicationSpringAuthUserDetails(byEmail.get());
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }else throw new TokenNotFoundException("token isn't present");
        }

        filterChain.doFilter(request,response);
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        System.out.println("URI: "+requestURI);
        return !Arrays.asList(SecurityUrlSettings.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}
