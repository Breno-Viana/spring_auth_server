package br.com.gdev.spring_auth_server.security.utils;

import br.com.gdev.spring_auth_server.model.ProfilePhoto;
import br.com.gdev.spring_auth_server.model.Users;

import java.time.LocalDate;

public interface OthersUserDetails {
    ProfilePhoto getProfilePhoto();
    String getDbUsername();
    LocalDate getBirthDate();
    Users getPrincipal();
}
