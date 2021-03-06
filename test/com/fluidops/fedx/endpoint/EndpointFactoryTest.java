package com.fluidops.fedx.endpoint;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fluidops.fedx.SPARQLBaseTest;
import com.fluidops.fedx.server.SPARQLEmbeddedServer;

public class EndpointFactoryTest extends SPARQLBaseTest {

	
	@Test
	public void testValidSparqlEndpoint() throws Exception {

		assumeSparqlEndpoint();

		prepareTest(Arrays.asList("/tests/basic/data01endpoint1.ttl", "/tests/basic/data01endpoint2.ttl"));

		String endpointUrl = ((SPARQLEmbeddedServer) server).getRepositoryUrl("endpoint1");
		EndpointBase e = (EndpointBase) EndpointFactory.loadSPARQLEndpoint(endpointUrl);
		
		Assertions.assertEquals("http://localhost_18080", e.getName());
		Assertions.assertEquals("sparql_localhost:18080_repositories_endpoint1", e.getId());
		Assertions.assertEquals("http://localhost:18080/repositories/endpoint1", e.getEndpoint());
		Assertions.assertEquals(EndpointType.SparqlEndpoint, e.getType());
	}
	
	@Test
	@Disabled // needs to be fixed, connection timeout needs to be set
	public void testNotReachableEndpoint() throws Exception {
		
		try {
			EndpointFactory.loadSPARQLEndpoint("http://invalid.org/not_sparql");
			Assertions.fail("Expected exception that endpoint is invalid");
		} catch (Exception expected) {
			
		}
		
	}

	@Test
	public void testDataConfig() throws Exception {

		fedxRule.setConfig("validateRepositoryConnections", "false");
		fedxRule.setConfig("baseDir", "target/tmp/fedxTest");

		File dataConfig = new File(EndpointFactoryTest.class.getResource("dataconfig.ttl").toURI());

		List<Endpoint> endpoints = EndpointFactory.loadFederationMembers(dataConfig);

		endpoints.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));

		Assertions.assertEquals(3, endpoints.size());

		Endpoint dbpediaSparql = endpoints.get(0);
		Assertions.assertEquals("http://dbpedia", dbpediaSparql.getName());
		Assertions.assertEquals("sparql_dbpedia", dbpediaSparql.getId());
		Assertions.assertEquals("http://dbpedia.org/sparql", dbpediaSparql.getEndpoint());

		Endpoint dbpediaLocal = endpoints.get(1);
		Assertions.assertEquals("http://dbpedia.local", dbpediaLocal.getName());
		Assertions.assertEquals("remote_dbpedia.local", dbpediaLocal.getId());
		Assertions.assertEquals("http://10.212.10.29:8088/rdf4j-server/dbpedia", dbpediaLocal.getEndpoint());

		Endpoint nativeStore = endpoints.get(2);
		Assertions.assertEquals("http://dbpedia.native", nativeStore.getName());
		Assertions.assertEquals("dbmodel", nativeStore.getId());
		Assertions.assertEquals("dbmodel", nativeStore.getEndpoint());
		Assertions.assertEquals(new File("target/tmp/fedxTest", "repositories/dbmodel"),
				((ManagedRepositoryEndpoint) nativeStore).repository.getDataDir());

	}


}
