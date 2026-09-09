package org.openapitools.configuration;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import com.pgvpt.dto.StatutContenu;
import com.pgvpt.dto.TypeContenu;

import jakarta.annotation.Generated;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * This class provides Spring Converter beans for the enum models in the OpenAPI specification.
 *
 * By default, Spring only converts primitive types to enums using Enum::valueOf, which can prevent
 * correct conversion if the OpenAPI specification is using an `enumPropertyNaming` other than
 * `original` or the specification has an integer enum.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-05T09:51:25.206125500Z[Africa/Dakar]", comments = "Generator version: 7.25.0")
@Configuration(value = "org.openapitools.configuration.enumConverterConfiguration")
public class EnumConverterConfiguration {

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.statutContenuConverter")
    Converter<String, StatutContenu> statutContenuConverter() {
        return new Converter<String, StatutContenu>() {
            @Override
            public StatutContenu convert(String source) {
                return StatutContenu.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.typeContenuConverter")
    Converter<String, TypeContenu> typeContenuConverter() {
        return new Converter<String, TypeContenu>() {
            @Override
            public TypeContenu convert(String source) {
                return TypeContenu.fromValue(source);
            }
        };
    }

}
