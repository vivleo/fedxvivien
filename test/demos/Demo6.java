package demos;

import java.util.Collections;

import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;

import com.fluidops.fedx.Config;
import com.fluidops.fedx.FedXFactory;
import com.fluidops.fedx.QueryManager;
import com.fluidops.fedx.endpoint.Endpoint;

public class Demo6 {

	
	public static void main(String[] args) throws Exception {
		
		// the fedx config implicitly defines a dataConfig
		String fedxConfig = "examples/fedxConfig.prop";
		Config.initialize(fedxConfig);
		Repository repo = FedXFactory.initializeFederation(Collections.<Endpoint>emptyList());

		String q = "SELECT ?President ?Party WHERE {\n"
			+ "?President rdf:type dbpedia:President .\n"
			+ "?President dbpedia:party ?Party . }";

		String q2 ="SELECT ?party ?page  WHERE {\n" +
				"   <http://dbpedia.org/resource/Barack_Obama> <http://dbpedia.org/ontology/party> ?party .\n" +
				"   ?x <http://data.nytimes.com/elements/topicPage> ?page .\n" +
				"   ?x <http://www.w3.org/2002/07/owl#sameAs> <http://dbpedia.org/resource/Barack_Obama> .\n" +
				"}";



		String q8 ="select distinct ?x where {?x a <http://xmlns.com/foaf/0.1/Person>} ";


		TupleQuery query = QueryManager.prepareTupleQuery(q8);
		try (TupleQueryResult res = query.evaluate()) {
			System.out.println("Tuple query res : ok");
			int i=0;
			while (res.hasNext()) {
				System.out.println(res.next());
				i++;
			}
			System.out.println(i);
		}
		
		repo.shutDown();
		System.out.println("Done.");
		System.exit(0);
		
	}
}
