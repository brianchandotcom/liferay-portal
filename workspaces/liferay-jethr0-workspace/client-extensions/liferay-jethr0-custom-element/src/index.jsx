/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import { createRoot } from 'react-dom/client';

import ProjectQueue from './common/ProjectQueue/ProjectQueue';

const App = () => {
	return (
		<div>
			<h2>Build Queue</h2>

			{Liferay.ThemeDisplay.isSignedIn() && <BuildQueue />}
		</div>
	);
};

class WebComponent extends HTMLElement {
	connectedCallback() {
		createRoot(this).render(<App />);
	}
}

const ELEMENT_ID = 'liferay-jethr0-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
