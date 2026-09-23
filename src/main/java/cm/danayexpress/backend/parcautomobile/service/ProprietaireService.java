package cm.danayexpress.backend.parcautomobile.service;

import cm.danayexpress.backend.parcautomobile.dto.ProprietaireRequest;
import cm.danayexpress.backend.parcautomobile.dto.ProprietaireResponse;
import cm.danayexpress.backend.parcautomobile.entity.Proprietaire;
import cm.danayexpress.backend.parcautomobile.exception.ParcAutomobileNotFoundException;
import cm.danayexpress.backend.parcautomobile.mapper.ProprietaireMapper;
import cm.danayexpress.backend.parcautomobile.repository.ProprietaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProprietaireService {

    private final ProprietaireRepository proprietaireRepository;
    private final ProprietaireMapper proprietaireMapper;

    public List<ProprietaireResponse> findAll() {
        return proprietaireRepository.findAll().stream()
                .map(proprietaireMapper::toResponse)
                .toList();
    }

    public ProprietaireResponse findById(Long id) {
        return proprietaireMapper.toResponse(getProprietaireOrThrow(id));
    }

    @Transactional
    public ProprietaireResponse create(ProprietaireRequest request) {
        Proprietaire proprietaire = proprietaireMapper.toEntity(request);
        return proprietaireMapper.toResponse(proprietaireRepository.save(proprietaire));
    }

    @Transactional
    public ProprietaireResponse update(Long id, ProprietaireRequest request) {
        Proprietaire proprietaire = getProprietaireOrThrow(id);
        proprietaireMapper.updateEntityFromRequest(request, proprietaire);
        return proprietaireMapper.toResponse(proprietaireRepository.save(proprietaire));
    }

    @Transactional
    public void delete(Long id) {
        proprietaireRepository.delete(getProprietaireOrThrow(id));
    }

    private Proprietaire getProprietaireOrThrow(Long id) {
        return proprietaireRepository.findById(id)
                .orElseThrow(() -> new ParcAutomobileNotFoundException("Proprietaire", id));
    }
}