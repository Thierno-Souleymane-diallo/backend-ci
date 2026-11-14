package sn.uadb.sunusante.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import sn.uadb.sunusante.model.RendezVous;
import sn.uadb.sunusante.model.StatutRendezVous;

import java.time.LocalDateTime;

@Schema(description = "Représentation publique d'un rendez-vous")
public class RendezVousDTO {

    @Schema(description = "Identifiant du rendez-vous", example = "1")
    private Long id;

    @Schema(description = "Nom du patient", example = "Fall")
    private String nomPatient;

    @Schema(description = "Téléphone du patient", example = "+221771234567")
    private String telephonePatient;

    @Schema(description = "Date et heure souhaitée", example = "2026-11-20T09:30:00")
    private LocalDateTime dateHeure;

    @Schema(description = "Motif de consultation", example = "Suivi général")
    private String motif;

    @Schema(description = "Statut du rendez-vous", example = "EN_ATTENTE")
    private StatutRendezVous statut;

    @Schema(description = "Identifiant du médecin concerné", example = "1")
    private Long medecinId;

    @Schema(description = "Nom complet du médecin", example = "Sow Aminata")
    private String medecinNom;

    @Schema(description = "Spécialité du médecin", example = "Pédiatrie")
    private String medecinSpecialite;

    @Schema(description = "Identifiant de l'établissement", example = "1")
    private Long etablissementId;

    @Schema(description = "Nom de l'établissement", example = "Centre de Santé de Bambey")
    private String etablissementNom;

    @Schema(description = "Date de création du rendez-vous")
    private LocalDateTime createdAt;

    @Schema(description = "Date de dernière mise à jour")
    private LocalDateTime updatedAt;

    public RendezVousDTO() {
    }

    public static RendezVousDTO fromEntity(RendezVous entity) {
        if (entity == null) {
            return null;
        }

        RendezVousDTO dto = new RendezVousDTO();
        dto.setId(entity.getId());
        dto.setNomPatient(entity.getNomPatient());
        dto.setTelephonePatient(entity.getTelephonePatient());
        dto.setDateHeure(entity.getDateHeure());
        dto.setMotif(entity.getMotif());
        dto.setStatut(entity.getStatut());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getMedecin() != null) {
            dto.setMedecinId(entity.getMedecin().getId());
            dto.setMedecinNom(entity.getMedecin().getNom() + " " + entity.getMedecin().getPrenom());
            dto.setMedecinSpecialite(entity.getMedecin().getSpecialite());
            if (entity.getMedecin().getEtablissement() != null) {
                dto.setEtablissementId(entity.getMedecin().getEtablissement().getId());
                dto.setEtablissementNom(entity.getMedecin().getEtablissement().getNom());
            }
        }

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getTelephonePatient() {
        return telephonePatient;
    }

    public void setTelephonePatient(String telephonePatient) {
        this.telephonePatient = telephonePatient;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public StatutRendezVous getStatut() {
        return statut;
    }

    public void setStatut(StatutRendezVous statut) {
        this.statut = statut;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    public String getMedecinNom() {
        return medecinNom;
    }

    public void setMedecinNom(String medecinNom) {
        this.medecinNom = medecinNom;
    }

    public String getMedecinSpecialite() {
        return medecinSpecialite;
    }

    public void setMedecinSpecialite(String medecinSpecialite) {
        this.medecinSpecialite = medecinSpecialite;
    }

    public Long getEtablissementId() {
        return etablissementId;
    }

    public void setEtablissementId(Long etablissementId) {
        this.etablissementId = etablissementId;
    }

    public String getEtablissementNom() {
        return etablissementNom;
    }

    public void setEtablissementNom(String etablissementNom) {
        this.etablissementNom = etablissementNom;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
