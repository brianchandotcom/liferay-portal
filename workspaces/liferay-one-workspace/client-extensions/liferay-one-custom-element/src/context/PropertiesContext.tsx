/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, createContext, useContext} from 'react';

import {Properties} from '../utils/attributes';

const PropertiesContext = createContext<Properties | null>(null);

type PropertiesProviderProps = {
	children: ReactNode;
	value: Properties;
};

export function PropertiesProvider({children, value}: PropertiesProviderProps) {
	return (
		<PropertiesContext.Provider value={value}>
			{children}
		</PropertiesContext.Provider>
	);
}

export function useProperties(): Properties {
	const ctx = useContext(PropertiesContext);

	if (!ctx) {
		throw new Error('useProperties must be used within PropertiesProvider');
	}

	return ctx;
}
