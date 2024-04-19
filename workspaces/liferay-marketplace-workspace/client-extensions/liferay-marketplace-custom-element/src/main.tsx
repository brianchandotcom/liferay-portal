/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Root, createRoot} from 'react-dom/client';

import AppRoutes, {RouteType} from './Routes';

import './index.scss';
import Providers from './providers';

class WebComponent extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<Providers
					properties={{
						cloudBaseURL: this.getAttribute('cloudBaseURL') || '',
						contactSupportUrl:
							this.getAttribute('contactSupportUrl') || '',
						eulaBaseURL: this.getAttribute('eulaBaseURL') || '',
						featureFlags: (this.getAttribute('featureFlags') ?? '')
							.split(',')
							.map((featureflag) => featureflag.trim()),
						marketoFormId: this.getAttribute('marketoFormId') || '',
					}}
				>
					<AppRoutes path={this.getAttribute('path') as RouteType} />
				</Providers>
			);
		}
	}
}
const ELEMENT_ID = 'liferay-marketplace-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
