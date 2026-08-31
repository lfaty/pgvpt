package com.pgvpt.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Localisation
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0")
public class Localisation {

  private String adresse;

  private String region;

  private String departement;

  private String arrondissement;

  private String commune;

  private String quartier;

  private String village;

  private String lieuDit;

  private Double latitude;

  private Double longitude;

  private Double altitude;

  private Double precision;

  private String zoneGeographique;

  private String aireGeographique;

  private String codePostal;

  private Double distanceCentreVilleKm;

  public Localisation() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Localisation(Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Localisation adresse(String adresse) {
    this.adresse = adresse;
    return this;
  }

  /**
   * Get adresse
   * @return adresse
   */
  
  @Schema(name = "adresse", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("adresse")
  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public Localisation region(String region) {
    this.region = region;
    return this;
  }

  /**
   * Get region
   * @return region
   */
  
  @Schema(name = "region", example = "Dakar", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("region")
  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public Localisation departement(String departement) {
    this.departement = departement;
    return this;
  }

  /**
   * Get departement
   * @return departement
   */
  
  @Schema(name = "departement", example = "Dakar", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("departement")
  public String getDepartement() {
    return departement;
  }

  public void setDepartement(String departement) {
    this.departement = departement;
  }

  public Localisation arrondissement(String arrondissement) {
    this.arrondissement = arrondissement;
    return this;
  }

  /**
   * Get arrondissement
   * @return arrondissement
   */
  
  @Schema(name = "arrondissement", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("arrondissement")
  public String getArrondissement() {
    return arrondissement;
  }

  public void setArrondissement(String arrondissement) {
    this.arrondissement = arrondissement;
  }

  public Localisation commune(String commune) {
    this.commune = commune;
    return this;
  }

  /**
   * Get commune
   * @return commune
   */
  
  @Schema(name = "commune", example = "Gorée", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commune")
  public String getCommune() {
    return commune;
  }

  public void setCommune(String commune) {
    this.commune = commune;
  }

  public Localisation quartier(String quartier) {
    this.quartier = quartier;
    return this;
  }

  /**
   * Get quartier
   * @return quartier
   */
  
  @Schema(name = "quartier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("quartier")
  public String getQuartier() {
    return quartier;
  }

  public void setQuartier(String quartier) {
    this.quartier = quartier;
  }

  public Localisation village(String village) {
    this.village = village;
    return this;
  }

  /**
   * Get village
   * @return village
   */
  
  @Schema(name = "village", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("village")
  public String getVillage() {
    return village;
  }

  public void setVillage(String village) {
    this.village = village;
  }

  public Localisation lieuDit(String lieuDit) {
    this.lieuDit = lieuDit;
    return this;
  }

  /**
   * Get lieuDit
   * @return lieuDit
   */
  
  @Schema(name = "lieuDit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lieuDit")
  public String getLieuDit() {
    return lieuDit;
  }

  public void setLieuDit(String lieuDit) {
    this.lieuDit = lieuDit;
  }

  public Localisation latitude(Double latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * Get latitude
   * minimum: -90
   * maximum: 90
   * @return latitude
   */
  @NotNull @DecimalMin("-90") @DecimalMax("90") 
  @Schema(name = "latitude", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("latitude")
  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Localisation longitude(Double longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * Get longitude
   * minimum: -180
   * maximum: 180
   * @return longitude
   */
  @NotNull @DecimalMin("-180") @DecimalMax("180") 
  @Schema(name = "longitude", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("longitude")
  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Localisation altitude(Double altitude) {
    this.altitude = altitude;
    return this;
  }

  /**
   * Get altitude
   * @return altitude
   */
  
  @Schema(name = "altitude", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("altitude")
  public Double getAltitude() {
    return altitude;
  }

  public void setAltitude(Double altitude) {
    this.altitude = altitude;
  }

  public Localisation precision(Double precision) {
    this.precision = precision;
    return this;
  }

  /**
   * Précision de la géolocalisation en mètres
   * @return precision
   */
  
  @Schema(name = "precision", description = "Précision de la géolocalisation en mètres", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("precision")
  public Double getPrecision() {
    return precision;
  }

  public void setPrecision(Double precision) {
    this.precision = precision;
  }

  public Localisation zoneGeographique(String zoneGeographique) {
    this.zoneGeographique = zoneGeographique;
    return this;
  }

  /**
   * Get zoneGeographique
   * @return zoneGeographique
   */
  
  @Schema(name = "zoneGeographique", example = "Petite Côte", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("zoneGeographique")
  public String getZoneGeographique() {
    return zoneGeographique;
  }

  public void setZoneGeographique(String zoneGeographique) {
    this.zoneGeographique = zoneGeographique;
  }

  public Localisation aireGeographique(String aireGeographique) {
    this.aireGeographique = aireGeographique;
    return this;
  }

  /**
   * Get aireGeographique
   * @return aireGeographique
   */
  
  @Schema(name = "aireGeographique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("aireGeographique")
  public String getAireGeographique() {
    return aireGeographique;
  }

  public void setAireGeographique(String aireGeographique) {
    this.aireGeographique = aireGeographique;
  }

  public Localisation codePostal(String codePostal) {
    this.codePostal = codePostal;
    return this;
  }

  /**
   * Get codePostal
   * @return codePostal
   */
  
  @Schema(name = "codePostal", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("codePostal")
  public String getCodePostal() {
    return codePostal;
  }

  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }

  public Localisation distanceCentreVilleKm(Double distanceCentreVilleKm) {
    this.distanceCentreVilleKm = distanceCentreVilleKm;
    return this;
  }

  /**
   * Get distanceCentreVilleKm
   * @return distanceCentreVilleKm
   */
  
  @Schema(name = "distanceCentreVilleKm", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("distanceCentreVilleKm")
  public Double getDistanceCentreVilleKm() {
    return distanceCentreVilleKm;
  }

  public void setDistanceCentreVilleKm(Double distanceCentreVilleKm) {
    this.distanceCentreVilleKm = distanceCentreVilleKm;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Localisation localisation = (Localisation) o;
    return Objects.equals(this.adresse, localisation.adresse) &&
        Objects.equals(this.region, localisation.region) &&
        Objects.equals(this.departement, localisation.departement) &&
        Objects.equals(this.arrondissement, localisation.arrondissement) &&
        Objects.equals(this.commune, localisation.commune) &&
        Objects.equals(this.quartier, localisation.quartier) &&
        Objects.equals(this.village, localisation.village) &&
        Objects.equals(this.lieuDit, localisation.lieuDit) &&
        Objects.equals(this.latitude, localisation.latitude) &&
        Objects.equals(this.longitude, localisation.longitude) &&
        Objects.equals(this.altitude, localisation.altitude) &&
        Objects.equals(this.precision, localisation.precision) &&
        Objects.equals(this.zoneGeographique, localisation.zoneGeographique) &&
        Objects.equals(this.aireGeographique, localisation.aireGeographique) &&
        Objects.equals(this.codePostal, localisation.codePostal) &&
        Objects.equals(this.distanceCentreVilleKm, localisation.distanceCentreVilleKm);
  }

  @Override
  public int hashCode() {
    return Objects.hash(adresse, region, departement, arrondissement, commune, quartier, village, lieuDit, latitude, longitude, altitude, precision, zoneGeographique, aireGeographique, codePostal, distanceCentreVilleKm);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Localisation {\n");
    sb.append("    adresse: ").append(toIndentedString(adresse)).append("\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    departement: ").append(toIndentedString(departement)).append("\n");
    sb.append("    arrondissement: ").append(toIndentedString(arrondissement)).append("\n");
    sb.append("    commune: ").append(toIndentedString(commune)).append("\n");
    sb.append("    quartier: ").append(toIndentedString(quartier)).append("\n");
    sb.append("    village: ").append(toIndentedString(village)).append("\n");
    sb.append("    lieuDit: ").append(toIndentedString(lieuDit)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    altitude: ").append(toIndentedString(altitude)).append("\n");
    sb.append("    precision: ").append(toIndentedString(precision)).append("\n");
    sb.append("    zoneGeographique: ").append(toIndentedString(zoneGeographique)).append("\n");
    sb.append("    aireGeographique: ").append(toIndentedString(aireGeographique)).append("\n");
    sb.append("    codePostal: ").append(toIndentedString(codePostal)).append("\n");
    sb.append("    distanceCentreVilleKm: ").append(toIndentedString(distanceCentreVilleKm)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

