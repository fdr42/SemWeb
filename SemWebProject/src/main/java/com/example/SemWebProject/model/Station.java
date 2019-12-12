package com.example.SemWebProject.model;

import org.apache.jena.rdf.model.RDFNode;

import java.util.List;

public class Station {
    private String stationId;
    private String locationCity;
    private String stationName;
    private String address;
    private Integer capacity;
    private Double latitude;
    private Double longitude;




    public Station(RDFNode stationId, LocationCity locationCity, RDFNode stationName, RDFNode capacity, RDFNode latitude, RDFNode longitude, RDFNode address) {
        this.stationId = stationId.asLiteral().getString();
        this.locationCity = locationCity.getCityName().asLiteral().getString();
        this.stationName = stationName.asLiteral().getString();
        this.capacity = capacity.asLiteral().getInt();
        this.latitude = latitude.asLiteral().getDouble();
        this.longitude = longitude.asLiteral().getDouble();
        this.address = address.asLiteral().getString();
    }
    public String getAddress() {
        return address;
    }
    public String getLocationCity() {
        return locationCity;
    }


    public String getStationName() {
        return stationName;
    }


    public Integer getCapacity() {
        return capacity;
    }


    public Double getLatitude() {
        return latitude;
    }


    public Double getLongitude() {
        return longitude;
    }


    public String getStationId() {
        return stationId;
    }

}
