/*
 * Copyright (C) 2018 Veritas Technologies LLC.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fluidops.fedx.evaluation.iterator;

import java.util.NoSuchElementException;

import org.eclipse.rdf4j.common.iteration.AbstractCloseableIteration;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.algebra.evaluation.QueryBindingSet;


/**
 * Converts graph results into a binding set iteration
 * 
 * @author Andreas Schwarte
 */
public class GraphToBindingSetConversionIteration
		extends AbstractCloseableIteration<BindingSet, QueryEvaluationException>
{

	protected final GraphQueryResult graph;

	
	public GraphToBindingSetConversionIteration(GraphQueryResult graph) {
		super();
		this.graph = graph;
	}
	
	
	@Override
	public boolean hasNext() throws QueryEvaluationException {
		return graph.hasNext();
	}

	@Override
	public BindingSet next() throws QueryEvaluationException {

		try {
			return convert(graph.next());
		} catch(NoSuchElementException e) {
			throw e;
	    } catch(IllegalStateException e) {
	    	throw e;
	    } 
	}

	@Override
	public void remove() throws QueryEvaluationException {

		try {
			graph.remove();
		} catch(UnsupportedOperationException e) {
			throw e;
		} catch(IllegalStateException e) {
			throw e;
		} 		
	}

	
	protected BindingSet convert(Statement st) {
		QueryBindingSet result = new QueryBindingSet();
		result.addBinding("subject", st.getSubject());
		result.addBinding("predicate", st.getPredicate());
		result.addBinding("object", st.getObject());
		if (st.getContext() != null) {
			result.addBinding("context", st.getContext());
		}

		return result;
	}
	
	
}
