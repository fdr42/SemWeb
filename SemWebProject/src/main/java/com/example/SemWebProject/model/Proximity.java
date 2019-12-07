package com.example.SemWebProject.model;

import org.apache.jena.rdf.model.RDFNode;

public class Proximity {
    private RDFNode placeLabel;
    private RDFNode image;
    private  RDFNode coordinate_location;
    private  RDFNode dist;
    private  RDFNode instance_ofLabel;

    public RDFNode getPlaceLabel() {
        return placeLabel;
    }

    public RDFNode getImage() {
        return image;
    }

    public RDFNode getCoordinate_location() {
        return coordinate_location;
    }

    public RDFNode getDist() {
        return dist;
    }

    public RDFNode getInstance_ofLabel() {
        return instance_ofLabel;
    }

    public Proximity(RDFNode placeLabel, RDFNode image, RDFNode coordinate_location, RDFNode dist, RDFNode instance_ofLabel) {
        this.coordinate_location=coordinate_location;
        this.dist=dist;
        this.image=image;
        this.instance_ofLabel=instance_ofLabel;
        this.placeLabel=placeLabel;
    }
}
