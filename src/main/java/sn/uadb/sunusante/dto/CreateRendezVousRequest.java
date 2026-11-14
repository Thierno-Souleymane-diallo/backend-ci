package sn.uadb.sunusante.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "Demande de prise d'un rendez-vous médical")
public class CreateRendezVousRequest {

    @Schema(description = "Nom du patient", example = "Fall", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le nom du patient est obligatoire")
    @Size(max = 150, message = "Le nom du patient ne doit pas dépasser 150 caractères")
    private String nomPatient;

    @Schema(description = "Téléphone du patient", example = "+221771234567", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le téléphone du patient est obligatoire")
    @Size(max = 30, message = "Le téléphone du patient ne doit pas dépasser 30 caractères")
    private String telephonePatient;

    @Schema(description = "Date et heure souhaitée du rendez-vous", example = "2026-11-20T09:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "La date et l'heure du rendez-vous sont obligatoires")
    private LocalDateTime dateHeure;

    @Schema(description = "Motif de consultation", example = "Suivi général")
    @Size(max = 255, message = "Le motif ne doit pas dépasser 255 caractères")
    private String motif;

    @Schema(description = "Identifiant du médecin choisi", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'identifiant du médecin est obligatoire")
    private Long medecinId;

    public CreateRendezVousRequest() {
    }

    public CreateRendezVousRequest(String nomPatient, String telephonePatient, LocalDateTime dateHeure,
                                  String motif, Long medecinId) {
        this.nomPatient = nomPatient;
        this.telephonePatient = telephonePatient;
        this.dateHeure = dateHeure;
        this.motif = motif;
        this.medecinId = medecinId;
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

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }
}
