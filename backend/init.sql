-- Création des bases de données
CREATE DATABASE patrimoine_db;
CREATE DATABASE geolocalisation_db;

-- Connexion à la base geolocalisation et activation de PostGIS
\c geolocalisation_db;
CREATE EXTENSION IF NOT EXISTS postgis;

