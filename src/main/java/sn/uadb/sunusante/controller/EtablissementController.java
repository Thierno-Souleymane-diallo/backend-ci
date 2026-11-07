package sn.uadb.sunusante.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.uadb.sunusante.dto.EtablissementDTO;
import sn.uadb.sunusante.dto.MedecinDTO;
import sn.uadb.sunusante.exception.ErrorResponse;
import sn.uadb.sunusante.service.EtablissementService;

import java.util.List;

@RestController
@RequestMapping("/api/etablissements")
@CrossOrigin(origins = "*")
@Tag(name = "Établissements de Santé", description = "Gestion des structures médicales et consultation de leurs médecins")
public class EtablissementController {

    private final EtablissementService etablissementService;

    public EtablissementController(EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }

    /**
     * Endpoint F1 : Consulter la liste des structures sanitaires avec filtre par ville.
     */
    @GetMapping
    @Operation(
        summary = "Lister les établissements sanitaires (F1)",
        description = "Retourne la liste des structures de santé partenaires au Sénégal, avec option de filtre insensible à la casse sur la ville (Bambey, Dakar, Thiès, etc.)."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    public ResponseEntity<List<EtablissementDTO>> getEtablissements(
            @Parameter(description = "Nom de la ville pour filtrer (ex: Bambey, Dakar)", example = "Bambey")
            @RequestParam(required = false) String ville) {
        List<EtablissementDTO> etablissements = etablissementService.getEtablissements(ville);
        return ResponseEntity.ok(etablissements);
    }

    /**
     * Recherche d'un établissement par son identifiant.
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Consulter le détail d'un établissement par son ID",
        description = "Retourne les informations complètes d'un établissement donné."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Établissement trouvé"),
        @ApiResponse(responseCode = "404", description = "Établissement introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EtablissementDTO> getEtablissementById(
            @Parameter(description = "Identifiant unique de l'établissement", example = "1")
            @PathVariable Long id) {
        EtablissementDTO dto = etablissementService.getEtablissementById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint F2 : Consulter les médecins d'un établissement avec filtre de spécialité.
     */
    @GetMapping("/{id}/medecins")
    @Operation(
        summary = "Lister les médecins d'un établissement (F2)",
        description = "Retourne la liste des praticiens en service dans la structure médicale ciblée, avec possibilité de filtrer par spécialité (Pédiatrie, Cardiologie, etc.)."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Médecins récupérés avec succès"),
        @ApiResponse(responseCode = "404", description = "Établissement introuvable", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<MedecinDTO>> getMedecinsByEtablissement(
            @Parameter(description = "Identifiant de l'établissement", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Spécialité médicale pour filtrer (ex: Pédiatrie)", example = "Pédiatrie")
            @RequestParam(required = false) String specialite) {
        List<MedecinDTO> medecins = etablissementService.getMedecinsByEtablissement(id, specialite);
        return ResponseEntity.ok(medecins);
    }
}
