package sn.uadb.sunusante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.uadb.sunusante.model.Etablissement;

import java.util.List;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {

    /**
     * Recherche des établissements par ville (insensible à la casse) - Endpoint F1
     */
    List<Etablissement> findByVilleIgnoreCase(String ville);
}
