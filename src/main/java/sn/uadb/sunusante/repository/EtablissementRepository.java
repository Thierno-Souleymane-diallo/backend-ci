package sn.uadb.sunusante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.uadb.sunusante.model.Etablissement;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {

    Optional<Etablissement> findByNomIgnoreCase(String nom);

    /**
     * Recherche des établissements par ville (insensible à la casse) - Endpoint F1
     */
    List<Etablissement> findByVilleIgnoreCase(String ville);
}
