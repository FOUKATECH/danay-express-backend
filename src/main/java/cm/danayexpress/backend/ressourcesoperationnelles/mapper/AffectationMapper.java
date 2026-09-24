package cm.danayexpress.backend.ressourcesoperationnelles.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.ressourcesoperationnelles.dto.AffectationRequest;
import cm.danayexpress.backend.ressourcesoperationnelles.dto.AffectationResponse;
import cm.danayexpress.backend.ressourcesoperationnelles.entity.Affectation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface AffectationMapper {

    @Mapping(source = "chauffeur.id", target = "chauffeurId")
    @Mapping(source = "chauffeur.nom", target = "chauffeurNom")
    @Mapping(source = "chauffeur.prenom", target = "chauffeurPrenom")
    @Mapping(source = "vehicule.id", target = "vehiculeId")
    @Mapping(source = "vehicule.immatriculation", target = "vehiculeImmatriculation")
    AffectationResponse toResponse(Affectation affectation);

    @Mapping(target = "chauffeur", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    Affectation toEntity(AffectationRequest request);

    @Mapping(target = "chauffeur", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    void updateEntityFromRequest(AffectationRequest request, @MappingTarget Affectation affectation);
}