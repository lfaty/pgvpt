package com.pgvpt.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ContactPatrimoineEmbeddable {
    @Column(name = "contact_nom", length = 255)
    private String nom;

    @Column(name = "contact_fonction", length = 150)
    private String fonction;

    @Column(name = "contact_telephone", length = 50)
    private String telephone;

    @Column(name = "contact_email", length = 255)
    private String email;

    @Column(name = "contact_site_web", length = 500)
    private String siteWeb;
}
