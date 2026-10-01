package com.pgvpt.embeddable;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.NiveauMenaceMetier;
import com.pgvpt.model.PatrimoineEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class MenaceEmbeddable {

    @Column(nullable = false, length = 150)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NiveauMenaceMetier niveau;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String mesuresPrevention;

}
