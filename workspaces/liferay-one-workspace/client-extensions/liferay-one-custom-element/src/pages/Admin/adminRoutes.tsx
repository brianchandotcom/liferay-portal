/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {lazy} from 'react';
import {Navigate} from 'react-router-dom';

import {AppRoute} from '../../utils/routes';

const Apps = lazy(() => import('./Apps/Apps'));
const Environments = lazy(() => import('./Environments/Environments'));
const LicenseKeyUploads = lazy(
	() => import('./LicenseKeyUploads/LicenseKeyUploads')
);
const MessageQueue = lazy(() => import('./MessageQueue/MessageQueue'));
const MPFinanceOrders = lazy(() => import('./MPFinanceOrders/MPFinanceOrders'));
const MPSummary = lazy(() => import('./MPSummary/MPSummary'));
const MySsaSaasEnvironments = lazy(
	() => import('./MySsaSaasEnvironments/MySsaSaasEnvironments')
);
const Orders = lazy(() => import('./Orders/Orders'));
const Payments = lazy(() => import('./Payments/Payments'));
const PublisherRequests = lazy(
	() => import('./PublisherRequests/PublisherRequests')
);
const Publishers = lazy(() => import('./Publishers/Publishers'));
const Solutions = lazy(() => import('./Solutions/Solutions'));
const Trials = lazy(() => import('./Trials/Trials'));

export const adminRoutes: AppRoute[] = [
	{element: <Navigate replace to="mp-summary" />, index: true},

	{
		element: <MPSummary />,
		nav: {label: 'Summary', section: 'Marketplace'},
		path: 'mp-summary',
	},
	{
		element: <Orders />,
		nav: {label: 'Orders', section: 'Marketplace'},
		path: 'mp-orders',
	},
	{
		element: <Apps />,
		nav: {label: 'Apps', section: 'Marketplace'},
		path: 'mp-apps',
	},
	{
		element: <Solutions />,
		nav: {label: 'Solutions', section: 'Marketplace'},
		path: 'mp-solutions',
	},
	{
		element: <Publishers />,
		nav: {label: 'Publishers', section: 'Marketplace'},
		path: 'publishers',
	},
	{
		element: <PublisherRequests />,
		nav: {label: 'Publisher Requests', section: 'Marketplace'},
		path: 'publisher-requests',
	},
	{
		element: <Trials />,
		nav: {label: '7 Day Trials', section: 'Marketplace'},
		path: 'trials',
	},

	{
		element: <MySsaSaasEnvironments />,
		nav: {label: 'My SaaS Environments', section: 'SSA'},
		path: 'my-ssa-saas-environments',
	},
	{
		element: <Environments />,
		nav: {label: 'SaaS Environments', section: 'SSA'},
		path: 'ssa-saas-environments',
	},

	{
		element: <MPFinanceOrders />,
		nav: {label: 'Orders', section: 'Finance'},
		path: 'mp-finance-orders',
	},
	{
		element: <Payments />,
		nav: {label: 'Payments', section: 'Finance'},
		path: 'mp-payments',
	},
	{
		element: <MessageQueue />,
		nav: {label: 'Message Queue', section: 'Support'},
		path: 'message-queue',
	},
	{
		element: <LicenseKeyUploads />,
		nav: {label: 'License Key Uploads', section: 'Support'},
		path: 'license-key-uploads',
	},

	{element: <Navigate replace to="." />, path: '*'},
];
