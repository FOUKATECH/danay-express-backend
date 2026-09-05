package cm.danayexpress.backend.referentiel.mapper;

import cm.danayexpress.backend.referentiel.dto.SensRequest;
import cm.danayexpress.backend.referentiel.dto.SensResponse;
import cm.danayexpress.backend.referentiel.entity.Sens;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SensMapper {

    @Mapping(source = "ligne.id", target = "ligneId")
    @Mapping(source = "ligne.nom", target = "ligneNom")
    @Mapping(source = "agenceDepart.id", target = "agenceDepartId")
    @Mapping(source = "agenceDepart.nom", target = "agenceDepartNom")
    @Mapping(source = "agenceArrivee.id", target = "agenceArriveeId")
    @Mapping(source = "agenceArrivee.nom", target = "agenceArriveeNom")
    SensResponse toResponse(Sens sens);

    /**
     * ligne, agenceDepart et agenceArrivee ne sont pas résolus ici (ce
     * sont de simples id dans la requête) : le service les récupère et
     * les affecte manuellement après validation.
     */
    @Mapping(target = "ligne", ignore = true)
    @Mapping(target = "agenceDepart", ignore = true)
    @Mapping(target = "agenceArrivee", ignore = true)
    Sens toEntity(SensRequest request);

    @Mapping(target = "ligne", ignore = true)
    @Mapping(target = "agenceDepart", ignore = true)
    @Mapping(target = "agenceArrivee", ignore = true)
    void updateEntityFromRequest(SensRequest request, @MappingTarget Sens sens);
}