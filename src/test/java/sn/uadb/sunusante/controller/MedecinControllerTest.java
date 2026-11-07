package sn.uadb.sunusante.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sn.uadb.sunusante.dto.CreateMedecinRequest;
import sn.uadb.sunusante.dto.MedecinDTO;
import sn.uadb.sunusante.service.MedecinService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedecinController.class)
class MedecinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedecinService medecinService;

    @Test
    @DisplayName("POST /api/medecins (F7) - Doit créer un médecin et renvoyer 201 Created")
    void testCreateMedecinSuccess() throws Exception {
        CreateMedecinRequest request = new CreateMedecinRequest(
                "Sow", "Aminata", "Pédiatrie", "+221 77 987 65 43", 1L
        );

        MedecinDTO createdDto = new MedecinDTO();
        createdDto.setId(5L);
        createdDto.setNom("Sow");
        createdDto.setPrenom("Aminata");
        createdDto.setSpecialite("Pédiatrie");
        createdDto.setTelephone("+221 77 987 65 43");
        createdDto.setEtablissementId(1L);
        createdDto.setEtablissementNom("Centre de Santé de Bambey");

        when(medecinService.createMedecin(any(CreateMedecinRequest.class))).thenReturn(createdDto);

        mockMvc.perform(post("/api/medecins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.nom").value("Sow"))
                .andExpect(jsonPath("$.specialite").value("Pédiatrie"));
    }

    @Test
    @DisplayName("POST /api/medecins - Doit renvoyer 400 Bad Request en cas de champs invalides")
    void testCreateMedecinValidationFailure() throws Exception {
        // Envoi d'un objet avec nom vide et établissement null
        CreateMedecinRequest invalidRequest = new CreateMedecinRequest(
                "", "", "", "", null
        );

        mockMvc.perform(post("/api/medecins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.validationErrors").isArray());
    }
}
