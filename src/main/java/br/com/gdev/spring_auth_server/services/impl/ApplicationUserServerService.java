package br.com.gdev.spring_auth_server.services.impl;

import br.com.gdev.spring_auth_server.infra.exception.AlreadyOwnerException;
import br.com.gdev.spring_auth_server.infra.exception.BadCredentialException;
import br.com.gdev.spring_auth_server.infra.exception.UserNotFoundException;
import br.com.gdev.spring_auth_server.model.dtos.*;
import br.com.gdev.spring_auth_server.model.ProfilePhoto;
import br.com.gdev.spring_auth_server.model.Users;
import br.com.gdev.spring_auth_server.model.mappers.UserMapper;
import br.com.gdev.spring_auth_server.model.repositories.UserRepository;
import br.com.gdev.spring_auth_server.security.ApplicationSpringAuthUserDetails;
import br.com.gdev.spring_auth_server.security.ApplicationUserDetailsServiceImpl;
import br.com.gdev.spring_auth_server.security.utils.SecurityInformationGetter;
import br.com.gdev.spring_auth_server.services.JwtService;
import br.com.gdev.spring_auth_server.services.StorageService;
import br.com.gdev.spring_auth_server.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ApplicationUserServerService implements UserService, SecurityInformationGetter {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final StorageService service;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @Override
    public ResponseEntity<UserResponseDTO> registerAccount(UserDTO dto) {
        Users entity = mapper.toEntity(dto, encoder);
        var stored = service.store(dto.file(), dto.email());
        ProfilePhoto profilePhoto = createProfilePhoto(stored);
        entity.setProfilePhoto(profilePhoto);
        Users save = repository.save(entity);

        UserResponseDTO responseDTO = mapper.toResponseDTO(save);

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<UserGetResponseDTO> findUser(String id) {
        Optional<Users> usr_opt = repository.findById(id);
        return usr_opt.map(usr -> {
            UserGetResponseDTO responseDTO = mapper.toGetResponseDTO(usr);
            return ResponseEntity.accepted().body(responseDTO);
        }).orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    @Override
    public ResponseEntity<List<UserGetResponseDTO>> findAll() {
        List<Users> all = repository.findAll();
        List<UserGetResponseDTO> list = all.stream().map(mapper::toGetResponseDTO).toList();
        return ResponseEntity.ok(list);
    }

    private ProfilePhoto createProfilePhoto(Map<String, Path> profileSpecs) {
        String fileName = profileSpecs.keySet().stream().findFirst().orElse(null);
        Path path = profileSpecs.get(fileName);
        ProfilePhoto pf = new ProfilePhoto();
        pf.setFile_name(fileName);
        pf.setFile_path(path.toString());

        return pf;
    }

    @Override
    public Users getUserByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    @Transactional(rollbackOn = NullPointerException.class)
    public ResponseEntity<Void> deleteUser(String id, String email) {
        System.out.println("HERE");
        if (id == null && email == null) throw new NullPointerException();
        if (id == null) repository.deleteUsersByIdOrEmail(null, email);
        if (email == null) repository.deleteUsersByIdOrEmail(id, null);
        return ResponseEntity.noContent().build();
    }

    @Override
    public Token authenticate(Login auth) {
        UserDetailsService userDetailsService = new ApplicationUserDetailsServiceImpl(this);
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.username());
        String password = userDetails.getPassword();
        if (!encoder.matches(auth.password(), password)) throw new BadCredentialException("bad credentials");
        return jwtService.generateUserDetailsToken(userDetails);

    }

    @Override
    public ResponseEntity<?> getOwn(HttpServletRequest request) {
        Users user;
        ApplicationSpringAuthUserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            userDetails = (ApplicationSpringAuthUserDetails) authentication.getPrincipal();
            user = userDetails.getPrincipal();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (user.getRole().contains("OWNER")) throw new AlreadyOwnerException("user already are a owner");
        user.setRole(String.format("%s,%s", user.getRole(), "OWNER"));
        repository.save(user);
        Token newToken = genNewToken(request);
        return ResponseEntity.accepted().body(newToken);
    }

    private Token genNewToken(HttpServletRequest request){
        String token_hard_string = recoveryBearerToken(request);
        ApplicationSpringAuthUserDetails principal;
        try {
            principal = (ApplicationSpringAuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        return jwtService.genNew(new Token("your new access token",token_hard_string), principal);
    }


}
