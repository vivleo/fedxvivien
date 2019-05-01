package demos;

import com.fluidops.fedx.Config;
import com.fluidops.fedx.FedX;
import com.fluidops.fedx.FedXFactory;
import com.fluidops.fedx.QueryManager;
import com.fluidops.fedx.endpoint.Endpoint;
import com.fluidops.fedx.endpoint.EndpointFactory;
import com.fluidops.fedx.monitoring.MonitoringUtil;

import local.LocalNetworkTest;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DemoVivien {

    public static void main(String[] args) throws Exception{
    	Config.initialize();
		List<Endpoint> endpoints = new ArrayList<Endpoint>();
		endpoints.add( EndpointFactory.loadSPARQLEndpoint("http://dbpedia", "http://dbpedia.org/sparql"));
		endpoints.add( EndpointFactory.loadSPARQLEndpoint("http://wikidata", "https://query.wikidata.org/sparql"));
		
		Repository repo = FedXFactory.initializeFederation(endpoints);
		
		String q = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
				+ "SELECT ?President ?Party WHERE {\n"
				+ "?President rdf:type dbpedia-owl:President .\n"
				+ "?President dbpedia-owl:party ?Party . }";
		
		String q2 = "PREFIX imdb: <http://data.linkedmdb.org/resource/movie/>\n" + 
				"PREFIX dbpedia: <http://dbpedia.org/ontology/>\n" + 
				"PREFIX dcterms: <http://purl.org/dc/terms/>\n" + 
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +  
				"SELECT * {\n" +   
				"        SERVICE <http://data.linkedmdb.org/sparql> { \n" + 
				"             ?actor1 imdb:producer_name \" Tom Hanks \". \n" + 
				"             ?movie imdb:producer ?actor1 ; \n" + 
				"                          dcterms:title ?movieTitle . }\n" + 
				"      SERVICE <http://dbpedia.org/sparql> { \n" + 
				"                ?actor owl:sameAs ?actor1 ; \n" + 
				"                           dbpedia:birthDate ?birth_date .\n" + 
				"                 } \n" + 
				"        }";
		
		TupleQuery query = QueryManager.prepareTupleQuery(q);
		
		try (TupleQueryResult res = query.evaluate()) {
			int i=0;
			while (res.hasNext()) {
				//System.out.println(format(res.next()));
				System.out.println(res.next());
				i++;
			}
			System.out.println("Nombre de résultat : "+i);
		}
		
		//MonitoringUtil.printMonitoringInformation();
		
		repo.shutDown();
		System.out.println("Done.");
		System.exit(0);

    }
    
    public static String format(BindingSet r ) {
    	String res = r.toString();
    	String [] tmp = res.split(";");

    	String pres = tmp[0].substring(tmp[0].indexOf("[")+1, tmp[0].indexOf("="));
    	String presValue = tmp[0].substring(tmp[0].lastIndexOf("/")+1, tmp[0].length()-1);

    	String party = tmp[1].substring(tmp[1].indexOf("[")+1, tmp[1].indexOf("="));
    	String partyValue = tmp[1].substring(tmp[1].lastIndexOf("/")+1, tmp[1].length()-1);

    	return pres+" => "+presValue+"          "+party+" => "+partyValue;
    }

    public static ArrayList<ArrayList<String>> getEndpointFromFile(String file){
		ArrayList<ArrayList<String>> endpoints = new ArrayList<>();
		FileReader endpointFile= null;
		String line = null;
		try {
			endpointFile = new FileReader(file);
			BufferedReader br = new BufferedReader(endpointFile);
			while((line = br.readLine()) != null){
				String [] tmp = line.split(" ");
				ArrayList<String> tmpArr = new ArrayList<>();
				tmpArr.add(tmp[0]); tmpArr.add(tmp[1]);
				endpoints.add(tmpArr);
			}
			endpointFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return endpoints;
	}

	public String readQueryFromFile(String file){
    	String request = null;
    	String line = null;
    	FileReader requestFile = null;
    	try{
    		requestFile = new FileReader(file);
    		BufferedReader br = new BufferedReader(requestFile);
    		while ((line = br.readLine()) != null){
    			request += line+"\n";
			}
    		requestFile.close();
		} catch (IOException e){
    		e.printStackTrace();
		}
		return request;
	}

	public void algorithm(String endpointsFile, String requestFile){
    	Config.initialize();
    	ArrayList<ArrayList<String>> endpointsArr = getEndpointFromFile(endpointsFile);
		List<Endpoint> endpointsColl = new ArrayList<Endpoint>();

		for (ArrayList<String> ar : endpointsArr){
    			endpointsColl.add(EndpointFactory.loadSPARQLEndpoint(ar.get(0), ar.get(1)));
		}
		Repository repo = FedXFactory.initializeFederation(endpointsColl);

		String query = readQueryFromFile(requestFile);

		TupleQuery queryTuple = QueryManager.prepareTupleQuery(query);

		try (TupleQueryResult res = queryTuple.evaluate()) {
			int i=0;
			while (res.hasNext()) {
				//System.out.println(format(res.next()));
				System.out.println(res.next());
				i++;
			}
			System.out.println("Nombre de résultat : "+i);
		}

		//MonitoringUtil.printMonitoringInformation();

		repo.shutDown();
		System.out.println("Done.");
		System.exit(0);

	}
}
