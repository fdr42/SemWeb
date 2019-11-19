package com.example.SemWebProject.model;

import org.apache.jena.rdf.model.RDFNode;

public class LocationCity {
    private RDFNode cityName;

    public LocationCity(RDFNode cityName) {
        this.cityName = cityName;
    }

    public RDFNode getCityName() {
        return cityName;
    }

    public void setCityName(RDFNode cityName) {
        this.cityName = cityName;
    }
}
