package sn.uadb.sunusante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.uadb.sunusante.model.RendezVous;
import sn.uadb.sunusante.model.StatutRendezVous;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    /**
     * Recherche des consultations par numéro de téléphone patient - Endpoint F4
     */
    List<RendezVous> findByTelephonePatientOrderByDateHeureDesc(String telephonePatient);

    /**
     * File d'attente d'un établissement pour une plage de date/heure donnée, triée par heure - Endpoint F5
     */
    @Query("SELECT r FROM RendezVous r WHERE r.medecin.etablissement.id = :etablissementId " +
           "AND r.dateHeure BETWEEN :startOfDay AND :endOfDay " +
           "AND r.statut IN (:statuts) " +
           "ORDER BY r.dateHeure ASC")
    List<RendezVous> findFileAttente(
        @Param("etablissementId") Long etablissementId,
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay,
        @Param("statuts") List<StatutRendezVous> statuts
    );
}
