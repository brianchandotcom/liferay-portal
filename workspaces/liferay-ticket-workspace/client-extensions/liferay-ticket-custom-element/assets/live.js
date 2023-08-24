/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
const script = document.createElement('script');
script.type = 'module';

script.textContent =
	"import RefreshRuntime from 'http://localhost:5173/@react-refresh'; " +
	'RefreshRuntime.injectIntoGlobalHook(window); ' +
	'window.$RefreshReg$ = () => {}; ' +
	'window.$RefreshSig$ = () => (type) => type; ' +
	'window.__vite_plugin_react_preamble_installed__ = true; ';

document.head.appendChild(script);
