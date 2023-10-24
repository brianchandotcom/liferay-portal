/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import React from 'react';
import ReactDOM from 'react-dom';

class CustomElement extends HTMLElement {
	connectedCallback() {
		ReactDOM.render(
			React.createElement(ClayBadge, {
				displayType: 'success',
				label: 'Success!',
			}),
			this
		);
	}

	disconnectedCallback() {
		ReactDOM.unmountComponentAtNode(this);
	}
}

const ELEMENT_NAME = 'liferay-sample-custom-element-5';

if (customElements.get(ELEMENT_NAME)) {
	// eslint-disable-next-line no-console
	console.log(
		`Skipping registration for <${ELEMENT_NAME}> (already registered)`
	);
}
else {
	customElements.define(ELEMENT_NAME, CustomElement);
}
