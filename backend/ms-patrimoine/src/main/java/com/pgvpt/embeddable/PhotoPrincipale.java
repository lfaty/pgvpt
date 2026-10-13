package com.pgvpt.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoPrincipale {
    @Column(columnDefinition = "TEXT")
    private String nomPhotoPrincipale;
    @Column(columnDefinition = "TEXT")
    private String urlPhotoPrincipale;
}
