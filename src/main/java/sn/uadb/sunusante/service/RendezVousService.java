package sn.uadb.sunusante.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.uadb.sunusante.dto.CreateRendezVousRequest;
import sn.uadb.sunusante.dto.RendezVousDTO;
import sn.uadb.sunusante.exception.ResourceNotFoundException;
import sn.uadb.sunusante.model.Medecin;
import sn.uadb.sunusante.model.RendezVous;
import sn.uadb.sunusante.model.StatutRendezVous;
import sn.uadb.sunusante.repository.EtablissementRepository;
import sn.uadb.sunusante.repository.MedecinRepository;
import sn.uadb.sunusante.repository.RendezVousRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final MedecinRepository medecinRepository;
    private final EtablissementRepository etablissementRepository;

    public RendezVousService(RendezVousRepository rendezVousRepository,
                            MedecinRepository medecinRepository,
                            EtablissementRepository etablissementRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.medecinRepository = medecinRepository;
        this.etablissementRepository = etablissementRepository;
    }

    public RendezVousDTO createRendezVous(CreateRendezVousRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La demande de rendez-vous est obligatoire");
        }

        Medecin medecin = medecinRepository.findById(request.getMedecinId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Médecin non trouvé avec l'id : " + request.getMedecinId()));

        if (request.getDateHeure() == null) {
            throw new IllegalArgumentException("La date et l'heure du rendez-vous sont obligatoires");
        }

        if (request.getDateHeure().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date du rendez-vous doit être dans le futur");
        }

        RendezVous rendezVous = new RendezVous();
        rendezVous.setNomPatient(request.getNomPatient().trim());
        rendezVous.setTelephonePatient(request.getTelephonePatient().trim());
        rendezVous.setDateHeure(request.getDateHeure());
        rendezVous.setMotif(request.getMotif() != null ? request.getMotif().trim() : null);
        rendezVous.setStatut(StatutRendezVous.EN_ATTENTE);
        rendezVous.setMedecin(medecin);

        return RendezVousDTO.fromEntity(rendezVousRepository.save(rendezVous));
    }

    @Transactional(readOnly = true)
    public RendezVousDTO getRendezVousById(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous non trouvé avec l'id : " + id));
        return RendezVousDTO.fromEntity(rendezVous);
    }

    @Transactional(readOnly = true)
    public List<RendezVousDTO> getRendezVousByTelephone(String telephone) {
        if (telephone == null || telephone.trim().isEmpty()) {
            throw new IllegalArgumentException("Le numéro de téléphone est obligatoire");
        }

        String trimmed = telephone.trim();
        String normalized = trimmed
                .replaceAll("[\\s.\\-()]", "")
                .replaceFirst("^(\\+221|00221|221)", "");

        return rendezVousRepository.searchByTelephone(trimmed, normalized)
                .stream()
                .map(RendezVousDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public RendezVousDTO updateStatut(Long id, StatutRendezVous statut) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous non trouvé avec l'id : " + id));

        if (statut == null) {
            throw new IllegalArgumentException("Le statut du rendez-vous est obligatoire");
        }

        rendezVous.setStatut(statut);
        return RendezVousDTO.fromEntity(rendezVousRepository.save(rendezVous));
    }

    @Transactional(readOnly = true)
    public List<RendezVousDTO> getFileAttente(Long etablissementId, LocalDate date) {
        if (etablissementId == null) {
            throw new IllegalArgumentException("L'identifiant de l'établissement est obligatoire");
        }

        if (!etablissementRepository.existsById(etablissementId)) {
            throw new ResourceNotFoundException("Établissement non trouvé avec l'id : " + etablissementId);
        }

        LocalDate targetDate = date != null ? date : LocalDate.now();
        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime endOfDay = targetDate.plusDays(1).atStartOfDay();

        List<StatutRendezVous> statuts = List.of(
                StatutRendezVous.EN_ATTENTE,
                StatutRendezVous.EN_CONSULTATION,
                StatutRendezVous.TERMINE,
                StatutRendezVous.ANNULE
        );
        return rendezVousRepository.findFileAttente(etablissementId, startOfDay, endOfDay, statuts)
                .stream()
                .map(RendezVousDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
