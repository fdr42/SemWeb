package com.example.SemWebProject.controller;

import com.example.SemWebProject.model.LocationCity;
import com.example.SemWebProject.model.Station;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.rdfconnection.SparqlQueryConnection;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StationListController {

    @RequestMapping(value = "/")
    public String stationList(Model model) throws IOException, JSONException {

        SparqlQueryConnection conn = RDFConnectionFactory.connect("http://localhost:3030/semwebproject/");

        QueryExecution qe = conn.query("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX vocab: <http://localhost/>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX voc: <http://voc.odw.tw/>\n" +
                "PREFIX db: <http://dbpedia.org/>\n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "prefix owl: <http://www.w3.org/2002/07/owl#>\n" +
                "SELECT ?nomStation (str(?capacity) as ?cap) (str(?address) as ?add) (str(?latitude) as ?lat) (str(?longitude) as ?lon) ?id ?stp\n" +
                "WHERE {\n" +
                "  ?object dbo:locationCity ?label.\n" +
                "  ?label rdfs:label ?stp.\n" +
                "  ?object rdfs:label ?nomStation.\n" +
                "  ?object dbo:capacity ?capacity.\n" +
                "  ?object geo:lat ?latitude.\n" +
                "  ?object geo:lon ?longitude.\n" +
                "  ?object dbo:id ?id.\n" +
                "  ?object rdfs:comment ?address.\n" +
                "  }");
        // FILTER regex(?stp, \"" + city + "\", \"i\").
        ResultSet rs = qe.execSelect();
        List<Station> stationList = new ArrayList<Station>();
        List<String> villesUniques=new ArrayList<>();
        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            LocationCity locationCity = new LocationCity(qs.get("stp"));
if(!villesUniques.contains(locationCity.getCityName().asLiteral().getString())){
    villesUniques.add(locationCity.getCityName().asLiteral().getString());
}


            if (!qs.get("nomStation").asLiteral().getString().toLowerCase().contains("test")) {
                Station station = new Station(qs.get("id"),
                        locationCity,
                        qs.get("nomStation"),
                        qs.get("cap"),
                        qs.get("lat"),
                        qs.get("lon"),
                        qs.get("add"));


                stationList.add(station);
            }

        }
        model.addAttribute("stationList", stationList);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(stationList);
        model.addAttribute("stationListJson", jsonInString);
        model.addAttribute("villesUniques", villesUniques);
        return "stationList";

    }

}

