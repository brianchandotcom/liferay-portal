/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface IFDSState {
	filters: Array<unknown>;
	search: {query: string};
}

type IDisposable = {dispose: () => void};

type ILiferayState = {
	__unsafe__: {
		getAtomOrSelectorKey: (key: string) => unknown | null;
		readKey: <T>(key: string) => T | null;
		subscribeKey: <T>(
			key: string,
			callback: (value: T) => void
		) => IDisposable;
		writeKey: <T>(key: string, value: T) => void;
	};
};

type ILiferay = {
	State: ILiferayState;
	ThemeDisplay: {
		getLanguageId: () => string;
	};
};

declare global {
	interface Window {
		Liferay: ILiferay;
	}
}

export const Liferay = window.Liferay;
