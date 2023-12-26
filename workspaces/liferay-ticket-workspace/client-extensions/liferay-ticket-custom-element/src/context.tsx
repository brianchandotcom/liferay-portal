/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Context} from 'react';
import {QueryClient} from 'react-query';

import {Liferay} from './services/liferay';

export type TicketsAppConfigs = {
	defaultPage: string;
	queryClient: QueryClient;
	spriteMap: string;
};

const queryClient = new QueryClient();
export const CONFIGS: TicketsAppConfigs = {
	defaultPage: '',
	queryClient,
	spriteMap: Liferay.Icons.spritemap,
};

export const TicketsAppContext: Context<TicketsAppConfigs> = React.createContext(
	CONFIGS
);
