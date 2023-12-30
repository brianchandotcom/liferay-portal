/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import {ReactNode, useState} from 'react';
import {createRoot} from 'react-dom/client';
import {QueryClientProvider} from 'react-query';

import {DefaultAppContext, QueryClientContext} from './context';
import AllApps from './pages/AllApps';
import TicketApp from './pages/TicketsApp';
import TicketsDashboardApp from './pages/TicketsDashboardApp';
import {Liferay} from './services/liferay';

const Main: React.FC<{defaultAppProp: string}> = ({defaultAppProp}) => {
	const [defaultApp] = useState(defaultAppProp);

	let app: ReactNode;

	switch (defaultApp) {
		case 'dashboard':
			app = <TicketsDashboardApp></TicketsDashboardApp>;
			break;
		case 'ticketsapp':
			app = <TicketApp></TicketApp>;
			break;
		default:
			app = <AllApps></AllApps>;
	}

	return (
		<QueryClientContext.Consumer>
			{(queryClient) => (
				<QueryClientProvider client={queryClient}>
					<DefaultAppContext.Provider value={defaultApp}>
						<ClayIconSpriteContext.Provider
							value={Liferay.Icons.spritemap}
						>
							{app}
						</ClayIconSpriteContext.Provider>
					</DefaultAppContext.Provider>
				</QueryClientProvider>
			)}
		</QueryClientContext.Consumer>
	);
};

class MainHTMLElement extends HTMLElement {
	connectedCallback() {
		const root = createRoot(this);
		const defaultApp = this.getAttribute('defaultapp') || '';

		root.render(<Main defaultAppProp={defaultApp} />);
	}
}

const ELEMENT_ID = 'liferay-ticket-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, MainHTMLElement);
}
