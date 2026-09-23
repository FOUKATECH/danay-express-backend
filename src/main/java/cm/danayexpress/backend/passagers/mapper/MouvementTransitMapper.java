package cm.danayexpress.backend.passagers.mapper;

import cm.danayexpress.backend.passagers.dto.MouvementTransitResponse;
import cm.danayexpress.backend.passagers.entity.MouvementTransit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MouvementTransitMapper {

    @Mapping(source = "voyage.id", target = "voyageId")
    @Mapping(source = "escale.id", target = "escaleId")
    @Mapping(source = "agenceOrigine.id", target = "agenceOrigineId")
    @Mapping(source = "agenceOrigine.nom", target = "agenceOrigineNom")
    @Mapping(source = "agenceMouvement.id", target = "agenceMouvementId")
    @Mapping(source = "agenceMouvement.nom", target = "agenceMouvementNom")
    MouvementTransitResponse toResponse(MouvementTransit mouvement);
}