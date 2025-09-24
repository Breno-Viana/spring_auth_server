package br.com.gdev.spring_auth_server.security.models;

import br.com.gdev.spring_auth_server.model.entities.ProfilePhoto;

import java.time.LocalDate;

public interface OthersUserDetails {
    ProfilePhoto getProfilePhoto();
    String getDbUsername();
    LocalDate getBirthDate();
}
