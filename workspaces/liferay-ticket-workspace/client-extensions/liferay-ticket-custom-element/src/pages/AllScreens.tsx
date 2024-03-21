/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ClayVerticalNav} from '@clayui/nav';
import React from 'react';

import {useHash} from '../hooks/useHash';
import {ScreenType} from '../types';
import TicketsDashboard from './TicketsDashboard';
import TicketsOverview from './TicketsOverview';

const ROUTES = [
	{
		element: <TicketsDashboard screenType={ScreenType.INTEGRATED} />,
		href: '#dashboard',
		label: 'Dashboard',
	},
	{
		element: <TicketsOverview />,
		href: '#overview',
		label: 'Tickets Overview',
	},
];

const HREF_COMPONENT_MAP: {[key: string]: React.ReactElement} = {};

ROUTES.forEach((route) => {
	HREF_COMPONENT_MAP[route.href] = route.element;
});

const AllScreens = () => {
	const {hash} = useHash('#dashboard');

	return (
		<ClayLayout.ContentRow padded>
			<ClayLayout.ContentCol>
				<div className="mb-2 text-uppercase">Site</div>

				<ClayVerticalNav active={hash} items={ROUTES} large={false}>
					{(item: any) => (
						<ClayVerticalNav.Item href={item.href} key={item.href}>
							{item.label}
						</ClayVerticalNav.Item>
					)}
				</ClayVerticalNav>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol expand>
				{HREF_COMPONENT_MAP[hash] ? (
					HREF_COMPONENT_MAP[hash]
				) : (
					<TicketsDashboard screenType={ScreenType.INTEGRATED} />
				)}
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
};

export default AllScreens;
