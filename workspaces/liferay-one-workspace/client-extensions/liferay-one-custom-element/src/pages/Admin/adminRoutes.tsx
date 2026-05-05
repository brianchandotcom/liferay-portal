/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {lazy} from 'react';
import {Navigate} from 'react-router-dom';

import {AppRoute} from '../../utils/routes';

const Apps = lazy(() => import('./Apps/Apps'));
const Environments = lazy(() => import('./Environments/Environments'));
const Orders = lazy(() => import('./Orders/Orders'));
const Payments = lazy(() => import('./Payments/Payments'));
const Publishers = lazy(() => import('./Publishers/Publishers'));
const Solutions = lazy(() => import('./Solutions/Solutions'));
const Trials = lazy(() => import('./Trials/Trials'));

export const adminRoutes: AppRoute[] = [
	{element: <Navigate replace to="apps" />, index: true},
	{element: <Apps />, nav: {label: 'Apps'}, path: 'apps'},
	{
		element: <Environments />,
		nav: {label: 'Environments'},
		path: 'environments',
	},
	{element: <Orders />, nav: {label: 'Orders'}, path: 'orders'},
	{element: <Payments />, nav: {label: 'Payments'}, path: 'payments'},
	{element: <Publishers />, nav: {label: 'Publishers'}, path: 'publishers'},
	{element: <Solutions />, nav: {label: 'Solutions'}, path: 'solutions'},
	{element: <Trials />, nav: {label: 'Trials'}, path: 'trials'},
	{element: <Navigate replace to="." />, path: '*'},
];
