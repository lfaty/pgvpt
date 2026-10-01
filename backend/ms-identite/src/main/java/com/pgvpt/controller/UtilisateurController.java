package com.pgvpt.controller;

import com.pgvpt.api.*;
import com.pgvpt.dto.Role;
import com.pgvpt.dto.StatutUtilisateur;
import com.pgvpt.dto.Utilisateur;
import com.pgvpt.dto.UtilisateurPage;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class UtilisateurController implements UtilisateursApi{

    @Override
    public ResponseEntity<Utilisateur> getUser(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<UtilisateurPage> searchUsers(Integer page, Integer size, String search, Role role, StatutUtilisateur statut, Boolean actif) {
        return null;
    }
}
