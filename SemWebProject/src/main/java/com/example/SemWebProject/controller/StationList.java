package com.example.SemWebProject.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.SemWebProject.model.LocationCity;
import com.example.SemWebProject.model.Station;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.rdfconnection.SparqlQueryConnection;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class StationList {

	@RequestMapping(value = "/StationList", method = RequestMethod.GET)
	public String stationList(Model model) {

		SparqlQueryConnection conn = RDFConnectionFactory.connect("http://localhost:3030/semwebproject/");
		QueryExecution qe = conn.query("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
		"PREFIX vocab: <http://localhost/>\n" +
				"PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
				"PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
				"PREFIX voc: <http://voc.odw.tw/>\n" +
				"PREFIX db: <http://dbpedia.org/>\n" +
				"prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
				"prefix owl: <http://www.w3.org/2002/07/owl#>\n" +
				"SELECT ?nomStation ?capacity ?latitude ?longitude ?id\n" +
				"WHERE {\n" +
				"  ?object dbo:locationCity ?label.\n" +
				"  ?label rdfs:label ?stp.\n" +
				"  ?object rdfs:label ?nomStation.\n" +
				"  ?object rdfs:capacity ?capacity.\n" +
				"  ?object geo:lat ?latitude.\n" +
				"  ?object geo:lon ?longitude.\n" +
				"  ?object dbo:id ?id.\n" +
				"  FILTER regex(?stp, \"Ly\", \"i\").}");
		ResultSet rs = qe.execSelect();

		LocationCity locationCity = new LocationCity("Lyon");
		List<Station> stationList = new ArrayList<Station>();
		int i = 0;
		while(rs.hasNext()) {
			QuerySolution qs = rs.next();
			System.out.println(qs.get("capacity"));
			Station station = new Station(qs.get("id"),
					locationCity,
					qs.get("nomStation"),
					qs.get("capacity"),
					qs.get("longitude"),
					qs.get("latitude"));

			stationList.add(station);
			i++;
		}

		model.addAttribute("stationList", stationList);
		model.addAttribute("locationCity", locationCity);
		return "stationList";

	}

}

