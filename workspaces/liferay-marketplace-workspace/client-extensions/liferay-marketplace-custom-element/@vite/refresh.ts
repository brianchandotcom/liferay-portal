/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

// @ts-ignore

// eslint-disable-next-line @liferay/no-absolute-import
import RefreshRuntime from '/@react-refresh';

/**
 * @description This file is used ONLY and EXCLUSIVE for development
 * When setting up the remote app, add this import
 * http://localhost:3000/@vite/refresh.ts
 */

RefreshRuntime.injectIntoGlobalHook(window);
window.$RefreshReg$ = () => {};
window.$RefreshSig$ = () => (type) => type;

window.__vite_plugin_react_preamble_installed__ = true;

declare global {
	interface Window {
		$RefreshReg$: () => void;
		$RefreshSig$: () => (type: string) => string;
		__vite_plugin_react_preamble_installed__: boolean;
	}
}
