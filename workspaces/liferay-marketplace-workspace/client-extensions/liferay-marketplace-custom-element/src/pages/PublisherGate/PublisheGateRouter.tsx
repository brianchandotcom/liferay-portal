/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import withProviders from '../../hoc/withProviders';
import {PublisherGatePage} from './PublisherGatePage';
import PublisherGateSteps from './components/PublisherGateSteps';

const PublisherGateRouter = () => (
	<div className="publisher-gate-page-container">
		<HashRouter>
			<Routes>
				<Route element={<PublisherGatePage />} index />
				<Route
					element={<PublisherGateSteps />}
					path="request-account"
				/>
			</Routes>
		</HashRouter>
	</div>
);

export default withProviders(PublisherGateRouter);
