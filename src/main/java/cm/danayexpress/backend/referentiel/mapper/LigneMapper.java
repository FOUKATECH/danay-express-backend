package cm.danayexpress.backend.referentiel.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.referentiel.dto.LigneRequest;
import cm.danayexpress.backend.referentiel.dto.LigneResponse;
import cm.danayexpress.backend.referentiel.entity.Ligne;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface LigneMapper {

    LigneResponse toResponse(Ligne ligne);

    Ligne toEntity(LigneRequest request);

    void updateEntityFromRequest(LigneRequest request, @MappingTarget Ligne ligne);
}