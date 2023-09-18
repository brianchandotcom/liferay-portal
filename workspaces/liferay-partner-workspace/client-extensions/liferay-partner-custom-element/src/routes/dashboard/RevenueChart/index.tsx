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

export default function () {
	const [titleChart, setTitleChart] = useState('');
	const [valueChart, setValueChart] = useState('');
	const [columnsRevenueChart, setColumnsRevenueChart] = useState([]);
	const [loading, setLoading] = useState(false);
	const [currencyData, setCurrencyData] = useState('');

	const getRevenueData = async () => {
		setLoading(true);
		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch('/o/c/opportunitysfs?&pageSize=200', {
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		});

		if (response.ok) {
			const revenueData = await response.json();

			const revenueCurrency = revenueData?.items[0]?.currency?.key;

			setCurrencyData(revenueCurrency);

			getRevenueChartColumns(
				revenueCurrency,
				revenueData,
				setTitleChart,
				setValueChart,
				setColumnsRevenueChart
			);
			setLoading(false);

			return;
		}
		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});
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
		<Container className="dashboard-mdf-revenue-chart" title="Revenue">
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
