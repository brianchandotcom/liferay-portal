/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {lazy} from 'react';
import {Navigate} from 'react-router-dom';

import {AppRoute} from '../../utils/routes';

const AccountDetails = lazy(() => import('./AccountDetails'));
const BillingUsage = lazy(() => import('./BillingUsage/BillingUsage'));
const Checkout = lazy(() => import('./BillingUsage/Checkout'));
const ConsumptionMetrics = lazy(
	() => import('./BillingUsage/ConsumptionMetrics')
);
const InvoiceHistory = lazy(() => import('./BillingUsage/InvoiceHistory'));
const SpendManagement = lazy(() => import('./BillingUsage/SpendManagement'));
const UsageMetrics = lazy(() => import('./BillingUsage/UsageMetrics'));
const OrderHistory = lazy(() => import('./Orders/OrderHistory'));
const Orders = lazy(() => import('./Orders/Orders'));
const AccountVerification = lazy(
	() => import('./Subscriptions/AccountVerification')
);
const ActivationKeys = lazy(() => import('./Subscriptions/ActivationKeys'));
const EulaManagement = lazy(() => import('./Subscriptions/EulaManagement'));
const PublisherOnboarding = lazy(
	() => import('./Subscriptions/PublisherOnboarding')
);
const Purchases = lazy(() => import('./Subscriptions/Purchases'));
const SubscriptionDetails = lazy(
	() => import('./Subscriptions/SubscriptionDetails')
);
const Subscriptions = lazy(() => import('./Subscriptions/Subscriptions'));
const TeamMembers = lazy(() => import('./TeamMembers'));

const subscriptionRoutes: AppRoute[] = [
	{element: <Subscriptions />, index: true},
	{
		children: [
			{element: <SubscriptionDetails />, index: true},
			{element: <ActivationKeys />, path: 'activation-keys'},
			{element: <EulaManagement />, path: 'eula-management'},
			{element: <Purchases />, path: 'purchases-licenses'},
			{element: <Navigate replace to="." />, path: '*'},
		],
		path: 'detail/:id',
	},
	{
		children: [
			{element: <PublisherOnboarding />, index: true},
			{
				element: <AccountVerification />,
				nav: {label: 'Account Verification'},
				path: 'account-verification',
			},
			{element: <Navigate replace to="." />, path: '*'},
		],
		nav: {label: 'Publisher Onboarding'},
		path: 'publisher-onboarding',
	},
	{element: <Navigate replace to="." />, path: '*'},
];

const orderRoutes: AppRoute[] = [
	{element: <Orders />, index: true},
	{element: <OrderHistory />, nav: {label: 'Order History'}, path: 'history'},
	{element: <Navigate replace to="." />, path: '*'},
];

const billingRoutes: AppRoute[] = [
	{element: <BillingUsage />, index: true},
	{
		children: [
			{element: <SpendManagement />, index: true},
			{
				element: <UsageMetrics />,
				nav: {label: 'Usage Metrics'},
				path: 'usage-metrics',
			},
			{
				element: <ConsumptionMetrics />,
				nav: {label: 'Consumption Metrics'},
				path: 'consumption-metrics',
			},
			{
				element: <InvoiceHistory />,
				nav: {label: 'Invoice History'},
				path: 'invoice-history',
			},
			{element: <Navigate replace to="." />, path: '*'},
		],
		nav: {label: 'Spend Management'},
		path: 'spend-management',
	},
	{element: <Checkout />, nav: {label: 'Checkout'}, path: 'checkout'},
	{element: <Navigate replace to="." />, path: '*'},
];

export const myAccountRoutes: AppRoute[] = [
	{element: <Navigate replace to="subscriptions" />, index: true},
	{
		children: subscriptionRoutes,
		nav: {label: 'Subscriptions'},
		path: 'subscriptions',
	},
	{children: orderRoutes, nav: {label: 'Orders'}, path: 'orders'},
	{
		children: billingRoutes,
		nav: {label: 'Billing & Usage'},
		path: 'billing-usage',
	},
	{
		element: <AccountDetails />,
		nav: {label: 'Account Details'},
		path: 'account-details',
	},
	{
		element: <TeamMembers />,
		nav: {label: 'Team Members'},
		path: 'team-members',
	},
	{element: <Navigate replace to="." />, path: '*'},
];
