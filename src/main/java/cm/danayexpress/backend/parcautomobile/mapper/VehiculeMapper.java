package cm.danayexpress.backend.parcautomobile.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.parcautomobile.dto.VehiculeRequest;
import cm.danayexpress.backend.parcautomobile.dto.VehiculeResponse;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface VehiculeMapper {

    @Mapping(source = "proprietaire.id", target = "proprietaireId")
    @Mapping(source = "proprietaire.nom", target = "proprietaireNom")
    @Mapping(source = "agence.id", target = "agenceId")
    @Mapping(source = "agence.nom", target = "agenceNom")
    VehiculeResponse toResponse(Vehicule vehicule);

    @Mapping(target = "proprietaire", ignore = true)
    @Mapping(target = "agence", ignore = true)
    Vehicule toEntity(VehiculeRequest request);

    @Mapping(target = "proprietaire", ignore = true)
    @Mapping(target = "agence", ignore = true)
    void updateEntityFromRequest(VehiculeRequest request, @MappingTarget Vehicule vehicule);
}