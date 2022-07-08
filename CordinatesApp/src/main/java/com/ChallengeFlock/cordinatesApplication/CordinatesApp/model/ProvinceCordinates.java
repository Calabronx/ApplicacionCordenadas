package com.ChallengeFlock.cordinatesApplication.CordinatesApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "provinceCordinates")
public class ProvinceCordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("long")
    private double longitude;

    private boolean isFound;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }

    public String returnLatAndLong(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;

        return toString();
    }

    @Override
    public String toString() {
        return "Cordenadas de la Provincia: {" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
