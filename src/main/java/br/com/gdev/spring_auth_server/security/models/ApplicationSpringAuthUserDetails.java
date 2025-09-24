package br.com.gdev.spring_auth_server.security.models;

import br.com.gdev.spring_auth_server.model.entities.ProfilePhoto;
import br.com.gdev.spring_auth_server.model.entities.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
public class ApplicationSpringAuthUserDetails implements UserDetails,OthersUserDetails {

    private final Users user;

    public ApplicationSpringAuthUserDetails(Users user){
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roles  = user.getRole().trim().split(",");
        List<String> list = Arrays.asList(roles);
        return list
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.trim()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.user.getPassHash();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public ProfilePhoto getProfilePhoto() {
        return this.user.getProfilePhoto();
    }

    @Override
    public String getDbUsername() {
        return this.user.getUserName();
    }

    @Override
    public LocalDate getBirthDate() {
        return this.user.getBirthDate();
    }
}
