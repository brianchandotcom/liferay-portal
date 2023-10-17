/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import Container from '../../../common/components/dashboard/components/Container';
import DonutChart from '../../../common/components/dashboard/components/DonutChart';
import {revenueChartColumnColors} from '../../../common/components/dashboard/utils/constants/chartColumnsColors';
import getRevenueChartColumns from '../../../common/components/dashboard/utils/getRevenueChartColumns';
import {Liferay} from '../../../common/services/liferay';
import {retry} from '../../../common/utils/retry';

export default function () {
	const [titleChart, setTitleChart] = useState('');
	const [valueChart, setValueChart] = useState('');
	const [columnsRevenueChart, setColumnsRevenueChart] = useState([]);
	const [loading, setLoading] = useState(false);
	const [currencyData, setCurrencyData] = useState('');

	const getRevenueData = async () => {
		setLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const growthRevenueResponseNewProject = await retry<Response>(() =>
			fetch(
				"/o/c/opportunitysfs?pageSize=200&sort=closeDate:desc&filter=type eq 'New Project Existing Business' and stage eq 'Closed Won'",
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			)
		);

		const growthRevenueResponseNewBusiness = await retry<Response>(() =>
			fetch(
				"/o/c/opportunitysfs?pageSize=200&sort=closeDate:desc&filter=type eq 'New Business' and stage eq 'Closed Won'",
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			)
		);

		const renewalRevenueResponse = await retry<Response>(() =>
			fetch(
				"/o/c/opportunitysfs?&pageSize=200&sort=closeDate:desc&filter=type ne 'New Business' and type ne 'New Project Existing Business' and stage ne 'Rejected' and stage ne 'Rolled into another opportunity' and stage ne 'Disqualified' and stage ne 'Closed Lost'",
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			)
		);

		if (
			growthRevenueResponseNewProject.ok &&
			renewalRevenueResponse.ok &&
			growthRevenueResponseNewBusiness.ok
		) {
			const growthRevenueResponseNewProjectData = await growthRevenueResponseNewProject.json();
			const growthRevenueResponseNewBusinessData = await growthRevenueResponseNewBusiness.json();
			const renewalRevenueData = await renewalRevenueResponse.json();

			const revenueCurrency = 'USD';

			setCurrencyData(revenueCurrency);

			getRevenueChartColumns(
				revenueCurrency,
				growthRevenueResponseNewProjectData,
				growthRevenueResponseNewBusinessData,
				renewalRevenueData,
				setTitleChart,
				setValueChart,
				setColumnsRevenueChart
			);
			setLoading(false);

			return;
		}
	};

	useEffect(() => {
		getRevenueData();
	}, []);

	const chartData = {
		colors: revenueChartColumnColors,
		columns: columnsRevenueChart,
		type: 'donut',
	};

	return (
		<Container
			className="dashboard-mdf-request-chart justify-content-between pb-7"
			title="Revenue"
		>
			<DonutChart
				chartDataColumns={chartData}
				dataCurrency={currencyData}
				isLoading={loading}
				titleChart={titleChart}
				valueChart={valueChart}
			/>
		</Container>
	);
}
