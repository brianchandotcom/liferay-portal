/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.graphql.client;

import com.liferay.osb.faro.rest.internal.graphql.model.GraphQLError;

import java.util.List;

/**
 * @author Leslie Wong
 */
public class GraphQLException extends Exception {

	public GraphQLException(
		List<GraphQLError> graphQLErrors, String operationName) {

		super(_buildMessage(graphQLErrors, operationName));

		_graphQLErrors = graphQLErrors;
		_operationName = operationName;
	}

	public List<GraphQLError> getGraphQLErrors() {
		return _graphQLErrors;
	}

	public String getOperationName() {
		return _operationName;
	}

	private static String _buildMessage(
		List<GraphQLError> graphQLErrors, String operationName) {

		StringBuilder sb = new StringBuilder();

		sb.append("GraphQL operation '");
		sb.append(operationName);
		sb.append("' returned errors: ");
		sb.append(graphQLErrors);

		return sb.toString();
	}

	private final List<GraphQLError> _graphQLErrors;
	private final String _operationName;

}