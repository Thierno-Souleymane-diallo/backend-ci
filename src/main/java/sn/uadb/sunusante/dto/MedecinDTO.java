package sn.uadb.sunusante.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import sn.uadb.sunusante.model.Medecin;

import java.time.LocalDateTime;

@Schema(description = "Représentation publique d'un médecin praticien")
public class MedecinDTO {

    @Schema(description = "Identifiant unique", example = "1")
    private Long id;

    @Schema(description = "Nom de famille", example = "Ndiaye")
    private String nom;

    @Schema(description = "Prénom", example = "Mamadou")
    private String prenom;

    @Schema(description = "Spécialité médicale", example = "Médecine Générale")
    private String specialite;

    @Schema(description = "Téléphone professionnel", example = "+221 77 123 45 67")
    private String telephone;

    @Schema(description = "Identifiant de l'établissement rattaché", example = "1")
    private Long etablissementId;

    @Schema(description = "Nom de l'établissement rattaché", example = "Centre de Santé de Bambey")
    private String etablissementNom;

    @Schema(description = "Date d'enregistrement")
    private LocalDateTime createdAt;

    public MedecinDTO() {
    }

    public static MedecinDTO fromEntity(Medecin entity) {
        if (entity == null) return null;
        MedecinDTO dto = new MedecinDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setSpecialite(entity.getSpecialite());
        dto.setTelephone(entity.getTelephone());
        if (entity.getEtablissement() != null) {
            dto.setEtablissementId(entity.getEtablissement().getId());
            dto.setEtablissementNom(entity.getEtablissement().getNom());
        }
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
}
