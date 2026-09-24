package cm.danayexpress.backend.ressourcesoperationnelles.mapper;

import cm.danayexpress.backend.common.mapper.MapStructConfig;
import cm.danayexpress.backend.ressourcesoperationnelles.dto.ChauffeurRequest;
import cm.danayexpress.backend.ressourcesoperationnelles.dto.ChauffeurResponse;
import cm.danayexpress.backend.ressourcesoperationnelles.entity.Chauffeur;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface ChauffeurMapper {

    ChauffeurResponse toResponse(Chauffeur chauffeur);

    Chauffeur toEntity(ChauffeurRequest request);

    void updateEntityFromRequest(ChauffeurRequest request, @MappingTarget Chauffeur chauffeur);
}