package br.com.gdev.spring_auth_server.services.impl;

import br.com.gdev.spring_auth_server.config.KeyConfigure;
import br.com.gdev.spring_auth_server.infra.exception.TokenNotFoundException;
import br.com.gdev.spring_auth_server.model.dtos.Subject;
import br.com.gdev.spring_auth_server.model.dtos.Token;
import br.com.gdev.spring_auth_server.security.models.ApplicationSpringAuthUserDetails;
import br.com.gdev.spring_auth_server.services.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.*;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ApplicationAuthServerJwtServiceImpl implements JwtService {
    private final KeyConfigure keyConfigure;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    private final String ISSUER = "gdev_auth_server";

    @Override
    public Token generateToken(UserDetails details) {
        String token = "";
        if (details instanceof ApplicationSpringAuthUserDetails user) {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            List<String> list = authorities.stream().map(GrantedAuthority::getAuthority).toList();
            System.out.printf("AUTHORITIES OF %s: %s%n",user.getDbUsername(), list);
            String[] array = list.toArray(new String[0]);
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getUsername())
                    .withClaim("name", user.getDbUsername())
                    .withArrayClaim("roles",array)
                    .withClaim("profile-photo", user.getProfilePhoto().getFile_path())
                    .withClaim("birth", user.getBirthDate().toString())
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        }

        return new Token(token);
    }

    @Override
    public Subject getSubject(Token token) {
      try{
          Algorithm algorithm = Algorithm.RSA256(publicKey, null);
          String subject = JWT.require(algorithm)
                  .withIssuer(ISSUER)
                  .build()
                  .verify(token.token())
                  .getSubject();

          return new Subject(subject);
      }catch (Exception e){
          throw new TokenNotFoundException("invalid or expired token");
      }
    }

    @Override
    public String publicKey(){
        return keyConfigure.getPublic_key_content();
    }


    @PostConstruct
    private void init() throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.publicKey = keyConfigure.getRSAPublicKey();
        this.privateKey = keyConfigure.getRSAPrivateKey();
    }


    private Date creationDate() {
        return (Date) Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

    private Date expirationDate() {
        return (Date) Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(1).toInstant());
    }

}


