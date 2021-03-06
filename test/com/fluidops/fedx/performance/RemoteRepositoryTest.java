package com.fluidops.fedx.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.http.HTTPRepository;

import com.fluidops.fedx.util.FedXUtil;

public class RemoteRepositoryTest {

	private static final int MAX_INSTANCES = 4000;
	private static final int N_QUERIES = 4000;
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		ExecutorService executor = Executors.newFixedThreadPool(30);
		
		Repository repo = new HTTPRepository("http://10.212.10.29:8081/openrdf-sesame", "drugbank");
		
		repo.initialize();

		RepositoryConnection conn = repo.getConnection();
		
		System.out.println("Retrieving instances...");
		List<IRI> instances = retrieveInstances(conn,
				FedXUtil.iri("http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/drugs"));
		System.out.println("Retrieved " + instances.size() + " instances");
		
		System.out.println("Performing queries to retrieve outgoing statements for " + N_QUERIES + " instances.");
		List<Future<?>> tasks = new ArrayList<Future<?>>();
		long start = System.currentTimeMillis();
		int count=0;
		for (final IRI instance : instances)
		{
			if (++count>N_QUERIES)
				break;
			
			// b) multithreaded
			final RepositoryConnection _conn = conn;
			Future<?> task = executor.submit(new Runnable() {						
				@Override
				public void run() {
					try {
						runQuery(_conn, instance);
					} catch (Exception e) {
						System.err.println("Error while performing query evaluation for instance " + instance.stringValue() +": " + e.getMessage());
					}							
				}
			});
			tasks.add(task);					
		}
		
		// wait for all tasks being finished
		for (Future<?> task: tasks) {
			task.get();
		}
		System.out.println("Done evaluating queries. Duration " + (System.currentTimeMillis() - start) + "ms");
		
		repo.shutDown();
		executor.shutdown();
		System.out.println("Done.");
	}

	
	private static List<IRI> retrieveInstances(RepositoryConnection conn, IRI type) throws Exception
	{
		
		List<IRI> res = new ArrayList<IRI>();
		RepositoryResult<Statement> qres = null;
		try {
			qres = conn.getStatements(null, RDF.TYPE, type, false);
			while (qres.hasNext() && res.size()<MAX_INSTANCES) {
				Statement next = qres.next();
				res.add((IRI) next.getObject());
			}
		} finally {
			try {
				if (qres!=null)
					qres.close();
			} catch (Exception ignore) {}
		}
		return res;
	}
	
	private static int runQuery(RepositoryConnection conn, IRI instance) throws Exception
	{
		
		TupleQuery query = conn.prepareTupleQuery(QueryLanguage.SPARQL, "SELECT * WHERE { <" + instance.stringValue() + "> ?p ?o }");
		
		TupleQueryResult res = null;
		try {
			res = query.evaluate();
			int count = 0;
			while (res.hasNext()) {
				res.next();
				count++;
			}
			return count;
		} finally {
			if (res!=null)
				res.close();
		}
	}
}
