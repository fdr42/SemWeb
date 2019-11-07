package com.example.SemWebProject.controller;

import java.util.ArrayList;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.rdfconnection.SparqlQueryConnection;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
		
		SparqlQueryConnection conn = RDFConnectionFactory.connect("http://localhost:3030/semwebproject/");
		QueryExecution qe = conn.query("SELECT ?s ?p ?o WHERE {?s ?p ?o}");
		ResultSet rs = qe.execSelect();
		String result = "";
		while(rs.hasNext()) {
			QuerySolution qs = rs.next();
			RDFNode s = qs.get("s");
			RDFNode p = qs.get("p");
			RDFNode o = qs.get("o");
			
			result+= "<p> Subject : " + s + " Predicate : " + p + " Object : " + o + "</p>";
					
		}
		
		return result;
		
    }

}
