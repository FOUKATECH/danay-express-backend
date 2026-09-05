package cm.danayexpress.backend.referentiel.mapper;

import cm.danayexpress.backend.referentiel.dto.AgenceRequest;
import cm.danayexpress.backend.referentiel.dto.AgenceResponse;
import cm.danayexpress.backend.referentiel.entity.Agence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AgenceMapper {

    @Mapping(source = "ville.id", target = "villeId")
    @Mapping(source = "ville.nom", target = "villeNom")
    AgenceResponse toResponse(Agence agence);

    /**
     * La ville n'est pas mappée ici : MapStruct ne peut pas résoudre un
     * id vers une entité sans accès au repository. Le service se
     * charge de récupérer la Ville et de l'affecter manuellement.
     */
    @Mapping(target = "ville", ignore = true)
    Agence toEntity(AgenceRequest request);

    @Mapping(target = "ville", ignore = true)
    void updateEntityFromRequest(AgenceRequest request, @MappingTarget Agence agence);
}