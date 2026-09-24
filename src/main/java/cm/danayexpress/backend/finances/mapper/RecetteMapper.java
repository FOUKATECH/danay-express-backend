package cm.danayexpress.backend.finances.mapper;

import cm.danayexpress.backend.finances.dto.RecetteResponse;
import cm.danayexpress.backend.finances.entity.Recette;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecetteMapper {

    @Mapping(source = "mouvementTransit.id", target = "mouvementTransitId")
    @Mapping(source = "mouvementTransit.voyage.id", target = "voyageId")
    @Mapping(source = "mouvementTransit.agenceOrigine.nom", target = "agenceOrigineNom")
    @Mapping(source = "mouvementTransit.agenceMouvement.nom", target = "agenceMouvementNom")
    @Mapping(source = "mouvementTransit.nombrePassagers", target = "nombrePassagers")
    @Mapping(source = "tarif.id", target = "tarifId")
    @Mapping(source = "tarif.montant", target = "montantUnitaire")
    RecetteResponse toResponse(Recette recette);
}