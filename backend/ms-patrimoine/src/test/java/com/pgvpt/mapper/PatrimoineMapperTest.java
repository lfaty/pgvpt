package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PatrimoineMapperTest {

    @Autowired
    private PatrimoineMapper patrimoineMapper;

    @Test
    void testMonumentMapping_ShouldNotBeNull() {
        // Arrange
        MonumentCreate dto = new MonumentCreate();
        dto.setNom("Tour Eiffel");
        dto.setDescription("Monument célèbre");
        dto.setIdentiteArchitecte("Gustave Eiffel");
        dto.setCategorie(CategoriePatrimoine.PATRIMOINE_CULTUREL);
        dto.setStatut(StatutPatrimoine.PUBLIE);
        dto.setNatureMonument(NatureMonument.MONUMENT_HISTORIQUE);

        // Act
        MonumentEntity entity = patrimoineMapper.toMonumentEntity(dto);

        // Assert
        assertNotNull(entity, "L'entité générée ne doit pas être nulle");
        assertEquals("Tour Eiffel", entity.getNom());
        assertEquals("Monument célèbre", entity.getDescription());
        assertEquals("Gustave Eiffel", entity.getIdentiteArchitecte());
        assertEquals(CategoriePatrimoine.PATRIMOINE_CULTUREL.getValue(), entity.getCategorie());
        assertEquals(StatutPatrimoine.PUBLIE.getValue(), entity.getStatut());
        assertEquals(NatureMonument.MONUMENT_HISTORIQUE.getValue(), entity.getNatureMonument());
    }
    
    @Test
    void testPolymorphicMapping_ShouldReturnCorrectEntity() {
        // Arrange
        SiteNaturelCreate dto = new SiteNaturelCreate();
        dto.setNom("Parc National");
        dto.setNatureSite(NatureSiteNaturel.PARC_NATIONAL);

        // Act
        PatrimoineEntity entity = patrimoineMapper.toEntity(dto);

        // Assert
        assertNotNull(entity, "L'entité polymorphe ne doit pas être nulle");
        assertTrue(entity instanceof SiteNaturelEntity, "L'entité doit être de type SiteNaturelEntity");
        
        SiteNaturelEntity siteEntity = (SiteNaturelEntity) entity;
        assertEquals("Parc National", siteEntity.getNom());
        assertEquals(NatureSiteNaturel.PARC_NATIONAL.getValue(), siteEntity.getNatureSite());
    }
}
