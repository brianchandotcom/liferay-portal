/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';
import * as ReactDOM from 'react-dom';
import {QueryClient, QueryClientProvider} from 'react-query';

import AllScreens from './pages/AllScreens';
import TicketsDashboard from './pages/TicketsDashboard';
import TicketsOverview from './pages/TicketsOverview';
import {Liferay} from './services/liferay';
import {ScreenType} from './types';

export type LiferayTicketWorkspaceComponentsType = {
	[key: string]: JSX.Element;
};

const LIFERAY_TICKET_WORKSPACE_COMPONENTS: LiferayTicketWorkspaceComponentsType =
	{
		dashboard: <TicketsDashboard screenType={ScreenType.STANDALONE} />,
		overview: <TicketsOverview />,
	};

const DirectToCustomer: React.FC<{defaultScreen: string}> = ({defaultScreen}) =>
	LIFERAY_TICKET_WORKSPACE_COMPONENTS[defaultScreen] ?? <AllScreens />;

const queryClient = new QueryClient();

const Main: React.FC<{defaultScreen: string}> = ({defaultScreen}) => (
	<QueryClientProvider client={queryClient}>
		<ClayIconSpriteContext.Provider value={Liferay.Icons.spritemap}>
			<DirectToCustomer defaultScreen={defaultScreen} />
		</ClayIconSpriteContext.Provider>
	</QueryClientProvider>
);

class WebComponent extends HTMLElement {
	connectedCallback() {
		const defaultScreen = this.getAttribute('defaultScreen') || '';
		ReactDOM.render(React.createElement(Main, {defaultScreen}), this);
	}
}

const ELEMENT_ID = 'liferay-ticket-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
