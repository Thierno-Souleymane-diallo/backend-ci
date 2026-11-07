package sn.uadb.sunusante.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.uadb.sunusante.dto.EtablissementDTO;
import sn.uadb.sunusante.dto.MedecinDTO;
import sn.uadb.sunusante.exception.ResourceNotFoundException;
import sn.uadb.sunusante.model.Etablissement;
import sn.uadb.sunusante.model.Medecin;
import sn.uadb.sunusante.model.TypeEtablissement;
import sn.uadb.sunusante.repository.EtablissementRepository;
import sn.uadb.sunusante.repository.MedecinRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtablissementServiceTest {

    @Mock
    private EtablissementRepository etablissementRepository;

    @Mock
    private MedecinRepository medecinRepository;

    @InjectMocks
    private EtablissementService etablissementService;

    private Etablissement etablissementBambey;
    private Medecin medecinGeneraliste;

    @BeforeEach
    void setUp() {
        etablissementBambey = new Etablissement(
                1L,
                "Centre de Santé de Bambey",
                TypeEtablissement.CENTRE_DE_SANTE,
                "Bambey",
                "Route Nationale 3",
                "+221 33 973 11 00"
        );

        medecinGeneraliste = new Medecin(
                1L,
                "Ndiaye",
                "Mamadou",
                "Médecine Générale",
                "+221 77 123 45 67",
                etablissementBambey
        );
    }

    @Test
    @DisplayName("F1: Doit retourner tous les établissements sans filtre")
    void testGetEtablissementsWithoutFilter() {
        when(etablissementRepository.findAll()).thenReturn(List.of(etablissementBambey));

        List<EtablissementDTO> result = etablissementService.getEtablissements(null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Centre de Santé de Bambey");
        verify(etablissementRepository, times(1)).findAll();
        verify(etablissementRepository, never()).findByVilleIgnoreCase(any());
    }

    @Test
    @DisplayName("F1: Doit filtrer les établissements par ville")
    void testGetEtablissementsWithVilleFilter() {
        when(etablissementRepository.findByVilleIgnoreCase("Bambey")).thenReturn(List.of(etablissementBambey));

        List<EtablissementDTO> result = etablissementService.getEtablissements("Bambey");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getVille()).isEqualTo("Bambey");
        verify(etablissementRepository, times(1)).findByVilleIgnoreCase("Bambey");
    }

    @Test
    @DisplayName("F2: Doit lister les médecins d'un établissement existant")
    void testGetMedecinsByEtablissement() {
        when(etablissementRepository.existsById(1L)).thenReturn(true);
        when(medecinRepository.findByEtablissementId(1L)).thenReturn(List.of(medecinGeneraliste));

        List<MedecinDTO> result = etablissementService.getMedecinsByEtablissement(1L, null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Ndiaye");
        assertThat(result.get(0).getSpecialite()).isEqualTo("Médecine Générale");
    }

    @Test
    @DisplayName("F2: Doit filtrer les médecins par spécialité")
    void testGetMedecinsByEtablissementAndSpecialite() {
        when(etablissementRepository.existsById(1L)).thenReturn(true);
        when(medecinRepository.findByEtablissementIdAndSpecialiteIgnoreCase(1L, "Médecine Générale"))
                .thenReturn(List.of(medecinGeneraliste));

        List<MedecinDTO> result = etablissementService.getMedecinsByEtablissement(1L, "Médecine Générale");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSpecialite()).isEqualTo("Médecine Générale");
    }

    @Test
    @DisplayName("F2: Doit lever une exception 404 si l'établissement n'existe pas")
    void testGetMedecinsByEtablissementNotFound() {
        when(etablissementRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> etablissementService.getMedecinsByEtablissement(999L, null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Établissement non trouvé avec l'id : 999");
    }
}
