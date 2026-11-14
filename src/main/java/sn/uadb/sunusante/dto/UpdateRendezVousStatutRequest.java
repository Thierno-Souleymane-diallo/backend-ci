package sn.uadb.sunusante.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import sn.uadb.sunusante.model.StatutRendezVous;

@Schema(description = "Demande de mise à jour du statut d'un rendez-vous")
public class UpdateRendezVousStatutRequest {

    @Schema(description = "Nouveau statut du rendez-vous", example = "EN_CONSULTATION", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Le statut du rendez-vous est obligatoire")
    private StatutRendezVous statut;

    public UpdateRendezVousStatutRequest() {
    }

    public UpdateRendezVousStatutRequest(StatutRendezVous statut) {
        this.statut = statut;
    }

    public StatutRendezVous getStatut() {
        return statut;
    }

    public void setStatut(StatutRendezVous statut) {
        this.statut = statut;
    }
}
