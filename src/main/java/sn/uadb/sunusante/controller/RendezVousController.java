package sn.uadb.sunusante.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.uadb.sunusante.dto.CreateRendezVousRequest;
import sn.uadb.sunusante.dto.RendezVousDTO;
import sn.uadb.sunusante.dto.UpdateRendezVousStatutRequest;
import sn.uadb.sunusante.service.RendezVousService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Rendez-vous", description = "Prise de rendez-vous et suivi des consultations")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @PostMapping("/rendez-vous")
    @Operation(
        summary = "Prendre un rendez-vous",
        description = "Crée un rendez-vous pour un patient avec un médecin donné. Le statut initial est EN_ATTENTE."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Rendez-vous créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Médecin introuvable")
    })
    public ResponseEntity<RendezVousDTO> createRendezVous(@Valid @RequestBody CreateRendezVousRequest request) {
        RendezVousDTO created = rendezVousService.createRendezVous(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/rendez-vous/{id}")
    @Operation(summary = "Récupérer un rendez-vous par son identifiant")
    public ResponseEntity<RendezVousDTO> getRendezVousById(@PathVariable Long id) {
        return ResponseEntity.ok(rendezVousService.getRendezVousById(id));
    }

    @GetMapping("/rendez-vous")
    @Operation(summary = "Rechercher les rendez-vous d'un patient par téléphone")
    public ResponseEntity<List<RendezVousDTO>> getRendezVousByTelephone(
            @Parameter(description = "Téléphone du patient", example = "+221771234567")
            @RequestParam(required = false) String telephone) {
        return ResponseEntity.ok(rendezVousService.getRendezVousByTelephone(telephone));
    }

    @RequestMapping(value = "/rendez-vous/{id}/statut", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Operation(summary = "Mettre à jour le statut d'un rendez-vous")
    public ResponseEntity<RendezVousDTO> updateStatut(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRendezVousStatutRequest request) {
        return ResponseEntity.ok(rendezVousService.updateStatut(id, request.getStatut()));
    }

    @GetMapping({"/etablissements/{etablissementId}/file-attente", "/rendez-vous/etablissement/{etablissementId}/file-attente"})
    @Operation(summary = "Afficher la file d'attente d'un établissement pour une date donnée")
    public ResponseEntity<List<RendezVousDTO>> getFileAttente(
            @PathVariable Long etablissementId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(rendezVousService.getFileAttente(etablissementId, date));
    }
}
