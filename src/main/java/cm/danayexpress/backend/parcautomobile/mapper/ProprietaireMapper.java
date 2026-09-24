package cm.danayexpress.backend.parcautomobile.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.parcautomobile.dto.ProprietaireRequest;
import cm.danayexpress.backend.parcautomobile.dto.ProprietaireResponse;
import cm.danayexpress.backend.parcautomobile.entity.Proprietaire;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface ProprietaireMapper {

    ProprietaireResponse toResponse(Proprietaire proprietaire);

    Proprietaire toEntity(ProprietaireRequest request);

    void updateEntityFromRequest(ProprietaireRequest request, @MappingTarget Proprietaire proprietaire);
}