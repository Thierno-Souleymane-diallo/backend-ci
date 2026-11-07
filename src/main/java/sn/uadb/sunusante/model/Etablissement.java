package sn.uadb.sunusante.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité JPA représentant une structure hospitalière ou centre de santé.
 */
@Entity
@Table(name = "etablissement", indexes = {
    @Index(name = "idx_etablissement_ville", columnList = "ville")
})
public class Etablissement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de l'établissement est obligatoire")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nom;

    @NotNull(message = "Le type d'établissement est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TypeEtablissement type;

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String ville;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String adresse;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String telephone;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "etablissement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Medecin> medecins = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Etablissement() {
    }

    public Etablissement(Long id, String nom, TypeEtablissement type, String ville, String adresse, String telephone) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.ville = ville;
        this.adresse = adresse;
        this.telephone = telephone;
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

    public List<Medecin> getMedecins() {
        return medecins;
    }

    public void setMedecins(List<Medecin> medecins) {
        this.medecins = medecins;
    }
}
