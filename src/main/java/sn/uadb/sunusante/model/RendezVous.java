package sn.uadb.sunusante.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Entité JPA représentant une réservation de consultation médicale.
 */
@Entity
@Table(name = "rendez_vous", indexes = {
    @Index(name = "idx_rendez_vous_telephone", columnList = "telephone_patient"),
    @Index(name = "idx_rendez_vous_date_heure", columnList = "date_heure"),
    @Index(name = "idx_rendez_vous_statut", columnList = "statut")
})
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du patient est obligatoire")
    @Size(max = 150)
    @Column(name = "nom_patient", nullable = false, length = 150)
    private String nomPatient;

    @NotBlank(message = "Le numéro de téléphone du patient est obligatoire")
    @Size(max = 30)
    @Column(name = "telephone_patient", nullable = false, length = 30)
    private String telephonePatient;

    @NotNull(message = "La date et heure du rendez-vous sont obligatoires")
    @Column(name = "date_heure", nullable = false)
    private LocalDateTime dateHeure;

    @Size(max = 255)
    @Column(length = 255)
    private String motif;

    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatutRendezVous statut = StatutRendezVous.EN_ATTENTE;

    @NotNull(message = "Le médecin traitant est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public RendezVous() {
    }

    public RendezVous(Long id, String nomPatient, String telephonePatient, LocalDateTime dateHeure,
                      String motif, StatutRendezVous statut, Medecin medecin) {
        this.id = id;
        this.nomPatient = nomPatient;
        this.telephonePatient = telephonePatient;
        this.dateHeure = dateHeure;
        this.motif = motif;
        this.statut = statut;
        this.medecin = medecin;
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

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
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
