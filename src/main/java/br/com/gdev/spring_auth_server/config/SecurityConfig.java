package br.com.gdev.spring_auth_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> {
           authorize.requestMatchers(HttpMethod.POST, "/client/save").permitAll();
           authorize.requestMatchers(HttpMethod.POST, "/user/save").permitAll();
           authorize.anyRequest().authenticated();
        });
      return http.build();
    }

    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(10);
    }
}
