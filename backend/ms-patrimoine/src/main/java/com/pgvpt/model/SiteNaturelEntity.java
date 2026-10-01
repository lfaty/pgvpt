package com.pgvpt.model;

import com.pgvpt.enums.NatureSiteNaturelMetier;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site_naturels")
@DiscriminatorValue("SITE_NATUREL")
@Getter
@Setter
public class SiteNaturelEntity extends PatrimoineEntity {

    private Double superficie;
    private Integer capaciteAccueil;

    @Enumerated(EnumType.STRING)
    private NatureSiteNaturelMetier natureSite;

    private String ecosysteme;
    private String biodiversite;
    private boolean zoneProtegee;
    private String categorieProtection;


    @OneToMany(mappedBy = "siteNaturel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EspeceProtegeeEntity> especesProtegees = new ArrayList<>();

//    public void addEspeceProtegee(EspeceProtegeeEntity espece) {
//        this.especesProtegees.add(espece);
//        espece.setSiteNaturel(this);
//    }

}