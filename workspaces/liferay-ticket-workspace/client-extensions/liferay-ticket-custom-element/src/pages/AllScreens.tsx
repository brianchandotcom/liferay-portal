/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ClayVerticalNav} from '@clayui/nav';
import {ReactNode} from 'react';

import {useHash} from '../services/useHash';
import {ScreenType} from '../types';
import TicketsDashboardApp from './TicketsDashboard';
import TicketApp from './TicketsOverview';

const routes = [
	{
		element: <TicketsDashboardApp screenType={ScreenType.INTEGRATED} />,
		href: '#dashboard',
		label: 'Dashboard',
	},
	{
		element: <TicketApp />,
		href: '#overview',
		label: 'Tickets Overview',
	},
];

const HREF_COMPONENT_MAP: {[key: string]: ReactNode} = {};

routes.forEach((item) => {
	HREF_COMPONENT_MAP[item.href] = item.element;
});

const defaultRoute: string = '#dashboard';

const AllScreens: React.FC = () => {
	const {hash} = useHash(defaultRoute);

	return (
		<ClayLayout.ContentRow padded>
			<ClayLayout.ContentCol>
				<div className="mb-2 text-uppercase">Site</div>
				<ClayVerticalNav active={hash} items={routes} large={false}>
					{(item: any) => (
						<ClayVerticalNav.Item href={item.href} key={item.href}>
							{item.label}
						</ClayVerticalNav.Item>
					)}
				</ClayVerticalNav>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol expand>
				{HREF_COMPONENT_MAP[hash]}
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
};

export default AllScreens;
