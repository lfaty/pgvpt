package com.pgvpt.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ContactEmbeddable {
    private String nom;

    private String fonction;

    private String telephone;

    private String email;

    private String siteWeb;
}
