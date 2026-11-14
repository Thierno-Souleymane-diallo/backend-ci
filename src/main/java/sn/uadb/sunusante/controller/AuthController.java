package sn.uadb.sunusante.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.uadb.sunusante.repository.EtablissementRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EtablissementRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(EtablissementRepository repository) {
        this.repository = repository;
    }

    public record LoginRequest(String nom, String motDePasse) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String nom = req.nom() == null ? "" : req.nom().trim();
        String mdp = req.motDePasse() == null ? "" : req.motDePasse();

        return repository.findByNomIgnoreCase(nom)
                .filter(e -> e.getMotDePasse() != null && encoder.matches(mdp, e.getMotDePasse()))
                .<ResponseEntity<?>>map(e -> ResponseEntity.ok(Map.of("id", e.getId(), "nom", e.getNom())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Nom d'établissement ou mot de passe incorrect.")));
    }
}
