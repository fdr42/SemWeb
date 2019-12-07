package com.example.SemWebProject.model;

import org.apache.jena.rdf.model.RDFNode;

public class Proximity {
    private String placeLabel;
    private String image;
    private Double longitude;
    private Double latitude;
    private Double dist;
    private  String instance_ofLabel;

    public String getPlaceLabel() {
        return placeLabel;
    }

    public String getImage() {
        return image;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude(){
        return latitude;
    }

    public Double getDist() {
        return dist;
    }

    public String getInstance_ofLabel() {
        return instance_ofLabel;
    }

    public Proximity(String placeLabel, String image, Double longitude, Double latitude, Double dist, String instance_ofLabel) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dist=dist;
        this.image=image;
        this.instance_ofLabel=instance_ofLabel;
        this.placeLabel=placeLabel;
    }
}
