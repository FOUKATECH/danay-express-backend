package cm.danayexpress.backend.administration.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.administration.dto.UserCreateRequest;
import cm.danayexpress.backend.administration.dto.UserResponse;
import cm.danayexpress.backend.administration.dto.UserUpdateRequest;
import cm.danayexpress.backend.administration.entity.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapStructConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.code", target = "roleCode")
    @Mapping(source = "role.libelle", target = "roleLibelle")
    @Mapping(source = "agence.id", target = "agenceId")
    @Mapping(source = "agence.nom", target = "agenceNom")
    UserResponse toResponse(Utilisateur utilisateur);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "motDePasse", ignore = true)
    Utilisateur toEntity(UserCreateRequest request);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "motDePasse", ignore = true)
    void updateEntityFromRequest(UserUpdateRequest request, @MappingTarget Utilisateur utilisateur);
}
