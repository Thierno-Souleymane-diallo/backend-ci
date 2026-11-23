-- ==============================================================================
-- PROJET DEVOPS MASTER 2 SI-SR (UADB) - SUNUSANTÉ
-- Script DML : Données d'amorçage sécurisées pour le Sénégal
-- ============================================================================== 

-- INSERTION DES ÉTABLISSEMENTS : noms réalistes et adaptés au contexte sénégalais
INSERT INTO etablissement (nom, type, ville, adresse, telephone, mot_de_passe)
SELECT 'Clinique de Dakar', 'CLINIQUE', 'Dakar', 'Avenue Cheikh Anta Diop, Dakar', '+221 33 824 15 15', '$2b$10$z2HY5Ny/aTNBASOvMlhIE.ZWLAMcD8vt8HYp2O8a9lO29au//Lbdy'
WHERE NOT EXISTS (SELECT 1 FROM etablissement WHERE nom = 'Clinique de Dakar' AND ville = 'Dakar');

INSERT INTO etablissement (nom, type, ville, adresse, telephone, mot_de_passe)
SELECT 'Centre de Santé de Thiès', 'CENTRE_DE_SANTE', 'Thiès', 'Route de Dakar, Thiès', '+221 33 951 14 00', '$2b$10$z2HY5Ny/aTNBASOvMlhIE.ZWLAMcD8vt8HYp2O8a9lO29au//Lbdy'
WHERE NOT EXISTS (SELECT 1 FROM etablissement WHERE nom = 'Centre de Santé de Thiès' AND ville = 'Thiès');

INSERT INTO etablissement (nom, type, ville, adresse, telephone, mot_de_passe)
SELECT 'Hôpital Régional de Diourbel', 'HOPITAL', 'Diourbel', 'Route de Gossas, Diourbel', '+221 33 971 10 05', '$2b$10$z2HY5Ny/aTNBASOvMlhIE.ZWLAMcD8vt8HYp2O8a9lO29au//Lbdy'
WHERE NOT EXISTS (SELECT 1 FROM etablissement WHERE nom = 'Hôpital Régional de Diourbel' AND ville = 'Diourbel');

INSERT INTO etablissement (nom, type, ville, adresse, telephone, mot_de_passe)
SELECT 'Clinique Saint Michel', 'CLINIQUE', 'Saint-Louis', 'Boulevard du Président, Saint-Louis', '+221 33 961 45 10', '$2b$10$z2HY5Ny/aTNBASOvMlhIE.ZWLAMcD8vt8HYp2O8a9lO29au//Lbdy'
WHERE NOT EXISTS (SELECT 1 FROM etablissement WHERE nom = 'Clinique Saint Michel' AND ville = 'Saint-Louis');

INSERT INTO etablissement (nom, type, ville, adresse, telephone, mot_de_passe)
SELECT 'Centre de Santé de Bambey', 'CENTRE_DE_SANTE', 'Bambey', 'Quartier Escale, Bambey', '+221 33 973 11 22', '$2b$10$z2HY5Ny/aTNBASOvMlhIE.ZWLAMcD8vt8HYp2O8a9lO29au//Lbdy'
WHERE NOT EXISTS (SELECT 1 FROM etablissement WHERE nom = 'Centre de Santé de Bambey' AND ville = 'Bambey');

INSERT INTO etablissement (nom, type, ville, adresse, telephone, mot_de_passe)
SELECT 'Polyclinique du Cap-Vert', 'CLINIQUE', 'Dakar', 'Avenue Lamine Gueye, Dakar', '+221 77 111 22 33', '$2b$10$z2HY5Ny/aTNBASOvMlhIE.ZWLAMcD8vt8HYp2O8a9lO29au//Lbdy'
WHERE NOT EXISTS (SELECT 1 FROM etablissement WHERE nom = 'Polyclinique du Cap-Vert' AND ville = 'Dakar');

-- INSERTION DES MÉDECINS
INSERT INTO medecin (nom, prenom, specialite, telephone, etablissement_id)
SELECT 'Ndiaye', 'Cheikh', 'Pédiatrie', '+221 77 123 45 67', id
FROM etablissement WHERE nom = 'Centre de Santé de Bambey'
AND NOT EXISTS (SELECT 1 FROM medecin WHERE nom = 'Ndiaye' AND prenom = 'Cheikh' AND etablissement_id = (SELECT id FROM etablissement WHERE nom = 'Centre de Santé de Bambey'));

INSERT INTO medecin (nom, prenom, specialite, telephone, etablissement_id)
SELECT 'Diallo', 'Aminata', 'Médecine générale', '+221 77 234 56 78', id
FROM etablissement WHERE nom = 'Clinique de Dakar'
AND NOT EXISTS (SELECT 1 FROM medecin WHERE nom = 'Diallo' AND prenom = 'Aminata' AND etablissement_id = (SELECT id FROM etablissement WHERE nom = 'Clinique de Dakar'));

INSERT INTO medecin (nom, prenom, specialite, telephone, etablissement_id)
SELECT 'Sarr', 'Modou', 'Cardiologie', '+221 76 345 67 89', id
FROM etablissement WHERE nom = 'Hôpital Régional de Diourbel'
AND NOT EXISTS (SELECT 1 FROM medecin WHERE nom = 'Sarr' AND prenom = 'Modou' AND etablissement_id = (SELECT id FROM etablissement WHERE nom = 'Hôpital Régional de Diourbel'));

INSERT INTO medecin (nom, prenom, specialite, telephone, etablissement_id)
SELECT 'Seck', 'Khady', 'Ophtalmologie', '+221 77 567 06 01', id
FROM etablissement WHERE nom = 'Centre de Santé de Thiès'
AND NOT EXISTS (SELECT 1 FROM medecin WHERE nom = 'Seck' AND prenom = 'Khady' AND etablissement_id = (SELECT id FROM etablissement WHERE nom = 'Centre de Santé de Thiès'));

INSERT INTO medecin (nom, prenom, specialite, telephone, etablissement_id)
SELECT 'Gueye', 'Marième', 'Gynécologie-obstétrique', '+221 77 234 02 02', id
FROM etablissement WHERE nom = 'Clinique Saint Michel'
AND NOT EXISTS (SELECT 1 FROM medecin WHERE nom = 'Gueye' AND prenom = 'Marième' AND etablissement_id = (SELECT id FROM etablissement WHERE nom = 'Clinique Saint Michel'));

INSERT INTO medecin (nom, prenom, specialite, telephone, etablissement_id)
SELECT 'Fall', 'Ibrahima', 'Dermatologie', '+221 77 345 03 02', id
FROM etablissement WHERE nom = 'Polyclinique du Cap-Vert'
AND NOT EXISTS (SELECT 1 FROM medecin WHERE nom = 'Fall' AND prenom = 'Ibrahima' AND etablissement_id = (SELECT id FROM etablissement WHERE nom = 'Polyclinique du Cap-Vert'));

-- INSERTION DES RENDEZ-VOUS
INSERT INTO rendez_vous (nom_patient, telephone_patient, date_heure, motif, statut, medecin_id)
SELECT 'Awa Diop', '+221 77 500 11 22', CURRENT_DATE + TIME '08:30', 'Fièvre persistante', 'EN_ATTENTE', id
FROM medecin WHERE nom = 'Ndiaye' AND prenom = 'Cheikh'
AND NOT EXISTS (SELECT 1 FROM rendez_vous WHERE nom_patient = 'Awa Diop' AND telephone_patient = '+221 77 500 11 22');

INSERT INTO rendez_vous (nom_patient, telephone_patient, date_heure, motif, statut, medecin_id)
SELECT 'Mamadou Faye', '+221 77 600 22 33', CURRENT_DATE + TIME '09:15', 'Suivi cardiaque', 'EN_CONSULTATION', id
FROM medecin WHERE nom = 'Sarr' AND prenom = 'Modou'
AND NOT EXISTS (SELECT 1 FROM rendez_vous WHERE nom_patient = 'Mamadou Faye' AND telephone_patient = '+221 77 600 22 33');

INSERT INTO rendez_vous (nom_patient, telephone_patient, date_heure, motif, statut, medecin_id)
SELECT 'Khady Ba', '+221 76 700 33 44', CURRENT_DATE + TIME '10:00', 'Consultation générale', 'TERMINE', id
FROM medecin WHERE nom = 'Diallo' AND prenom = 'Aminata'
AND NOT EXISTS (SELECT 1 FROM rendez_vous WHERE nom_patient = 'Khady Ba' AND telephone_patient = '+221 76 700 33 44');
