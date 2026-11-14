package sn.uadb.sunusante.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sn.uadb.sunusante.dto.CreateRendezVousRequest;
import sn.uadb.sunusante.dto.RendezVousDTO;
import sn.uadb.sunusante.model.StatutRendezVous;
import sn.uadb.sunusante.service.RendezVousService;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RendezVousController.class)
class RendezVousControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RendezVousService rendezVousService;

    @Test
    @DisplayName("POST /api/rendez-vous - doit créer un rendez-vous avec succès")
    void testCreateRendezVousSuccess() throws Exception {
        CreateRendezVousRequest request = new CreateRendezVousRequest(
                "Fall",
                "+221771234567",
                LocalDateTime.of(2026, 11, 20, 9, 30),
                "Suivi général",
                1L
        );

        RendezVousDTO created = new RendezVousDTO();
        created.setId(10L);
        created.setNomPatient("Fall");
        created.setTelephonePatient("+221771234567");
        created.setDateHeure(request.getDateHeure());
        created.setMotif("Suivi général");
        created.setStatut(StatutRendezVous.EN_ATTENTE);
        created.setMedecinId(1L);
        created.setMedecinNom("Sow Aminata");
        created.setEtablissementNom("Centre de Santé de Bambey");

        when(rendezVousService.createRendezVous(any(CreateRendezVousRequest.class))).thenReturn(created);

        mockMvc.perform(post("/api/rendez-vous")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nomPatient").value("Fall"))
                .andExpect(jsonPath("$.statut").value("EN_ATTENTE"));
    }

    @Test
    @DisplayName("POST /api/rendez-vous - doit renvoyer 400 si les données sont invalides")
    void testCreateRendezVousValidationFailure() throws Exception {
        CreateRendezVousRequest invalidRequest = new CreateRendezVousRequest(
                "",
                "",
                null,
                "",
                null
        );

        mockMvc.perform(post("/api/rendez-vous")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
