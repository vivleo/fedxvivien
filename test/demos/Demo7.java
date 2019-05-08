package demos;

import java.util.Collections;

import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;

import com.fluidops.fedx.Config;
import com.fluidops.fedx.FedXFactory;
import com.fluidops.fedx.FederationManager;
import com.fluidops.fedx.QueryManager;
import com.fluidops.fedx.endpoint.Endpoint;

public class Demo7 {

	@SuppressWarnings("Duplicates")
	public static void main(String[] args) throws Exception {
		
		// the fedx config implicitly defines a dataConfig
		String fedxConfig = "examples/fedxConfig-dataCfg.prop";
		Config.initialize(fedxConfig);
		Repository repo = FedXFactory.initializeFederation(Collections.<Endpoint>emptyList());
		
		QueryManager qm = FederationManager.getInstance().getQueryManager();
		qm.addPrefixDeclaration("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		qm.addPrefixDeclaration("dbpedia", "http://dbpedia.org/ontology/");
		
		String q = "SELECT ?President ?Party WHERE {\n"
			+ "?President rdf:type dbpedia:President .\n"
			+ "?President dbpedia:party ?Party . }";

		String q2 ="select distinct ?x where {?x a <http://xmlns.com/foaf/0.1/Person>} LIMIT 1000000";

		TupleQuery query = QueryManager.prepareTupleQuery(q2);
		try (TupleQueryResult res = query.evaluate()) {
		
			while (res.hasNext()) {
				System.out.println(res.next());
			}
		}
		
		repo.shutDown();
		System.out.println("Done.");
		System.exit(0);
		
	}
}
