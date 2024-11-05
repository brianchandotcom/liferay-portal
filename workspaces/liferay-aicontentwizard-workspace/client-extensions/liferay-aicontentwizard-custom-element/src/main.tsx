/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import ReactDOM, {Root} from 'react-dom/client';
import {SWRConfig} from 'swr';

import App from './routes/App';

import './index.css';
import SWRCacheProvider from './SWRCacheProvider';
import AppContextProvider from './context/AppContext';
import SettingRouter from './routes/settings/SettingRouter';
import {getIconSpriteMap} from './utils/iconSpritemap';

const customElementId = 'liferay-aicontentwizard-custom-element';

type Route = 'app' | 'settings';

class AIContentWizard extends HTMLElement {
	private root?: Root;

	connectedCallback() {
		if (!this.root) {
			this.root = ReactDOM.createRoot(this);

			const route = (this.getAttribute('route') as Route) || 'app';

			this.root.render(
				<SWRConfig
					value={{
						provider: SWRCacheProvider,
						revalidateIfStale: true,
						revalidateOnFocus: false,
					}}
				>
					<AppContextProvider>
						<ClayIconSpriteContext.Provider
							value={getIconSpriteMap()}
						>
							{route === 'app' && <App />}
							{route === 'settings' && <SettingRouter />}
						</ClayIconSpriteContext.Provider>
					</AppContextProvider>
				</SWRConfig>
			);
		}
	}
}

if (!customElements.get(customElementId)) {
	customElements.define(customElementId, AIContentWizard);
}
