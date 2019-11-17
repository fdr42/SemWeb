package com.example.SemWebProject.model;

import org.apache.jena.rdf.model.RDFNode;

public class Station {
    private  RDFNode stationId;
    private  LocationCity locationCity;
    private RDFNode stationName;
    private RDFNode capacity;
    private  RDFNode latitude;
    private  RDFNode longitude;

    public Station(RDFNode stationId, LocationCity locationCity, RDFNode stationName, RDFNode capacity, RDFNode latitude, RDFNode longitude) {
        this.stationId = stationId;
        this.locationCity = locationCity;
        this.stationName = stationName;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationCity getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(LocationCity locationCity) {
        this.locationCity = locationCity;
    }

    public RDFNode getStationName() {
        return stationName;
    }

    public void setStationName(RDFNode stationName) {
        this.stationName = stationName;
    }

    public RDFNode getCapacity() {
        return capacity;
    }

    public void setCapacity(RDFNode capacity) {
        this.capacity = capacity;
    }

    public RDFNode getLatitude() {
        return latitude;
    }

    public void setLatitude(RDFNode latitude) {
        this.latitude = latitude;
    }

    public RDFNode getLongitude() {
        return longitude;
    }

    public void setLongitude(RDFNode longitude) {
        this.longitude = longitude;
    }

    public RDFNode getStationId() {
        return stationId;
    }

    public void setStationId(RDFNode stationId) {
        this.stationId = stationId;
    }
}
