package sn.uadb.sunusante.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Données pour la création d'un nouveau médecin (Endpoint F7)")
public class CreateMedecinRequest {

    @Schema(description = "Nom de famille", example = "Sow", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le nom du médecin est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    @Schema(description = "Prénom", example = "Aminata", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le prénom du médecin est obligatoire")
    @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
    private String prenom;

    @Schema(description = "Spécialité médicale", example = "Pédiatrie", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La spécialité est obligatoire")
    @Size(max = 100, message = "La spécialité ne doit pas dépasser 100 caractères")
    private String specialite;

    @Schema(description = "Numéro de téléphone professionnel", example = "+221 77 987 65 43")
    @Size(max = 30, message = "Le téléphone ne doit pas dépasser 30 caractères")
    private String telephone;

    @Schema(description = "Identifiant de l'établissement d'affectation", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "L'identifiant de l'établissement est obligatoire")
    private Long etablissementId;

    public CreateMedecinRequest() {
    }

    public CreateMedecinRequest(String nom, String prenom, String specialite, String telephone, Long etablissementId) {
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.telephone = telephone;
        this.etablissementId = etablissementId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getEtablissementId() {
        return etablissementId;
    }

    public void setEtablissementId(Long etablissementId) {
        this.etablissementId = etablissementId;
    }
}
