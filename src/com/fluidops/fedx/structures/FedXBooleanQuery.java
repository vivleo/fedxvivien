/*
 * Copyright (C) 2019 Veritas Technologies LLC.
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
package com.fluidops.fedx.structures;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.Dataset;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.parser.ParsedBooleanQuery;
import org.eclipse.rdf4j.repository.sail.SailBooleanQuery;

import com.fluidops.fedx.repository.FedXRepositoryConnection;
import com.fluidops.fedx.util.FedXUtil;

/**
 * Abstraction of a {@link SailBooleanQuery} which takes care for tracking the
 * {@link FedXRepositoryConnection#BINDING_ORIGINAL_MAX_EXECUTION_TIME}
 * during evaluation.
 * 
 * All methods are delegated to the actual {@link SailBooleanQuery}.
 * 
 * 
 * @author Andreas Schwarte
 *
 */
public class FedXBooleanQuery extends SailBooleanQuery {

	protected final SailBooleanQuery delegate;

	public FedXBooleanQuery(SailBooleanQuery delegate) {
		super(delegate.getParsedQuery(), null);
		this.delegate = delegate;
	}

	@Override
	public boolean evaluate() throws QueryEvaluationException {
		FedXUtil.applyQueryBindings(this);
		return delegate.evaluate();
	}

	/*
	 * DELEGATE TO ACTUAL SailBooleanQuery
	 */

	@SuppressWarnings("deprecation")
	@Override
	public void setMaxQueryTime(int maxQueryTime) {
		delegate.setMaxQueryTime(maxQueryTime);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMaxQueryTime() {
		return delegate.getMaxQueryTime();
	}

	@Override
	public void setBinding(String name, Value value) {
		delegate.setBinding(name, value);
	}

	@Override
	public ParsedBooleanQuery getParsedQuery() {
		return delegate.getParsedQuery();
	}

	@Override
	public void removeBinding(String name) {
		delegate.removeBinding(name);
	}

	@Override
	public void clearBindings() {
		delegate.clearBindings();
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public BindingSet getBindings() {
		return delegate.getBindings();
	}

	@Override
	public void setDataset(Dataset dataset) {
		delegate.setDataset(dataset);
	}

	@Override
	public Dataset getDataset() {
		return delegate.getDataset();
	}

	@Override
	public void setIncludeInferred(boolean includeInferred) {
		delegate.setIncludeInferred(includeInferred);
	}

	@Override
	public Dataset getActiveDataset() {
		return delegate.getActiveDataset();
	}

	@Override
	public boolean getIncludeInferred() {
		return delegate.getIncludeInferred();
	}

	@Override
	public void setMaxExecutionTime(int maxExecutionTime) {
		delegate.setMaxExecutionTime(maxExecutionTime);
	}

	@Override
	public int getMaxExecutionTime() {
		return delegate.getMaxExecutionTime();
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}
}
