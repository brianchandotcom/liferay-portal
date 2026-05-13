/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
import {RouteObject} from 'react-router-dom';

import {NavItem} from '../components/SideNav';

type IndexRoute = {
	children?: AppRoute[];
	element?: ReactNode;
	index: true;
	nav?: never;
	path?: never;
};

type PathRoute = {
	children?: AppRoute[];
	element?: ReactNode;
	index?: never;
	nav?: {label: string; section?: string};
	path: string;
};

export type AppRoute = IndexRoute | PathRoute;

export function toRouteObjects(routes: AppRoute[]): RouteObject[] {
	return routes.map((route) => {
		if (route.index) {
			return {element: route.element, index: true};
		}

		return {
			children: route.children
				? toRouteObjects(route.children)
				: undefined,
			element: route.element,
			path: route.path,
		};
	});
}

export function buildNavItems(routes: AppRoute[], prefix = ''): NavItem[] {
	const items: NavItem[] = [];

	for (const route of routes) {
		if (!route.path || !route.nav || route.path.includes(':')) {
			continue;
		}

		const fullPath = prefix ? `${prefix}/${route.path}` : `/${route.path}`;
		const children = route.children
			? buildNavItems(route.children, fullPath)
			: undefined;

		items.push({
			children: children?.length ? children : undefined,
			label: route.nav.label,
			path: fullPath,
			section: route.nav.section,
		});
	}

	return items;
}
