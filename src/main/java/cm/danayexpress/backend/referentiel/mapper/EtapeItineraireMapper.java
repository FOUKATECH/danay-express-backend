package cm.danayexpress.backend.referentiel.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.referentiel.dto.EtapeItineraireRequest;
import cm.danayexpress.backend.referentiel.dto.EtapeItineraireResponse;
import cm.danayexpress.backend.referentiel.entity.EtapeItineraire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface EtapeItineraireMapper {

    @Mapping(source = "sens.id", target = "sensId")
    @Mapping(source = "agence.id", target = "agenceId")
    @Mapping(source = "agence.nom", target = "agenceNom")
    EtapeItineraireResponse toResponse(EtapeItineraire etape);

    @Mapping(target = "sens", ignore = true)
    @Mapping(target = "agence", ignore = true)
    EtapeItineraire toEntity(EtapeItineraireRequest request);

    @Mapping(target = "sens", ignore = true)
    @Mapping(target = "agence", ignore = true)
    void updateEntityFromRequest(EtapeItineraireRequest request, @MappingTarget EtapeItineraire etape);
}