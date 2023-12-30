/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayVerticalNav} from '@clayui/nav';
import {ReactNode, useState} from 'react';
import {HashRouter, Navigate, Route, Routes} from 'react-router-dom';

import TicketApp from './TicketsApp';
import TicketsDashboardApp from './TicketsDashboardApp';

const routes: {
	element: ReactNode;
	href: string;
	id: string;
	index: string;
	label: string;
	path: string;
}[] = [
	{
		element: <TicketsDashboardApp></TicketsDashboardApp>,
		href: '#/dashboard',
		id: '1',
		index: '1',
		label: 'Dashboard',
		path: 'dashboard',
	},
	{
		element: <TicketApp></TicketApp>,
		href: '#/ticketsapp',
		id: '2',
		index: '2',
		label: 'Tickets App',
		path: 'ticketsapp',
	},
];
const defaultRoute: string = 'dashboard';

const AllApps: React.FC = () => {
	const [activeItem, setActiveItem] = useState(() => {
		const currentRoute = location.hash;
		const initialActiveItem =
			routes.find((route) => route.href === currentRoute)?.index || '1';

		return initialActiveItem;
	});

	return (
		<ClayIconSpriteContext.Consumer>
			{(spritemap) => (
				<div className="autofit-padded autofit-row">
					<div className="autofit-col">
						<div className="h-100">
							<div className="mb-2 text-uppercase">Site</div>
							<ClayVerticalNav
								active={activeItem}
								items={routes}
								large={false}
								spritemap={spritemap}
							>
								{(item: any) => (
									<ClayVerticalNav.Item
										href={item.href}
										key={item.id}
										onClick={() => setActiveItem(item.id)}
									>
										{item.label}
									</ClayVerticalNav.Item>
								)}
							</ClayVerticalNav>
						</div>
					</div>
					<div className="autofit-col autofit-col-expand ml-2">
						<HashRouter>
							<Routes>
								{routes.map((route) => (
									<Route
										element={route.element}
										key={route.id}
										path={route.path}
									/>
								))}
								<Route
									element={
										<Navigate replace to={defaultRoute} />
									}
									path=""
								/>
							</Routes>
						</HashRouter>
					</div>
				</div>
			)}
		</ClayIconSpriteContext.Consumer>
	);
};

export default AllApps;
