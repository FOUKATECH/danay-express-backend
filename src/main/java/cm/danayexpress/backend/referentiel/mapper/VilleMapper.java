package cm.danayexpress.backend.referentiel.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.referentiel.dto.VilleRequest;
import cm.danayexpress.backend.referentiel.dto.VilleResponse;
import cm.danayexpress.backend.referentiel.entity.Ville;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversion entre l'entité Ville et ses DTO.
 * MapStruct génère l'implémentation à la compilation (classe
 * VilleMapperImpl, dans target/generated-sources) : pas de réflexion à
 * l'exécution, et toute erreur de mapping (champ oublié, type
 * incompatible) est détectée au build plutôt qu'en production.
 */
@Mapper(config = MapStructConfig.class)
public interface VilleMapper {

    VilleResponse toResponse(Ville ville);

    Ville toEntity(VilleRequest request);

    /**
     * Met à jour une entité existante à partir d'une requête, sans
     * créer de nouvel objet (utile pour un update : on garde le même
     * id, createdAt, etc., seuls nom/region changent).
     */
    void updateEntityFromRequest(VilleRequest request, @MappingTarget Ville ville);
}