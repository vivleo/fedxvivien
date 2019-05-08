package demos;

import java.util.Arrays;

import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.TupleQueryResultHandlerException;
import org.eclipse.rdf4j.repository.Repository;

import com.fluidops.fedx.Config;
import com.fluidops.fedx.FedXFactory;
import com.fluidops.fedx.QueryManager;

public class Demo4
{
	@SuppressWarnings("Duplicates")
	public static void main(String[] args) throws Exception
	{

		Config.initialize();
		Repository repo = FedXFactory.initializeSparqlFederation(Arrays.asList(
				"http://dbpedia.org/sparql",
				"http://data.bnf.fr/sparql"));

		String q = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
				+ "SELECT ?President ?Party WHERE {\n"
				+ "?President rdf:type dbpedia-owl:President .\n"
				+ "?President dbpedia-owl:party ?Party . }";

		String q2 ="select distinct ?x where {?x a <http://xmlns.com/foaf/0.1/Person>} LIMIT 100";

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