package com.example.SemWebProject.restController;

import com.example.SemWebProject.model.Proximity;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.rdfconnection.SparqlQueryConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.SemWebProject.JsonReader.readJsonFromUrl;

@RestController
@RequestMapping("availableRest")
public class AvailableRestController {
    @GetMapping(produces = "application/json")
    public int getAvailable(@RequestParam(name = "id") Integer id, @RequestParam(name = "city") String city) throws IOException, JSONException {

        if (city.equals("Lyon")) {
            JSONObject response = readJsonFromUrl("https://download.data.grandlyon.com/wfs/rdata?SERVICE=WFS&VERSION=1.1.0&outputformat=GEOJSON&request=GetFeature&typename=jcd_jcdecaux.jcdvelov&SRSNAME=urn:ogc:def:crs:EPSG::4171");

            for (int i = 0; i < response.getJSONArray("features").length(); i++) {

                if (response.getJSONArray("features").getJSONObject(i).getJSONObject("properties").get("number").toString().equals(id.toString())) {


                    return  Integer.parseInt(response.getJSONArray("features").getJSONObject(i).getJSONObject("properties").get("available_bikes").toString());
                }

            }
        } else if (city.equals("Saint Etienne")) {
            JSONObject response = readJsonFromUrl("https://saint-etienne-gbfs.klervi.net/gbfs/en/station_status.json");
            System.out.println(city + ": " + response);
            for (int i = 0; i < response.getJSONObject("data").getJSONArray("stations").length(); i++) {
                if (response.getJSONObject("data").getJSONArray("stations").getJSONObject(i).get("station_id").toString().equals(id.toString())) {
                    return  Integer.parseInt(response.getJSONObject("data").getJSONArray("stations").getJSONObject(i).get("num_bikes_available").toString());

                }
            }
            }
        return -1;
    }
}
