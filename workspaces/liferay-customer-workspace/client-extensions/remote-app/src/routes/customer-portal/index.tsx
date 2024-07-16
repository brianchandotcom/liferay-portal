/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../routes/customer-portal/styles/app.scss';
import './app.scss';

import {AppContextProvider} from './context';
import Pages from './pages';

const CustomerPortal = () => (
	<AppContextProvider>
		<Pages />
	</AppContextProvider>
);

export default CustomerPortal;
