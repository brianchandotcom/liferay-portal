/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Root, createRoot} from 'react-dom/client';
import {SWRConfig} from 'swr';

import TestrayRouter from './TestrayRouter';
import ClayIconProvider from './context/ClayIconProvider';
import TestrayContextProvider from './context/TestrayContext';

import './styles/index.scss';
import ApplicationContextProvider from './context/ApplicationPropertiesContext';
import SWRCacheProvider from './services/SWRCacheProvider';
import fetcher from './services/fetcher';

class Testray extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		const properties = {
			jiraBaseURL: this.getAttribute('jiraBaseURL') || '',
			pusherKey: this.getAttribute('pusherKey') ?? '',
			pusherRegion: this.getAttribute('pusherRegion') ?? '',
		};

		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<SWRConfig
					value={{
						fetcher,
						provider: SWRCacheProvider,
						revalidateOnFocus: false,
					}}
				>
					<ApplicationContextProvider properties={properties}>
						<TestrayContextProvider>
							<ClayIconProvider>
								<TestrayRouter />
							</ClayIconProvider>
						</TestrayContextProvider>
					</ApplicationContextProvider>
				</SWRConfig>
			);
		}
	}
}

const ELEMENT_ID = 'liferay-testray-custom-element';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, Testray);
}
