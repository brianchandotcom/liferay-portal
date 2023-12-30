/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Context} from 'react';
import {QueryClient} from 'react-query';

const queryClient = new QueryClient();
const defaultPage = '';

export const QueryClientContext: Context<QueryClient> = React.createContext(
	queryClient
);
export const DefaultAppContext: Context<string> = React.createContext(
	defaultPage
);
