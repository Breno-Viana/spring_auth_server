package br.com.gdev.spring_auth_server.config;

import br.com.gdev.spring_auth_server.infra.handler.CustomAccessDeniedHandler;
import br.com.gdev.spring_auth_server.infra.handler.CustomUnauthorizedExceptionHandler;
import br.com.gdev.spring_auth_server.security.ApplicationUserDetailsServiceImpl;
import br.com.gdev.spring_auth_server.security.filter.UserAuthenticationFilter;
import br.com.gdev.spring_auth_server.security.utils.SecurityUrlSettings;
import br.com.gdev.spring_auth_server.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class UserSecurityConfigContext {


    @Bean
    SecurityFilterChain UserAndClientsResourceServerFilterChain(HttpSecurity http,
                                                                UserAuthenticationFilter userAuthenticationFilter,
                                                                CustomAccessDeniedHandler customAccessDeniedHandler,
                                                                CustomUnauthorizedExceptionHandler unauthorizedExceptionHandler) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(authorize -> {
           authorize.requestMatchers(SecurityUrlSettings.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll();
           authorize.anyRequest().hasRole("WRITER");
        });
        http.addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(ex -> {
            ex.accessDeniedHandler(customAccessDeniedHandler);
            ex.authenticationEntryPoint(unauthorizedExceptionHandler);
        });
        http.formLogin(Customizer.withDefaults());

      return http.build();
    }



    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService(UserService service){
        return new ApplicationUserDetailsServiceImpl(service);
    }



}
