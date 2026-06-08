/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import AverageOrderValueCard from '~/commerce/components/AverageOrderValueCard';
import AverageRevenuePerAccountCard from '~/commerce/components/AverageRevenuePerAccountCard';
import IncompleteOrdersCard from '~/commerce/components/IncompleteOrdersCard';
import TotalOrderValueCard from '~/commerce/components/TotalOrderValueCard';

const Overview = () => (
	<div className="commerce-dashboard-overview-root">
		<div className="row">
			<div className="col-xl-6">
				<TotalOrderValueCard />
			</div>
			<div className="col-xl-6">
				<IncompleteOrdersCard />
			</div>
		</div>
		<div className="row">
			<div className="col-xl-6">
				<AverageOrderValueCard />
			</div>
			<div className="col-xl-6">
				<AverageRevenuePerAccountCard />
			</div>
		</div>
	</div>
);

export default Overview;
