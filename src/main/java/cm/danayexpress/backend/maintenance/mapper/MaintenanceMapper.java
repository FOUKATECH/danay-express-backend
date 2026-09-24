package cm.danayexpress.backend.maintenance.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.maintenance.dto.MaintenanceCreateRequest;
import cm.danayexpress.backend.maintenance.dto.MaintenanceResponse;
import cm.danayexpress.backend.maintenance.dto.MaintenanceUpdateRequest;
import cm.danayexpress.backend.maintenance.entity.InterventionMaintenance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapStructConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MaintenanceMapper {

    @Mapping(source = "vehicule.id", target = "vehiculeId")
    @Mapping(source = "vehicule.immatriculation", target = "immatriculationVehicule")
    @Mapping(source = "incident.id", target = "incidentId")
    MaintenanceResponse toResponse(InterventionMaintenance intervention);

    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "incident", ignore = true)
    InterventionMaintenance toEntity(MaintenanceCreateRequest request);

    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "incident", ignore = true)
    void updateEntityFromRequest(MaintenanceUpdateRequest request, @MappingTarget InterventionMaintenance intervention);
}
