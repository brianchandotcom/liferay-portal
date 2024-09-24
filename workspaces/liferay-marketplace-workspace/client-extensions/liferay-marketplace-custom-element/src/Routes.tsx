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
	'get-app': React.lazy(() => import('./pages/GetApp/GetAppRouter')),
	'license-agreement': React.lazy(
		() => import('./pages/LicenseAgreementPage')
	),
	'next-steps': React.lazy(() => import('./pages/NextSteps')),
	'product-purchase': React.lazy(
		() => import('./pages/ProductPurchase/ProductPurchaseRouter')
	),
	'published-apps': React.lazy(
		() => import('./pages/PublisherDashboard/PublisherDashboardRouter')
	),
	'publisher-gate': React.lazy(
		() => import('./pages/PublisherGate/PublisherGateRouter')
	),
	'purchased-apps': React.lazy(
		() => import('./pages/CustomerDashboard/CustomerDashboardRouter')
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
