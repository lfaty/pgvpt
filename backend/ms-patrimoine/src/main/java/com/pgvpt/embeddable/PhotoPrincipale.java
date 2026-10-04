package com.pgvpt.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoPrincipale {
    private String nomPhotoPrincipale;
    private String urlPhotoPrincipale;
}
