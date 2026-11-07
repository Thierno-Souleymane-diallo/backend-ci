package sn.uadb.sunusante.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import sn.uadb.sunusante.model.Etablissement;
import sn.uadb.sunusante.model.TypeEtablissement;

import java.time.LocalDateTime;

@Schema(description = "Représentation publique d'un établissement de santé")
public class EtablissementDTO {

    @Schema(description = "Identifiant unique", example = "1")
    private Long id;

    @Schema(description = "Nom de l'établissement", example = "Centre Hospitalier Régional de Diourbel")
    private String nom;

    @Schema(description = "Type d'établissement", example = "HOPITAL")
    private TypeEtablissement type;

    @Schema(description = "Ville d'implantation", example = "Diourbel")
    private String ville;

    @Schema(description = "Adresse physique", example = "Quartier Médina Fall")
    private String adresse;

    @Schema(description = "Numéro de téléphone d'accueil", example = "+221 33 971 11 22")
    private String telephone;

    @Schema(description = "Date d'enregistrement dans le système")
    private LocalDateTime createdAt;

    public EtablissementDTO() {
    }

    public static EtablissementDTO fromEntity(Etablissement entity) {
        if (entity == null) return null;
        EtablissementDTO dto = new EtablissementDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setType(entity.getType());
        dto.setVille(entity.getVille());
        dto.setAdresse(entity.getAdresse());
        dto.setTelephone(entity.getTelephone());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeEtablissement getType() {
        return type;
    }

    public void setType(TypeEtablissement type) {
        this.type = type;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
