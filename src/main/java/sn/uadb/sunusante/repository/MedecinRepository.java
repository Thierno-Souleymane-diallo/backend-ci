package sn.uadb.sunusante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.uadb.sunusante.model.Medecin;

import java.util.List;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    /**
     * Recherche des médecins d'un établissement donné - Endpoint F2
     */
    List<Medecin> findByEtablissementId(Long etablissementId);

    /**
     * Recherche des médecins d'un établissement filtrés par spécialité - Endpoint F2
     */
    List<Medecin> findByEtablissementIdAndSpecialiteIgnoreCase(Long etablissementId, String specialite);
}
