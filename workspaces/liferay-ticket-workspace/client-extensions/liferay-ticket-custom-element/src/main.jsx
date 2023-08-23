import React from 'react';
import {createRoot} from 'react-dom/client';
import TicketApp from './TicketApp.jsx';
import {QueryClient, QueryClientProvider} from 'react-query';

const queryClient = new QueryClient();
class WebComponent extends HTMLElement {
	connectedCallback() {
		const root = createRoot(this);
		root.render(
			<React.StrictMode>
				<QueryClientProvider client={queryClient}>
					<TicketApp queryClient={queryClient} route={this.getAttribute('route') || '/'} />
				</QueryClientProvider>
			</React.StrictMode>
		);
	}
}
const ELEMENT_ID = 'liferay-ticket-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
