/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Root, createRoot} from 'react-dom/client';

import Routes, {RouteType} from './Routes';

import './main.scss';

class WebComponent extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<Routes
					path={this.getAttribute('path') as RouteType}
					properties={{
						analyticsCloudURL:
							this.getAttribute('analyticsCloudURL') || '',
						cloudConsoleURL:
							this.getAttribute('cloudConsoleURL') || '',
						contactSupportURL:
							this.getAttribute('contactSupportURL') || '',
						eulaBaseURL: this.getAttribute('eulaBaseURL') || '',
						featureFlags: (this.getAttribute('featureFlags') ?? '')
							.split(',')
							.map((featureflag) => featureflag.trim()),
						marketoFormId: this.getAttribute('marketoFormId') || '',
						productId: this.getAttribute('productId') || '',
						trialAccountCheck:
							(this.getAttribute('trialAccountCheck') as any) ||
							'true',
						trialEulaURL: this.getAttribute('trialEulaURL') || '',
					}}
				/>
			);
		}
	}
}
const ELEMENT_ID = 'liferay-marketplace-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
