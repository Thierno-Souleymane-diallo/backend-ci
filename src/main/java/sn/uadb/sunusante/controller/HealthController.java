package sn.uadb.sunusante.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.uadb.sunusante.dto.HealthResponse;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Health Check", description = "Endpoint technique de surveillance de l'état du microservice")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Vérifier la santé du service backend", 
               description = "Utilisé par les pipelines CI/CD Jenkins et les orchestrateurs pour les healthchecks post-déploiement.")
    public ResponseEntity<HealthResponse> checkHealth() {
        HealthResponse response = new HealthResponse(
                "UP",
                "sunusante-backend",
                "1.0.0",
                "SunuSanté Backend API is healthy and operational"
        );
        return ResponseEntity.ok(response);
    }
}
