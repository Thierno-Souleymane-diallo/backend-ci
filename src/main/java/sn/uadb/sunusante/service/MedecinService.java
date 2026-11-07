package sn.uadb.sunusante.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.uadb.sunusante.dto.CreateMedecinRequest;
import sn.uadb.sunusante.dto.MedecinDTO;
import sn.uadb.sunusante.exception.ResourceNotFoundException;
import sn.uadb.sunusante.model.Etablissement;
import sn.uadb.sunusante.model.Medecin;
import sn.uadb.sunusante.repository.EtablissementRepository;
import sn.uadb.sunusante.repository.MedecinRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedecinService {

    private final MedecinRepository medecinRepository;
    private final EtablissementRepository etablissementRepository;

    public MedecinService(MedecinRepository medecinRepository,
                          EtablissementRepository etablissementRepository) {
        this.medecinRepository = medecinRepository;
        this.etablissementRepository = etablissementRepository;
    }

    /**
     * Recherche de tous les médecins.
     */
    @Transactional(readOnly = true)
    public List<MedecinDTO> getAllMedecins() {
        return medecinRepository.findAll().stream()
                .map(MedecinDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Recherche d'un médecin par son ID.
     */
    @Transactional(readOnly = true)
    public MedecinDTO getMedecinById(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé avec l'id : " + id));
        return MedecinDTO.fromEntity(medecin);
    }

    /**
     * F7 : Enregistrement d'un nouveau médecin rattaché à un établissement.
     */
    public MedecinDTO createMedecin(CreateMedecinRequest request) {
        Etablissement etablissement = etablissementRepository.findById(request.getEtablissementId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Impossible de créer le médecin : Établissement non trouvé avec l'id " + request.getEtablissementId()));

        Medecin medecin = new Medecin();
        medecin.setNom(request.getNom().trim());
        medecin.setPrenom(request.getPrenom().trim());
        medecin.setSpecialite(request.getSpecialite().trim());
        medecin.setTelephone(request.getTelephone() != null ? request.getTelephone().trim() : null);
        medecin.setEtablissement(etablissement);

        Medecin saved = medecinRepository.save(medecin);
        return MedecinDTO.fromEntity(saved);
    }
}
