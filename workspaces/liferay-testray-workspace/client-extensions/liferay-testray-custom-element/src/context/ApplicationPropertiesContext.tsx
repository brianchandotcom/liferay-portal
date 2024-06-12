/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext} from 'react';

const defaultProperties = {
	jiraBaseURL: '',
	pusherKey: '',
	pusherRegion: '',
};

type DefaultProperties = typeof defaultProperties;

type ApplicationContextProviderProps = {
	children: JSX.Element;
	properties: DefaultProperties;
};

export const ApplicationPropertiesContext =
	createContext<DefaultProperties>(defaultProperties);

const ApplicationContextProvider: React.FC<ApplicationContextProviderProps> = ({
	children,
	properties,
}) => (
	<ApplicationPropertiesContext.Provider value={properties}>
		{children}
	</ApplicationPropertiesContext.Provider>
);

export default ApplicationContextProvider;
