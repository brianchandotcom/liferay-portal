/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayContact from './components/LiferayContact';
import SLACardsList from './components/SLACardsList';

const SupportOverview = ({koroneikiAccount, loading}) => (
	<div className="d-flex flex-column flex-xl-row">
		<SLACardsList koroneikiAccount={koroneikiAccount} loading={loading} />

		<LiferayContact koroneikiAccount={koroneikiAccount} loading={loading} />
	</div>
);

export default SupportOverview;
