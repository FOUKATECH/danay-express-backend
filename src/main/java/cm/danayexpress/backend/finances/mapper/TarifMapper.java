package cm.danayexpress.backend.finances.mapper;

import cm.danayexpress.backend.finances.dto.TarifRequest;
import cm.danayexpress.backend.finances.dto.TarifResponse;
import cm.danayexpress.backend.finances.entity.Tarif;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TarifMapper {

    @Mapping(source = "origine.id", target = "origineId")
    @Mapping(source = "origine.nom", target = "origineNom")
    @Mapping(source = "destination.id", target = "destinationId")
    @Mapping(source = "destination.nom", target = "destinationNom")
    TarifResponse toResponse(Tarif tarif);

    @Mapping(target = "origine", ignore = true)
    @Mapping(target = "destination", ignore = true)
    Tarif toEntity(TarifRequest request);

    @Mapping(target = "origine", ignore = true)
    @Mapping(target = "destination", ignore = true)
    void updateEntityFromRequest(TarifRequest request, @MappingTarget Tarif tarif);
}