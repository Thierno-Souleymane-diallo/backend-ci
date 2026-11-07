-- ==============================================================================
-- PROJET DEVOPS MASTER 2 SI-SR (UADB) - SUNUSANTÉ
-- Script DML : Données d'amorçage (Seed data) réalistes pour le Sénégal
-- Auteur : Tacko Ciss (Lead Backend & Base de données)
-- ==============================================================================

-- 1. Réinitialisation des séquences et nettoyage des données
TRUNCATE TABLE rendez_vous, medecin, etablissement RESTART IDENTITY CASCADE;

-- ==============================================================================
-- INSERTION 1 : ÉTABLISSEMENTS DE SANTÉ
-- Échantillons représentatifs : Bambey (siège UADB), Diourbel, Dakar, Thiès
-- ==============================================================================
INSERT INTO etablissement (nom, type, ville, adresse, telephone) VALUES
('Centre de Santé de Bambey', 'CENTRE_DE_SANTE', 'Bambey', 'Quartier Escale, face Préfecture', '+221 33 973 11 22'),
('Hôpital Régional Heinrich Lübke', 'HOPITAL', 'Diourbel', 'Route de Gossas', '+221 33 971 10 05'),
('Hôpital Général Idrissa Pouye (HOGIP)', 'HOPITAL', 'Dakar', 'Grand Yoff, Voie de Dégagement Nord', '+221 33 869 40 50'),
('Centre de Santé Gaspard Camara', 'CENTRE_DE_SANTE', 'Dakar', 'Avenue Cheikh Anta Diop', '+221 33 824 15 15'),
('Poste de Santé de Médina', 'POSTE_DE_SANTE', 'Dakar', 'Rue 22 angle 11, Médina', '+221 33 821 30 40'),
('Hôpital Régional de Thiès', 'HOPITAL', 'Thiès', 'Quartier Dixième, Route de Dakar', '+221 33 951 14 00');

-- ==============================================================================
-- INSERTION 2 : MÉDECINS ET PRATICIENS
-- Rattachés aux établissements créés ci-dessus
-- ==============================================================================
INSERT INTO medecin (nom, prenom, specialite, telephone, etablissement_id) VALUES
-- Centre de Santé de Bambey (id: 1)
('Ndiaye', 'Dr. Cheikh', 'Pédiatrie', '+221 77 123 01 01', 1),
('Sow', 'Dr. Fatou', 'Médecine Générale', '+221 77 123 01 02', 1),

-- Hôpital Régional de Diourbel (id: 2)
('Diallo', 'Dr. Amadou', 'Cardiologie', '+221 77 234 02 01', 2),
('Gueye', 'Dr. Marième', 'Gynécologie', '+221 77 234 02 02', 2),

-- HOGIP Dakar (id: 3)
('Diop', 'Dr. Babacar', 'Cardiologie', '+221 77 345 03 01', 3),
('Fall', 'Dr. Ibrahima', 'Dermatologie', '+221 77 345 03 02', 3),

-- Centre Gaspard Camara Dakar (id: 4)
('Ba', 'Dr. Mariama', 'Pédiatrie', '+221 77 456 04 01', 4),
('Kane', 'Dr. Ousmane', 'Médecine Générale', '+221 77 456 04 02', 4),

-- Hôpital Régional de Thiès (id: 6)
('Seck', 'Dr. Khady', 'Ophtalmologie', '+221 77 567 06 01', 6);

-- ==============================================================================
-- INSERTION 3 : RENDEZ-VOUS & FILE D'ATTENTE DU JOUR
-- Utilisation de CURRENT_DATE pour garantir que la file d'attente du jour 
-- fonctionne à n'importe quelle date de démonstration ou d'évaluation
-- ==============================================================================
INSERT INTO rendez_vous (nom_patient, telephone_patient, date_heure, motif, statut, medecin_id) VALUES
-- Consultations du jour pour le Dr. Cheikh Ndiaye (Bambey - id: 1)
('Awa Diallo', '+221 77 500 11 22', CURRENT_DATE + TIME '08:30:00', 'Fièvre persistante enfant (3 ans)', 'TERMINE', 1),
('Moussa Ba', '+221 78 600 33 44', CURRENT_DATE + TIME '09:15:00', 'Vaccination rappel 9 mois', 'EN_CONSULTATION', 1),
('Aminata Ndiaye', '+221 76 700 55 66', CURRENT_DATE + TIME '10:00:00', 'Contrôle toux et bronchite', 'EN_ATTENTE', 1),
('Modou Diagne', '+221 70 800 77 88', CURRENT_DATE + TIME '11:00:00', 'Visite de routine nourrisson', 'EN_ATTENTE', 1),

-- Consultations du jour pour le Dr. Fatou Sow (Bambey - id: 2)
('Oumar Sy', '+221 77 111 22 33', CURRENT_DATE + TIME '09:00:00', 'Bilan de santé général', 'TERMINE', 2),
('Khady Cissé', '+221 78 222 33 44', CURRENT_DATE + TIME '10:30:00', 'Hypertension et vertiges', 'EN_ATTENTE', 2),

-- Consultations à Dakar pour le Dr. Babacar Diop (HOGIP - id: 5)
('Mamadou Fall', '+221 77 333 44 55', CURRENT_DATE + TIME '09:30:00', 'Suivi post-infarctus', 'EN_CONSULTATION', 5),
('Aïssatou Sarr', '+221 76 444 55 66', CURRENT_DATE + TIME '11:30:00', 'Échographie cardiaque de contrôle', 'EN_ATTENTE', 5),
('Ibrahima Diouf', '+221 70 555 66 77', CURRENT_DATE + TIME '14:00:00', 'Palpitations anormales à l''effort', 'EN_ATTENTE', 5),

-- Consultation annulée d'exemple
('Seynabou Faye', '+221 77 666 77 88', CURRENT_DATE + TIME '15:00:00', 'Consultation générale', 'ANNULE', 2);
