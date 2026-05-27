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
const ManageSsaSaasUsers = lazy(
	() => import('./ManageSsaSaasUsers/ManageSsaSaasUsers')
);
const MessageQueue = lazy(() => import('./MessageQueue/MessageQueue'));
const MPFinanceOrders = lazy(() => import('./MPFinanceOrders/MPFinanceOrders'));
const MPSummary = lazy(() => import('./MPSummary/MPSummary'));
const MySsaSaasDemo = lazy(() => import('./MySsaSaasDemo/MySsaSaasDemo'));
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
		nav: {icon: 'polls', label: 'Marketplace Summary'},
		path: 'mp-summary',
	},
	{
		element: <Orders />,
		nav: {icon: 'order-form', label: 'Marketplace Orders'},
		path: 'mp-orders',
	},
	{
		element: <Apps />,
		nav: {icon: 'grid', label: 'Marketplace Apps'},
		path: 'mp-apps',
	},
	{
		element: <Solutions />,
		nav: {icon: 'union', label: 'Marketplace Solutions'},
		path: 'mp-solutions',
	},
	{
		element: <MPFinanceOrders />,
		nav: {icon: 'order-form', label: 'Marketplace Finance Orders'},
		path: 'mp-finance-orders',
	},
	{
		element: <Payments />,
		nav: {icon: 'order-form', label: 'Marketplace Payments'},
		path: 'mp-payments',
	},
	{
		element: <Publishers />,
		nav: {icon: 'squares-clock', label: 'Publishers'},
		path: 'publishers',
	},
	{
		element: <PublisherRequests />,
		nav: {icon: 'order-form', label: 'Publisher Requests'},
		path: 'publisher-requests',
	},
	{
		element: <Trials />,
		nav: {icon: 'grid', label: '7 Days Trials'},
		path: 'trials',
	},
	{
		element: <MySsaSaasDemo />,
		nav: {icon: 'union', label: 'My SSA SaaS Demo'},
		path: 'my-ssa-saas-demo',
	},
	{
		element: <Environments />,
		nav: {icon: 'squares-clock', label: 'SSA SaaS Environments'},
		path: 'ssa-saas-environments',
	},
	{
		element: <ManageSsaSaasUsers />,
		nav: {icon: 'users', label: 'Manage SSA SaaS Users'},
		path: 'manage-ssa-saas-users',
	},
	{
		element: <MessageQueue />,
		nav: {icon: 'message-boards', label: 'Message Queue'},
		path: 'message-queue',
	},
	{
		element: <LicenseKeyUploads />,
		nav: {icon: 'password-policies', label: 'License Key Uploads'},
		path: 'license-key-uploads',
	},

	{element: <Navigate replace to="." />, path: '*'},
];
