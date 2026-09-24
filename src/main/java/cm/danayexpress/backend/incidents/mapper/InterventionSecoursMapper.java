package cm.danayexpress.backend.incidents.mapper;

import cm.danayexpress.backend.incidents.dto.InterventionSecoursResponse;
import cm.danayexpress.backend.incidents.entity.InterventionSecours;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InterventionSecoursMapper {

    @Mapping(source = "incident.id", target = "incidentId")
    @Mapping(source = "vehiculeSecours.id", target = "vehiculeSecoursId")
    @Mapping(source = "vehiculeSecours.immatriculation", target = "immatriculationVehiculeSecours")
    @Mapping(source = "chauffeurSecours.id", target = "chauffeurSecoursId")
    @Mapping(target = "nomCompletChauffeurSecours", expression = "java(intervention.getChauffeurSecours() != null ? intervention.getChauffeurSecours().getPrenom() + ' ' + intervention.getChauffeurSecours().getNom() : null)")
    InterventionSecoursResponse toResponse(InterventionSecours intervention);
}
