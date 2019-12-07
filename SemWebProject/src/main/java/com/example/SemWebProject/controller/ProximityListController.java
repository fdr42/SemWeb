package com.example.SemWebProject.controller;

import com.example.SemWebProject.model.LocationCity;
import com.example.SemWebProject.model.Proximity;
import com.example.SemWebProject.model.Station;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.rdfconnection.SparqlQueryConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ProximityListController {

	@RequestMapping(value = "/getProximity",
			params = {"lat", "lon"},
			method = GET)
	public String stationList(Model model,
							  @RequestParam("lat") double lat,
							  @RequestParam("lon") double lon) {
		List<Proximity> proximityList = new ArrayList<Proximity>();
		SparqlQueryConnection connWiki = RDFConnectionFactory.connect("https://query.wikidata.org/bigdata/namespace/wdq/sparql");

		QueryExecution qu = connWiki.query(" PREFIX bd: <http://www.bigdata.com/rdf#> " +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
				"				 PREFIX wdt: <http://www.wikidata.org/prop/direct/> " +
				"PREFIX wd: <http://www.wikidata.org/entity/>" +
				"PREFIX geo: <http://www.opengis.net/ont/geosparql#>" +
				"				 PREFIX wikibase: <http://wikiba.se/ontology#> "
				+ "SELECT ?place ?placeLabel ?image ?coordinate_location ?dist ?instance_of ?instance_ofLabel WHERE {" +
				"  SERVICE wikibase:around {" +
				"    ?place wdt:P625 ?coordinate_location." +
				"    bd:serviceParam wikibase:center \"Point(" + lat + " " + lon + ")\"^^geo:wktLiteral;" +
				"    wikibase:radius \"0.2\";" +
				"     wikibase:distance ?dist." +
				"  }" +
				"  SERVICE wikibase:label { bd:serviceParam wikibase:language \"[AUTO_LANGUAGE],fr\". }" +
				"  OPTIONAL { ?place wdt:P18 ?image. }" +
				"  OPTIONAL { ?place wdt:P31 ?instance_of. }" +
				" FILTER (   ?instance_of != wd:Q79007 \n" +
				"           && ?instance_of != wd:Q61663696  \n" +
				"           && ?instance_of != wd:Q12731  \n" +
				"           && ?instance_of != wd:Q13634881 \n" +
				"           && ?instance_of != wd:Q174782\n" +
				"           && ?instance_of != wd:Q22746\n" +
				"           && ?instance_of != wd:Q194203) \n" +


				"}");
		ResultSet rs2 = qu.execSelect();
		while (rs2.hasNext()) {
			QuerySolution qs2 = rs2.next();
			Double longitude = Double.parseDouble(qs2.get("coordinate_location").toString().replace("Point", "").
					replace("(", "").
					replace(")", "").
					replace("^^http://www.opengis.net/ont/geosparql#wktLiteral", "").split(" ")[0]);
			Double latitude = Double.parseDouble(qs2.get("coordinate_location").toString().replace("Point", "").
					replace("(", "").
					replace(")", "").
					replace("^^http://www.opengis.net/ont/geosparql#wktLiteral", "").split(" ")[1]);

			Double dist = Double.parseDouble(qs2.get("dist").toString().replace("^^http://www.w3.org/2001/XMLSchema#double", ""));

			Proximity item = new Proximity(
					qs2.get("placeLabel").toString(),
					qs2.get("image").toString(),
					longitude,
					latitude,
					dist,
					qs2.get("instance_ofLabel").toString());


			proximityList.add(item);
		}
		return "stationList";

	}

}