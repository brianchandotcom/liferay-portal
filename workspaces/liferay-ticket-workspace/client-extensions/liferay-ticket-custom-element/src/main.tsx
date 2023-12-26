/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayVerticalNav} from '@clayui/nav';
import {useState} from 'react';
import {createRoot} from 'react-dom/client';
import {QueryClientProvider} from 'react-query';
import {HashRouter, Navigate, Route, Routes} from 'react-router-dom';

import {CONFIGS, TicketsAppContext} from './context';
import TicketApp from './pages/TicketApp';
import TicketsByStatusDashboard from './pages/TicketsByStatusDashboard';

const routes: any[] = [
	{
		href: '#/dashboard',
		id: '1',
		index: '1',
		label: 'Dashboard',
	},
	{
		href: '#/ticketapp',
		id: '2',
		index: '2',
		label: 'Tickets App',
	},
];

const Main: React.FC = () => {
	const currentRoute = '#' + location.href.split('#')[1];
	const initialActiveItem =
		routes.find((route) => route.href === currentRoute)?.index || '1';

	const [activeItem, setActiveItem] = useState(initialActiveItem);

	return (
		<TicketsAppContext.Provider value={CONFIGS}>
			<QueryClientProvider client={CONFIGS.queryClient}>
				<div className="autofit-padded autofit-row">
					<div
						className={
							'autofit-col ' +
							(CONFIGS.defaultPage ? 'd-none' : '')
						}
					>
						<div className="h-100">
							<div className="mb-2 text-uppercase">Site</div>
							<ClayVerticalNav
								active={activeItem}
								items={routes}
								large={false}
								spritemap={CONFIGS.spriteMap}
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
								<Route
									element={<TicketsByStatusDashboard />}
									path="dashboard"
								/>
								<Route
									element={<TicketApp />}
									path="ticketapp"
								/>
								<Route
									element={
										<Navigate
											replace
											to={
												CONFIGS.defaultPage
													? CONFIGS.defaultPage
													: 'dashboard'
											}
										/>
									}
									path=""
								/>
							</Routes>
						</HashRouter>
					</div>
				</div>
			</QueryClientProvider>
		</TicketsAppContext.Provider>
	);
};

class MainHTMLElement extends HTMLElement {
	connectedCallback() {
		const root = createRoot(this);
		const defaultPage = this.getAttribute('defaultpage');
		if (defaultPage) {
			CONFIGS.defaultPage = defaultPage;
		}
		root.render(<Main />);
	}
}

const ELEMENT_ID = 'liferay-ticket-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, MainHTMLElement);
}
