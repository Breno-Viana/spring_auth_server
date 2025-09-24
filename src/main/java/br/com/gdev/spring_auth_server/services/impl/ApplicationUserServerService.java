package br.com.gdev.spring_auth_server.services.impl;

import br.com.gdev.spring_auth_server.config.KeyConfigure;
import br.com.gdev.spring_auth_server.infra.exception.BadCredentialException;
import br.com.gdev.spring_auth_server.infra.exception.UserNotFoundException;
import br.com.gdev.spring_auth_server.model.dtos.*;
import br.com.gdev.spring_auth_server.model.entities.ProfilePhoto;
import br.com.gdev.spring_auth_server.model.entities.Users;
import br.com.gdev.spring_auth_server.model.mappers.UserMapper;
import br.com.gdev.spring_auth_server.model.repositories.UserRepository;
import br.com.gdev.spring_auth_server.security.ApplicationUserDetailsServiceImpl;
import br.com.gdev.spring_auth_server.services.JwtService;
import br.com.gdev.spring_auth_server.services.StorageService;
import br.com.gdev.spring_auth_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ApplicationUserServerService implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final StorageService service;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @Override
    public ResponseEntity<UserResponseDTO> register_account(UserDTO dto) {
        Users entity = mapper.toEntity(dto,encoder);
        var stored = service.store(dto.file(), dto.email());
        ProfilePhoto profilePhoto = createProfilePhoto(stored);
        entity.setProfile_photo(profilePhoto);
        Users save = repository.save(entity);

        UserResponseDTO responseDTO = mapper.toResponseDTO(save);

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<UserGetResponseDTO> find_user(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Users> usr_opt = repository.findById(uuid);
        return usr_opt.map(usr -> {
            UserGetResponseDTO responseDTO = mapper.toGetResponseDTO(usr);
            return ResponseEntity.accepted().body(responseDTO);
        }).orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    @Override
    public ResponseEntity<List<UserResponseDTO>> find_all() {
        List<Users> all = repository.findAll();
        List<UserResponseDTO> list = all.stream().map( mapper::toResponseDTO).toList();
        return ResponseEntity.ok(list);
    }

    private ProfilePhoto createProfilePhoto(Map<String,Path> profileSpecs){
        String fileName = profileSpecs.keySet().stream().findFirst().orElse(null);
        Path path = profileSpecs.get(fileName);
        ProfilePhoto pf = new ProfilePhoto();
        pf.setFile_name(fileName);
        pf.setFile_path(path.toString());

        return pf;
    }

    @Override
    public Users getUserByEmail(String email){
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    public Token authenticate(Login login) {
        UserDetailsService userDetailsService = new ApplicationUserDetailsServiceImpl(this);
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.username());
        String password = userDetails.getPassword();
        if (!encoder.matches(login.password(), password))throw new BadCredentialException("bad credentials");
        return jwtService.generateToken(userDetails);

    }


}
