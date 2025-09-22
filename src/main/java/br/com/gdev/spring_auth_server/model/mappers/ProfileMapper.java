package br.com.gdev.spring_auth_server.model.mappers;

import br.com.gdev.spring_auth_server.model.dtos.ProfilePhotoDTO;
import br.com.gdev.spring_auth_server.model.entities.ProfilePhoto;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfilePhotoDTO toDTO(ProfilePhoto entity){
        if (entity == null) return null;

        return new ProfilePhotoDTO(
                entity.getFile_name(),
                entity.getFile_path()
        );
    }
}
