/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import { createRoot } from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';

import TicketApp from './pages/TicketApp';

const queryClient = new QueryClient();
class TicketWebComponent extends HTMLElement {
  connectedCallback() {
    const root = createRoot(this);

    root.render(
      <QueryClientProvider client={queryClient}>
        <TicketApp queryClient={queryClient} />
      </QueryClientProvider>
    );
  }
}

const ELEMENT_ID = 'liferay-ticket-custom-element';

if (!customElements.get(ELEMENT_ID)) {
  customElements.define(ELEMENT_ID, TicketWebComponent);
}
