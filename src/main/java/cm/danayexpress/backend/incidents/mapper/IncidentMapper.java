package cm.danayexpress.backend.incidents.mapper;

import cm.danayexpress.backend.incidents.dto.IncidentCreateRequest;
import cm.danayexpress.backend.incidents.dto.IncidentResponse;
import cm.danayexpress.backend.incidents.dto.IncidentUpdateRequest;
import cm.danayexpress.backend.incidents.entity.Incident;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IncidentMapper {

    @Mapping(source = "vehicule.id", target = "vehiculeId")
    @Mapping(source = "vehicule.immatriculation", target = "immatriculationVehicule")
    @Mapping(source = "voyage.id", target = "voyageId")
    @Mapping(source = "agence.id", target = "agenceId")
    @Mapping(source = "agence.nom", target = "nomAgence")
    IncidentResponse toResponse(Incident incident);

    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "voyage", ignore = true)
    @Mapping(target = "agence", ignore = true)
    Incident toEntity(IncidentCreateRequest request);

    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "voyage", ignore = true)
    @Mapping(target = "agence", ignore = true)
    void updateEntityFromRequest(IncidentUpdateRequest request, @MappingTarget Incident incident);
}
