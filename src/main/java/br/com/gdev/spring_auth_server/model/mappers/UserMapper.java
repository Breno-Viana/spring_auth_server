package br.com.gdev.spring_auth_server.model.mappers;

import br.com.gdev.spring_auth_server.model.dtos.UserDTO;
import br.com.gdev.spring_auth_server.model.dtos.UserGetResponseDTO;
import br.com.gdev.spring_auth_server.model.dtos.UserResponseDTO;
import br.com.gdev.spring_auth_server.model.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ProfileMapper mapper;

    public Users toEntity(UserDTO dto, PasswordEncoder ec){
        if (dto == null) return new Users();
        String hash = ec.encode(dto.password());
        Users usr = new Users();
        usr.setUsername(dto.username());
        usr.setBirth_day(dto.birth());
        usr.setEmail(dto.email());
        usr.setPass_hash(hash);
        usr.setRole(dto.role() == null?"READ_ONLY":formar_roles(dto.role()));
        System.out.println(dto.role());
        return usr;
    }


    public UserResponseDTO toResponseDTO(Users entity){
        if (entity == null) return null;

        return new UserResponseDTO(
                entity.getUsername(),
                entity.getEmail(),
                mapper.toDTO(entity.getProfile_photo()),
                String.format("http://localhost:8989/usr/g/%s",entity.getId())
        );
    }


    public UserGetResponseDTO toGetResponseDTO(Users entity){
        if (entity == null) return null;
        Long age = cal_age(entity.getBirth_day());
        return new UserGetResponseDTO(
                entity.getUsername(),
                entity.getEmail(),
                entity.getBirth_day(),
                age,
                entity.getCreate_at()
        );
    }

    private Long cal_age(LocalDate localDate){
        return ChronoUnit.YEARS.between(localDate, LocalDate.now());
    }

    private String formar_roles(String roles){
        return roles.trim().toUpperCase();
    }
}
