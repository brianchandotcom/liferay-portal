/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import React, {Suspense, lazy, useState} from 'react';
import {Switch} from 'react-router';
import BundleRouter from '~/route-middleware/BundleRouter';
import Card from '~/shared/components/Card';
import Loading from '~/shared/components/Loading';
import RouteNotFound from '~/shared/components/RouteNotFound';
import {Routes, getMatchedRoute, toRoute} from '~/shared/util/router';

const AttributeList = lazy(
	() => import(/* webpackChunkName: "AttributeList" */ './AttributeList')
);

const GlobalAttributeList = lazy(
	() =>
		import(

			/* webpackChunkName: "GlobalAttributeList" */ './GlobalAttributeList'
		)
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('global-attributes'),
		route: Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_GLOBAL,
	},
	{
		exact: true,
		label: Liferay.Language.get('attributes'),
		route: Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_LOCAL,
	},
];

interface ITabsCardProps {
	groupId: string;
}

const TabsCard: React.FC<ITabsCardProps> = ({groupId}) => {
	const matchedRoute = getMatchedRoute(NAV_ITEMS);

	const initialItem =
		NAV_ITEMS.find((item) => item.route === matchedRoute) ?? NAV_ITEMS[0];

	const [activeLabel, setActiveLabel] = useState(initialItem.label);

	return (
		<Card key="cardContainer" pageDisplay>
			<ClayNavigationBar
				className="mx-4 my-3 page-subnav"
				triggerLabel={activeLabel}
			>
				{NAV_ITEMS.map(({label, route}) => (
					<ClayNavigationBar.Item
						active={matchedRoute === route}
						key={route}
					>
						<ClayLink
							href={toRoute(route, {groupId})}
							onClick={() => setActiveLabel(label)}
						>
							{label}
						</ClayLink>
					</ClayNavigationBar.Item>
				))}
			</ClayNavigationBar>

			<Suspense fallback={<Loading />}>
				<Switch>
					<BundleRouter
						data={AttributeList}
						exact
						path={
							Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_LOCAL
						}
					/>

					<BundleRouter
						data={GlobalAttributeList}
						exact
						path={
							Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_GLOBAL
						}
					/>

					<RouteNotFound />
				</Switch>
			</Suspense>
		</Card>
	);
};
export default TabsCard;
