package demos;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;

import com.fluidops.fedx.Config;
import com.fluidops.fedx.FedXFactory;
import com.fluidops.fedx.QueryManager;
import com.fluidops.fedx.endpoint.Endpoint;
import com.fluidops.fedx.endpoint.EndpointFactory;

public class Demo3 {

	
	public static void main(String[] args) throws Exception {

		if (System.getProperty("log4j.configuration")==null)
			System.setProperty("log4j.configuration", "etc/log4j.properties");

		Config.initialize();
		Config.getConfig().set("enableMonitoring", "true");
		Config.getConfig().set("monitoring.logQueryPlan", "true");
		Config.getConfig().set("debugQueryPlan", "true");
		List<Endpoint> endpoints = new ArrayList<Endpoint>();
		endpoints.add( EndpointFactory.loadSPARQLEndpoint("http://data.bnf", "https://data.bnf.fr/sparql"));
		endpoints.add( EndpointFactory.loadSPARQLEndpoint("http://DBpedia", "http://dbpedia.org/sparql"));

		Repository repo = FedXFactory.initializeFederation(endpoints);
		
		String q = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
			+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
			+ "SELECT ?President ?Party WHERE {\n"
			+ "?President rdf:type dbpedia-owl:President .\n"
			+ "?President dbpedia-owl:party ?Party . }";

		String q2 ="select distinct ?x where {?x a <http://xmlns.com/foaf/0.1/Person>} ";

		String q3 ="SELECT ?party ?page  WHERE {\n" +
				"   <http://dbpedia.org/resource/Barack_Obama> <http://dbpedia.org/ontology/party> ?party .\n" +
				"   ?x <http://data.nytimes.com/elements/topicPage> ?page .\n" +
				"   ?x <http://www.w3.org/2002/07/owl#sameAs> <http://dbpedia.org/resource/Barack_Obama> .\n" +
				"}";

		//TupleQuery query = QueryManager.prepareTupleQuery(q2);
		TupleQuery query = repo.getConnection().prepareTupleQuery(QueryLanguage.SPARQL, q2);

		try (TupleQueryResult res = query.evaluate()) {
			int cmptr =0;
			while (res.hasNext()) {
				//System.out.println(res.next());
				cmptr++;
			}
			System.out.println(cmptr);
		}
		
		repo.shutDown();
		System.out.println("Done.");
		System.exit(0);
		
	}
}