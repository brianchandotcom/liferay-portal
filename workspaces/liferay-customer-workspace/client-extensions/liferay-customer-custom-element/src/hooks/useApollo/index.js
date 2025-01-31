/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloClient, InMemoryCache} from '@apollo/client';
import {BatchHttpLink} from '@apollo/client/link/batch-http';
import {setContext} from '@apollo/client/link/context';
import {onError} from '@apollo/client/link/error';
import {RestLink} from 'apollo-link-rest';
import {SessionStorageWrapper, persistCache} from 'apollo3-cache-persist';
import {useEffect, useState} from 'react';
import {createNetworkStatusNotifier} from 'react-apollo-network-status';

import {Liferay} from '../../services/liferay';
import {liferayTypePolicies} from '../../services/liferay/graphql/typePolicies';
import {getOrRequestToken} from '../../services/liferay/security/auth/getOrRequestToken';
import {networkIndicator} from './networkIndicator';

const LiferayURI = `${Liferay.ThemeDisplay.getPortalURL()}/o`;
const {link, useApolloNetworkStatusReducer} = createNetworkStatusNotifier();
const {initialState, reducer} = networkIndicator;

const liferaBatchLink = new BatchHttpLink({
	uri: `${LiferayURI}/graphql`,
});

const liferayRestLink = new RestLink({
	uri: LiferayURI,
});

const liferayAuthLink = setContext((_, {headers, ...context}) => ({
	headers: {
		...headers,
		'x-csrf-token': Liferay.authToken,
	},
	...context,
}));

const raySourceErrorLink = onError(({forward, networkError, operation}) => {
	if (
		networkError.statusCode === 401 ||
		networkError.statusCode === 403 ||
		networkError.statusCode === 405
	) {
		return forward(operation);
	}
});

const getRaysourceAuthLink = () =>
	setContext(async (_, {headers, ...context}) => {
		const oAuthToken = await getOrRequestToken();

		return {
			headers: {
				...headers,
				'OAuth-Token': oAuthToken,
			},
			...context,
		};
	});

const getRaysourceRestLink = (uri) =>
	new RestLink({
		uri,
	});

export default function useApollo(provisioningServerAPI) {
	const [client, setClient] = useState();
	const networkStatus = useApolloNetworkStatusReducer(reducer, initialState);

	useEffect(() => {
		const init = async () => {
			const cache = new InMemoryCache({
				typePolicies: {
					...liferayTypePolicies,
				},
			});

			await persistCache({
				cache,
				storage: new SessionStorageWrapper(Liferay.Util.SessionStorage),
			});

			const apolloClient = new ApolloClient({
				cache,
				defaultOptions: {
					watchQuery: {
						fetchPolicy: 'network-only',
					},
				},
				link: link.split(
					(operation) =>
						operation.getContext().type === 'raysource-rest',
					raySourceErrorLink.concat(
						getRaysourceAuthLink().concat(
							getRaysourceRestLink(provisioningServerAPI)
						)
					),
					liferayAuthLink.split(
						(operation) =>
							operation.getContext().type === 'liferay-rest',
						liferayRestLink,
						liferaBatchLink
					)
				),
			});

			setClient(apolloClient);
		};

		init();
	}, [provisioningServerAPI]);

	return {client, networkStatus};
}
