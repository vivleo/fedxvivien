package demos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sun.net.httpserver.HttpsParameters;
import org.apache.http.impl.client.AbstractHttpClient;
import org.eclipse.rdf4j.http.client.SesameClientImpl;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.algebra.evaluation.QueryBindingSet;
import org.eclipse.rdf4j.repository.sail.SailRepository;

import com.fluidops.fedx.Config;
import com.fluidops.fedx.FedXFactory;
import com.fluidops.fedx.monitoring.MonitoringUtil;

public class Demo {


	public static void main(String[] args) throws Exception {

		File dataConfig = new File("test/local/test.ttl");
		Config.initialize();
		SailRepository repo = FedXFactory.initializeFederation(dataConfig);

		String q = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
				+ "SELECT ?President ?Party WHERE {\n"
				+ "?President rdf:type dbpedia-owl:President .\n"
				+ "?President dbpedia-owl:party ?Party . }";

		String q2 = "select distinct ?x where {?x a <http://xmlns.com/foaf/0.1/Organization> } ";

		String q3 = "select (COUNT(?x) AS ?nb) where {?x a <http://xmlns.com/foaf/0.1/Person>}";
		String q4 ="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n" +
				"SELECT ?President ?Party ?Articles ?TopicPage WHERE {\n" +
				"   ?President rdf:type <http://dbpedia.org/class/yago/PresidentsOfTheUnitedStates> .\n" +
				"   ?President dbpedia-owl:party ?Party .\n" +
				"   ?nytPres <http://www.w3.org/2002/07/owl#sameAs> ?President .\n" +
				"   ?nytPres <http://data.nytimes.com/elements/associated_article_count> ?Articles .\n" +
				"   ?nytPres <http://data.nytimes.com/elements/topicPage> ?TopicPage .\n" +
				"}\n";

		String q5 = "select distinct ?x \n" +
				"where {\n" +
				"  ?x a <http://xmlns.com/foaf/0.1/Person>\n" +
				"    FILTER regex(?x, 'leonard')\n" +
				"} ";

		String q6 ="SELECT  ?graph_uri" + "\n"+
				"WHERE" + "\n"+
				"{" +"\n"+
				"GRAPH ?graph_uri { ?s rdf:type owl:Class } . }";

		String q7 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX sioc: <http://rdfs.org/sioc/ns#>\n" +
				"SELECT distinct ?cls\n" +
				"WHERE { ?o rdf:type ?cls }\n" +
				"ORDER BY ?cls\n";

		String q8 = "PREFIX  dc:  <http://purl.org/dc/elements/1.1/>\n" +
				"SELECT  ?title\n" +
				"WHERE   { ?x dc:title ?title\n" +
				"          FILTER regex(?title, \"^SPARQL\") \n" +
				"        }";

		String q9 = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
				"select ?x \n" +
				"where {?x a <http://xmlns.com/foaf/0.1/Person> . \n" +
				"?x foaf:name ?name .\n" +
				"FILTER regex (?name, 'colomb', 'i')\n" +
				"} ";

		String q10 = "select distinct ?x where {?x a <http://xmlns.com/foaf/0.1/Organization>. \n" +
				"?x <http://xmlns.com/foaf/0.1/homepage> ?y.}";

		String q11 = "SELECT distinct ?x\n" +
				"WHERE\n" +
				"  { ?x a <http://xmlns.com/foaf/0.1/Person>.\n" +
				"   ?x <http://xmlns.com/foaf/0.1/name> ?y.\n" +
				"}";

		String q12 = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
				"SELECT distinct ?x\n" +
				" WHERE { ?x foaf:name  ?name\n" +
				"         FILTER regex(?name, \"^ali\", \"i\") }";
		/*TupleQuery query = repo.getConnection().prepareTupleQuery(QueryLanguage.SPARQL, q3);
		int nb = 0;
		ArrayList<BindingSet> tmp = new ArrayList<>();

		try (TupleQueryResult res = query.evaluate()) {
			while(res.hasNext()) {
				//System.out.println(res.next().getClass());
				//System.out.println(res.next());
				String next = res.next().toString();
				nb = Integer.valueOf(next.substring(next.indexOf("\"")+1,
							next.lastIndexOf("\"")-1));
				//System.out.println(res.next());
				//cmptr++;
				//System.out.println(cmptr);
			}
			System.out.println("Nombre de tuple (requête de comptage) : "+nb);
		}*/
		/*
			ArrayList<BindingSet> tmp = new ArrayList<>();
			int i=0;
			boolean test=true;
			while (test) {
				if (i==0) {
					q2 = "select distinct ?x where {?x a <http://xmlns.com/foaf/0.1/Person> } ";
				}
				else {
					q2 = "select distinct ?x where {?x a <http://xmlns.com/foaf/0.1/Person> }" + "\n" +
							" LIMIT 10000 "+"\n"+
							" OFFSET " + i ;
				}
				System.out.println("q2 : "+q2);
				TupleQuery query2 = repo.getConnection().prepareTupleQuery(QueryLanguage.SPARQL, q2);

				try (TupleQueryResult res2 = query2.evaluate()) {
						int x = 0;
						while (res2.hasNext()) {
							BindingSet temp = res2.next();
							if (temp.size()<10000){test=false;}
							//System.out.println(temp);
							tmp.add(temp);
							x++;
						}
					System.out.println("Nombre de résultat : "+x);

						//sample(tmp);
						//System.out.println("Nombre de résultat : "+res.getBindingNames().size());
						//System.out.println("Resultat : "+cmptr);
				}
				System.out.println(i);
				i+=10000;
				System.out.println("===========================");

			}*/

			//Simple query that return wrong number of tuple

			TupleQuery request = repo.getConnection().prepareTupleQuery(QueryLanguage.SPARQL, q12);
			ArrayList<BindingSet> tmp = new ArrayList<>();
			int i = 0;
			request.setMaxExecutionTime(180);

			try (TupleQueryResult res = request.evaluate()) {

				while (res.hasNext()){
					//tmp.add(res.next());
					System.out.println(res.next());
					i++;
				}
			}
			System.out.println("i : "+i);
		//System.out.println("Taille du résultat : "+tmp.size());
			//sample(tmp);

			//MonitoringUtil.printMonitoringInformation();

			repo.shutDown();
			System.out.println("Done.");
			System.exit(0);

		}


	public static void sample(ArrayList<BindingSet> b){
		int size = b.size()-1;
		int cmptrd=0;
		int cmptrb=0;
		for (int i = 0; i<=size; i++){
			String t = b.get(i).toString();
			if (t.contains("dbpedia")){
				cmptrd++;
			}
			if (t.contains("w3id")){
				cmptrb++;
			}
			//System.out.println(b.get(indice));
		}
		System.out.println("DBpedia : "+cmptrd);
		System.out.println("Scholarydata : "+cmptrb);
		int total = cmptrb+cmptrd;
		System.out.println("Total : "+total);
	}
}
