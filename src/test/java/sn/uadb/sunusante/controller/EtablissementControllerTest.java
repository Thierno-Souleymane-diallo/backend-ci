package sn.uadb.sunusante.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sn.uadb.sunusante.dto.EtablissementDTO;
import sn.uadb.sunusante.dto.MedecinDTO;
import sn.uadb.sunusante.exception.ResourceNotFoundException;
import sn.uadb.sunusante.model.TypeEtablissement;
import sn.uadb.sunusante.service.EtablissementService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EtablissementController.class)
class EtablissementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EtablissementService etablissementService;

    @Test
    @DisplayName("GET /api/etablissements - Doit renvoyer 200 OK avec la liste des structures")
    void testGetEtablissements() throws Exception {
        EtablissementDTO dto = new EtablissementDTO();
        dto.setId(1L);
        dto.setNom("Centre de Santé de Bambey");
        dto.setType(TypeEtablissement.CENTRE_DE_SANTE);
        dto.setVille("Bambey");
        dto.setAdresse("Route Nationale 3");
        dto.setTelephone("+221 33 973 11 00");
        dto.setCreatedAt(LocalDateTime.now());

        when(etablissementService.getEtablissements(null)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/etablissements")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Centre de Santé de Bambey"))
                .andExpect(jsonPath("$[0].ville").value("Bambey"));
    }

    @Test
    @DisplayName("GET /api/etablissements?ville=Bambey - Doit filtrer par ville")
    void testGetEtablissementsFiltered() throws Exception {
        EtablissementDTO dto = new EtablissementDTO();
        dto.setId(1L);
        dto.setNom("Centre de Santé de Bambey");
        dto.setVille("Bambey");

        when(etablissementService.getEtablissements("Bambey")).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/etablissements")
                .param("ville", "Bambey")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ville").value("Bambey"));
    }

    @Test
    @DisplayName("GET /api/etablissements/{id}/medecins - Doit renvoyer les médecins de l'établissement")
    void testGetMedecinsByEtablissement() throws Exception {
        MedecinDTO dto = new MedecinDTO();
        dto.setId(1L);
        dto.setNom("Ndiaye");
        dto.setPrenom("Mamadou");
        dto.setSpecialite("Médecine Générale");
        dto.setEtablissementId(1L);

        when(etablissementService.getMedecinsByEtablissement(1L, null)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/etablissements/1/medecins")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Ndiaye"))
                .andExpect(jsonPath("$[0].specialite").value("Médecine Générale"));
    }

    @Test
    @DisplayName("GET /api/etablissements/999/medecins - Doit renvoyer 404 si établissement introuvable")
    void testGetMedecinsByEtablissementNotFound() throws Exception {
        when(etablissementService.getMedecinsByEtablissement(999L, null))
                .thenThrow(new ResourceNotFoundException("Établissement non trouvé avec l'id : 999"));

        mockMvc.perform(get("/api/etablissements/999/medecins")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Établissement non trouvé avec l'id : 999"));
    }
}
