package com.pgvpt.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ContactPatrimoineEmbeddable {
    @Column(name = "contact_nom", columnDefinition = "TEXT")
    private String nom;

    @Column(name = "contact_fonction", columnDefinition = "TEXT")
    private String fonction;

    @Column(name = "contact_telephone", columnDefinition = "TEXT")
    private String telephone;

    @Column(name = "contact_email", columnDefinition = "TEXT")
    private String email;

    @Column(name = "contact_site_web", columnDefinition = "TEXT")
    private String siteWeb;
}
