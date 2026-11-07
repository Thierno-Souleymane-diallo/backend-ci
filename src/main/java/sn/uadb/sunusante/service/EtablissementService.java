package sn.uadb.sunusante.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.uadb.sunusante.dto.EtablissementDTO;
import sn.uadb.sunusante.dto.MedecinDTO;
import sn.uadb.sunusante.exception.ResourceNotFoundException;
import sn.uadb.sunusante.model.Etablissement;
import sn.uadb.sunusante.model.Medecin;
import sn.uadb.sunusante.repository.EtablissementRepository;
import sn.uadb.sunusante.repository.MedecinRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EtablissementService {

    private final EtablissementRepository etablissementRepository;
    private final MedecinRepository medecinRepository;

    public EtablissementService(EtablissementRepository etablissementRepository,
                                MedecinRepository medecinRepository) {
        this.etablissementRepository = etablissementRepository;
        this.medecinRepository = medecinRepository;
    }

    /**
     * F1 : Liste des établissements de santé avec filtrage optionnel par ville.
     */
    public List<EtablissementDTO> getEtablissements(String ville) {
        List<Etablissement> etablissements;
        if (ville != null && !ville.trim().isEmpty()) {
            etablissements = etablissementRepository.findByVilleIgnoreCase(ville.trim());
        } else {
            etablissements = etablissementRepository.findAll();
        }
        return etablissements.stream()
                .map(EtablissementDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Recherche d'un établissement par son identifiant unique.
     */
    public EtablissementDTO getEtablissementById(Long id) {
        Etablissement etablissement = etablissementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Établissement non trouvé avec l'id : " + id));
        return EtablissementDTO.fromEntity(etablissement);
    }

    /**
     * F2 : Liste des médecins d'un établissement donné avec filtrage optionnel par spécialité.
     */
    public List<MedecinDTO> getMedecinsByEtablissement(Long etablissementId, String specialite) {
        if (!etablissementRepository.existsById(etablissementId)) {
            throw new ResourceNotFoundException("Établissement non trouvé avec l'id : " + etablissementId);
        }

        List<Medecin> medecins;
        if (specialite != null && !specialite.trim().isEmpty()) {
            medecins = medecinRepository.findByEtablissementIdAndSpecialiteIgnoreCase(etablissementId, specialite.trim());
        } else {
            medecins = medecinRepository.findByEtablissementId(etablissementId);
        }

        return medecins.stream()
                .map(MedecinDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
