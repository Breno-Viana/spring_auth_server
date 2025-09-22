package br.com.gdev.spring_auth_server.model.dtos;

public record UserResponseDTO(
        String USERNAME,
        String EMAIL,
        ProfilePhotoDTO PROFILE_PHOTO,
        String GET_URL) {
}
