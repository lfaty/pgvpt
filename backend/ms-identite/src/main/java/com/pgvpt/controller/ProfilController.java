package com.pgvpt.controller;

import com.pgvpt.api.ProfilsApi;
import com.pgvpt.dto.ProfilUpdateRequest;
import com.pgvpt.dto.Utilisateur;
import org.springframework.http.ResponseEntity;

public class ProfilController implements ProfilsApi{

    @Override
    public ResponseEntity<Utilisateur> getCurrentUser() {
        return null;
    }

    @Override
    public ResponseEntity<Utilisateur> updateCurrentUser(ProfilUpdateRequest profilUpdateRequest) {
        return null;
    }
}
