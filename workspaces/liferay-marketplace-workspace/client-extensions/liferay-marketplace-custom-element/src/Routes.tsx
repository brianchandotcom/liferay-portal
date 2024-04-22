/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Suspense} from 'react';

import Loading from './components/Loading';

const lazyRoutes = {
	'administrator-dashboard': React.lazy(
		() =>
			import(
				'./pages/AdministratorDashboard/AdministratorDashboardRouter'
			)
	),
	'customer-gate': React.lazy(
		() => import('./pages/CustomerGatePage/CustomerGatePage')
	),
	'get-app': React.lazy(() => import('./pages/GetApp/GetAppRouter')),
	'next-steps': React.lazy(() => import('./pages/NextSteps')),
	'published-apps': React.lazy(
		() => import('./pages/PublisherDashboard/PublisherDashboardRouter')
	),
	'publisher-gate': React.lazy(
		() => import('./pages/PublisherGate/PublisheGateRouter')
	),
	'purchased-apps': React.lazy(
		() => import('./pages/CustomerDashboard/CustomerDashboardRouter')
	),
	'purchased-solutions': React.lazy(
		() => import('./pages/GetSolution/GetSolutionRouter')
	),
} as const;

export type RouteType = keyof typeof lazyRoutes;

type AppRoutesProps = {
	path: RouteType;
	properties: DefaultProperties;
};

export default function Routes({path, properties}: AppRoutesProps) {
	const Route = lazyRoutes[path] as React.FC<{properties: DefaultProperties}>;

	if (!Route) {
		return <h1>Page not found</h1>;
	}

	return (
		<Suspense
			fallback={<Loading displayType="secondary" shape="squares" />}
		>
			<Route properties={properties} />
		</Suspense>
	);
}
