/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createRoot} from 'react-dom/client';
import {QueryClient, QueryClientProvider} from 'react-query';
import {HashRouter, Navigate, Outlet, Route, Routes} from 'react-router-dom';

import TicketApp from './pages/TicketApp';
import TicketsByStatusDashboard from './pages/TicketsByStatusDashboard';

const queryClient = new QueryClient();
class TicketWebComponent extends HTMLElement {
	connectedCallback() {
		const root = createRoot(this);
		root.render(
			<>
				<QueryClientProvider client={queryClient}>
					<div className="container">
						<section className="container current-tickets m-0 p-0 row">
							<div className="col-lg-2">
								<nav className="h-100 site-navigation">
									<h6 className="text-uppercase">Site</h6>
									<ul>
										<li>
											<a href="#dashboard">Dashboard</a>
										</li>
										<li>
											<a href="#ticketapp">
												Tickets App
											</a>
										</li>
									</ul>
								</nav>
							</div>
							<div className="col-lg-10">
								<HashRouter>
									<Routes>
										<Route
											element={
												<TicketsByStatusDashboard
													queryClient={queryClient}
												/>
											}
											path="dashboard"
										/>
										<Route
											element={
												<TicketApp
													queryClient={queryClient}
												/>
											}
											path="ticketapp"
										/>
										<Route
											element={
												<Navigate
													replace
													to="dashboard"
												/>
											}
											path=""
										/>
									</Routes>
								</HashRouter>
								<Outlet />
							</div>
						</section>
					</div>
				</QueryClientProvider>
			</>
		);
	}
}

const ELEMENT_ID = 'liferay-ticket-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, TicketWebComponent);
}
