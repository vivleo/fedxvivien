package demos;

import java.io.File;

import com.fluidops.fedx.Config;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;

import com.fluidops.fedx.FedXFactory;

public class Demo2 {

	
	public static void main(String[] args) throws Exception {
		
		if (System.getProperty("log4j.configuration")==null)
			System.setProperty("log4j.configuration", "etc/log4j.properties");
		Config.initialize();
		File dataConfig = new File("test/local/test.ttl");
		Repository repo = FedXFactory.initializeFederation(dataConfig);
		
		String q = "SELECT ?Drug ?IntDrug ?IntEffect WHERE { "
			+ "?Drug <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://dbpedia.org/ontology/Drug> . "
			+ "?y <http://www.w3.org/2002/07/owl#sameAs> ?Drug . "
		    + "?Int <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/interactionDrug1> ?y . "
		    + "?Int <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/interactionDrug2> ?IntDrug . "
		    + "?Int <http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/text> ?IntEffect . }";

		String q2 ="select distinct ?x where {?x a <http://xmlns.com/foaf/0.1/Person>} LIMIT 1000000";


		TupleQuery query = repo.getConnection().prepareTupleQuery(QueryLanguage.SPARQL, q2);
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
