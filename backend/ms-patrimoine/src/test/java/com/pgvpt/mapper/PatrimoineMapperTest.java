package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.enums.CategoriePatrimoineMetier;
import com.pgvpt.enums.StatutPatrimoineMetier;
import com.pgvpt.enums.TypePatrimoineMetier;
import com.pgvpt.model.*;
import com.pgvpt.record.PatrimoineSearchCriteria;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatrimoineMapperTest {

    @Autowired
    private PatrimoineMapper patrimoineMapper;

    @Test
    @DisplayName("Devrait mapper un MonumentCreate en MonumentEntity avec les Enums métier")
    void testMonumentMapping_ShouldNotBeNull() {
        // Arrange
        MonumentCreate dto = new MonumentCreate();
        dto.setNom("Tour Eiffel");
        dto.setType(TypePatrimoine.MONUMENT);
        dto.setDescription("Monument célèbre");
        dto.setIdentiteArchitecte("Gustave Eiffel");
        dto.setCategorie(CategoriePatrimoine.PATRIMOINE_CULTUREL);
        dto.setStatut(StatutPatrimoine.PUBLIE);
        dto.setNatureMonument(NatureMonument.MONUMENT_HISTORIQUE);

        // Act
        MonumentEntity entity = patrimoineMapper.toMonumentEntity(dto);

        // Assert
        assertThat(entity)
                .as("L'entité générée ne doit pas être nulle")
                .isNotNull();

        assertThat(entity.getNom()).isEqualTo("Tour Eiffel");
        assertThat(entity.getDescription()).isEqualTo("Monument célèbre");
        assertThat(entity.getIdentiteArchitecte()).isEqualTo("Gustave Eiffel");

        // Vérification du mapping des enums gérées par le PatrimoineMapper
        assertThat(entity.getCategorie()).isEqualTo(CategoriePatrimoineMetier.PATRIMOINE_CULTUREL);
        assertThat(entity.getStatut()).isEqualTo(StatutPatrimoineMetier.PUBLIE);

        // Si natureMonument est stocké en String ou Enum DTO dans votre entité
        assertThat(entity.getNatureMonument()).isNotNull();
    }

    @Test
    @DisplayName("Devrait appliquer le mapping polymorphe via toEntity")
    void testPolymorphicMapping_ShouldReturnCorrectEntity() {
        // Given
        MonumentCreate monumentDto = new MonumentCreate();
        monumentDto.setNom("Maison des Esclaves");
        monumentDto.setType(TypePatrimoine.MONUMENT);
        monumentDto.setCategorie(CategoriePatrimoine.PATRIMOINE_MEMORIEL);
        monumentDto.setStyleArchitectural("Architecture coloniale");
        monumentDto.setMateriauxConstruction(List.of("Pierre", "Bois"));

        // When
        PatrimoineEntity resultEntity = patrimoineMapper.toEntity(monumentDto);

        // Then
        assertThat(resultEntity)
                .isNotNull()
                .isInstanceOf(MonumentEntity.class);

        MonumentEntity monumentEntity = (MonumentEntity) resultEntity;
        assertThat(monumentEntity.getNom()).isEqualTo("Maison des Esclaves");
        assertThat(monumentEntity.getStyleArchitectural()).isEqualTo("Architecture coloniale");

        assertThat(monumentEntity.getMateriauxConstruction())
                .asInstanceOf(InstanceOfAssertFactories.list(String.class))
                .containsExactly("Pierre", "Bois");
    }

    @Test
    @DisplayName("Devrait convertir les paramètres d'entrée en record PatrimoineSearchCriteria")
    void testToCriteriaMapping_ShouldReturnCorrectRecord() {
        // Given & When
        PatrimoineSearchCriteria criteria = patrimoineMapper.toCriteria(
                CategoriePatrimoine.PATRIMOINE_NATUREL,
                TypePatrimoine.SITE_NATUREL,
                StatutPatrimoine.PUBLIE,
                EtatConservation.EXCELLENT,
                true,
                false,
                true,
                "Saloum"
        );

        // Then
        assertThat(criteria).isNotNull();
        assertThat(criteria.categorie()).isEqualTo(CategoriePatrimoineMetier.PATRIMOINE_NATUREL);
        assertThat(criteria.type()).isEqualTo(TypePatrimoineMetier.SITE_NATUREL);
        assertThat(criteria.statut()).isEqualTo(StatutPatrimoineMetier.PUBLIE);
        assertThat(criteria.accessiblePublic()).isTrue();
        assertThat(criteria.q()).isEqualTo("Saloum");
    }
}
