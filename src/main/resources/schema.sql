-- ==============================================================================
-- PROJET DEVOPS MASTER 2 SI-SR (UADB) - SUNUSANTÉ
-- Script DDL : Modélisation et création du schéma relationnel PostgreSQL
-- Auteur : Tacko Ciss (Lead Backend & Base de données)
-- Date : Novembre 2026
-- ==============================================================================

-- 1. Nettoyage préventif des tables (ordre respectant les clés étrangères)
DROP TABLE IF EXISTS rendez_vous CASCADE;
DROP TABLE IF EXISTS medecin CASCADE;
DROP TABLE IF EXISTS etablissement CASCADE;

-- ==============================================================================
-- TABLE 1 : etablissement
-- Représente les structures de santé partenaires au Sénégal
-- ==============================================================================
CREATE TABLE etablissement (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('HOPITAL', 'CENTRE_DE_SANTE', 'POSTE_DE_SANTE', 'CLINIQUE')),
    ville VARCHAR(100) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    telephone VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE etablissement IS 'Structures sanitaires (hôpitaux, centres et postes de santé)';
COMMENT ON COLUMN etablissement.type IS 'Type d''établissement : HOPITAL, CENTRE_DE_SANTE, POSTE_DE_SANTE, CLINIQUE';
COMMENT ON COLUMN etablissement.ville IS 'Ville d''implantation (ex: Bambey, Dakar, Thiès)';

-- ==============================================================================
-- TABLE 2 : medecin
-- Praticiens rattachés à un établissement médical
-- ==============================================================================
CREATE TABLE medecin (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    specialite VARCHAR(100) NOT NULL,
    telephone VARCHAR(30),
    etablissement_id INT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_medecin_etablissement 
        FOREIGN KEY (etablissement_id) 
        REFERENCES etablissement(id) 
        ON DELETE CASCADE
);

COMMENT ON TABLE medecin IS 'Médecins et spécialistes rattachés à un établissement';
COMMENT ON COLUMN medecin.specialite IS 'Domaine médical (ex: Pédiatrie, Cardiologie, Médecine Générale)';

-- ==============================================================================
-- TABLE 3 : rendez_vous
-- Réservations de consultations des patients et gestion de file d'attente
-- ==============================================================================
CREATE TABLE rendez_vous (
    id SERIAL PRIMARY KEY,
    nom_patient VARCHAR(150) NOT NULL,
    telephone_patient VARCHAR(30) NOT NULL,
    date_heure TIMESTAMP NOT NULL,
    motif VARCHAR(255),
    statut VARCHAR(30) NOT NULL DEFAULT 'EN_ATTENTE' 
        CHECK (statut IN ('EN_ATTENTE', 'EN_CONSULTATION', 'TERMINE', 'ANNULE')),
    medecin_id INT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_rendez_vous_medecin 
        FOREIGN KEY (medecin_id) 
        REFERENCES medecin(id) 
        ON DELETE CASCADE
);

COMMENT ON TABLE rendez_vous IS 'Rendez-vous et suivi de la file d''attente quotidienne';
COMMENT ON COLUMN rendez_vous.statut IS 'Statut de consultation : EN_ATTENTE, EN_CONSULTATION, TERMINE, ANNULE';

-- ==============================================================================
-- INDEX DE PERFORMANCE (Optimisation des endpoints de l'API REST)
-- ==============================================================================

-- Pour la recherche et filtrage par ville (Endpoint F1 : /api/etablissements?ville=)
CREATE INDEX idx_etablissement_ville ON etablissement(ville);

-- Pour lister les médecins d'une structure (Endpoint F2 : /api/etablissements/{id}/medecins)
CREATE INDEX idx_medecin_etablissement ON medecin(etablissement_id);

-- Pour filtrer les médecins par spécialité
CREATE INDEX idx_medecin_specialite ON medecin(specialite);

-- Pour retrouver les consultations d'un patient par son téléphone (Endpoint F4 : /api/rendez-vous?telephone=)
CREATE INDEX idx_rendez_vous_telephone ON rendez_vous(telephone_patient);

-- Pour afficher et trier rapidement la file d'attente du jour (Endpoint F5 : /api/etablissements/{id}/file-attente)
CREATE INDEX idx_rendez_vous_date_heure ON rendez_vous(date_heure);
CREATE INDEX idx_rendez_vous_statut ON rendez_vous(statut);
