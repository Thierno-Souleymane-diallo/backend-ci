package sn.uadb.sunusante.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.uadb.sunusante.dto.CreateMedecinRequest;
import sn.uadb.sunusante.dto.MedecinDTO;
import sn.uadb.sunusante.exception.ErrorResponse;
import sn.uadb.sunusante.service.MedecinService;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@CrossOrigin(origins = "*")
@Tag(name = "Médecins & Praticiens", description = "Gestion du corps médical et ajout de nouveaux praticiens")
public class MedecinController {

    private final MedecinService medecinService;

    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }

    /**
     * Liste tous les médecins enregistrés.
     */
    @GetMapping
    @Operation(
        summary = "Lister tous les médecins",
        description = "Retourne la liste globale de tous les médecins du réseau SunuSanté."
    )
    public ResponseEntity<List<MedecinDTO>> getAllMedecins() {
        return ResponseEntity.ok(medecinService.getAllMedecins());
    }

    /**
     * Récupère un médecin par son ID.
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Consulter le profil d'un médecin",
        description = "Retourne les informations d'un praticien ainsi que son établissement de rattachement."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Médecin trouvé"),
        @ApiResponse(responseCode = "404", description = "Médecin introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MedecinDTO> getMedecinById(
            @Parameter(description = "Identifiant unique du médecin", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(medecinService.getMedecinById(id));
    }

    /**
     * Endpoint F7 : Enregistrer un nouveau médecin rattaché à une structure.
     */
    @PostMapping
    @Operation(
        summary = "Enregistrer un nouveau médecin (F7)",
        description = "Crée un nouveau profil médecin rattaché à un établissement de santé existant. Renvoie le code HTTP 201 Created."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Médecin créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données d'entrée invalides", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Établissement rattaché introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MedecinDTO> createMedecin(
            @Valid @RequestBody CreateMedecinRequest request) {
        MedecinDTO created = medecinService.createMedecin(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
