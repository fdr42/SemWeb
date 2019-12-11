package com.example.SemWebProject.controller;

import com.example.SemWebProject.model.LocationCity;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.rdfconnection.SparqlQueryConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @RequestMapping(value = "/djdkj", method = RequestMethod.GET)
    public String index(Model model) {
        SparqlQueryConnection conn = RDFConnectionFactory.connect("http://localhost:3030/semwebproject/");
        QueryExecution qe = conn.query("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX vocab: <http://localhost/>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX voc: <http://voc.odw.tw/>\n" +
                "PREFIX db: <http://dbpedia.org/>\n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "prefix owl: <http://www.w3.org/2002/07/owl#>\n" +
                "prefix schema: <http://schema.org/>\n" +
                "SELECT ?label\n" +
                "WHERE {\n" +
                "  ?object a schema:City.\n" +
                "  ?object rdfs:label ?label.\n" +
                "}");
        ResultSet rs = qe.execSelect();
        List<LocationCity> locationCityList = new ArrayList<LocationCity>();

        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            LocationCity locationCity = new LocationCity(qs.get("label"));
            locationCityList.add(locationCity);
        }
        model.addAttribute("locationCityList", locationCityList);
        return "index";
    }

}
