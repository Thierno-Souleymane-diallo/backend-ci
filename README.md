# Microservice Backend — SunuSanté (`backend-ci`)

**Projet :** Examen Pratique DevOps (Master 2 SI-SR - UADB)  
**Auteur :** Tacko Ciss (Lead Backend & Scrum Master)  
**Technologie :** Java 21, Spring Boot 3.3.4, Maven, Spring Data JPA, PostgreSQL 16

---

## 📌 Présentation

Ce dépôt contient le microservice Backend de l'application **SunuSanté**. Il expose une API REST documentée avec Swagger (OpenAPI) pour la gestion des structures sanitaires, des médecins et des rendez-vous au Sénégal.

Il intègre également l'endpoint technique de surveillance `/health` indispensable aux healthchecks des pipelines CI/CD Jenkins et des orchestrateurs Docker.

---

## 🚀 Démarrage rapide en local

### Prérequis

- Java 21 (OpenJDK)
- Maven 3.8+
- PostgreSQL 16 (ou conteneur Docker)

### 1. Cloner le projet

```bash
git clone https://github.com/<votre-organisation>/backend-ci.git
cd backend-ci
```

### 2. Variables d'environnement

Le microservice est configurable via des variables d'environnement (avec valeurs par défaut) :

| Variable | Description | Valeur par défaut |
| :--- | :--- | :--- |
| `SERVER_PORT` | Port d'écoute HTTP | `8080` |
| `DB_HOST` | Hôte PostgreSQL | `localhost` |
| `DB_PORT` | Port PostgreSQL | `5432` |
| `DB_NAME` | Nom de la base de données | `sunusante_db` |
| `DB_USER` | Utilisateur PostgreSQL | `sunusante` |
| `DB_PASSWORD` | Mot de passe | `sunusante_secure_password` |
| `JPA_DDL_AUTO` | Stratégie Hibernate DDL | `update` |

### 3. Exécuter les tests unitaires

```bash
mvn clean test
```

### 4. Lancer l'application

```bash
mvn spring-boot:run
```

---

## 🩺 Endpoints Clés

### Endpoint de Santé (Healthcheck)

- **URL :** `http://localhost:8080/health`
- **Méthode :** `GET`
- **Réponse HTTP 200 :**

```json
{
  "status": "UP",
  "service": "sunusante-backend",
  "version": "1.0.0",
  "timestamp": "2026-11-06T23:00:00.000Z",
  "message": "SunuSanté Backend API is healthy and operational"
}
```

### Documentation interactive Swagger UI

- **URL :** `http://localhost:8080/swagger-ui.html`
- **Spécification OpenAPI (JSON) :** `http://localhost:8080/api-docs`

---

## 🏗️ Structure du Dépôt

```text
backend-ci/
├── pom.xml                                 # Configuration Maven et dépendances
├── README.md                               # Documentation technique du dépôt
├── src/
│   ├── main/
│   │   ├── java/sn/uadb/sunusante/
│   │   │   ├── SunuSanteBackendApplication.java
│   │   │   ├── controller/
│   │   │   │   └── HealthController.java   # Contrôleur /health
│   │   │   ├── dto/
│   │   │   │   └── HealthResponse.java     # Format de réponse standard
│   │   │   ├── model/                      # Entités JPA (Étape suivante)
│   │   │   ├── repository/                 # Interfaces Spring Data JPA
│   │   │   └── service/                    # Logique métier
│   │   └── resources/
│   │       ├── application.yml             # Paramètres Spring Boot & BDD
│   │       ├── schema.sql                  # Schéma PostgreSQL
│   │       └── data.sql                    # Données d'amorçage réalistes
│   └── test/
│       ├── java/sn/uadb/sunusante/
│       │   ├── SunuSanteBackendApplicationTests.java
│       │   └── controller/
│       │       └── HealthControllerTest.java # Test MockMvc /health
│       └── resources/
│           └── application.yml             # Configuration H2 pour CI
```

Pipeline CI : declenchement automatique par Jenkins (polling toutes les 2 minutes)
