/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import './index.css';
import Container from '../../../common/components/dashboard/components/Container';
import DonutChart from '../../../common/components/dashboard/components/DonutChart';
import {revenueChartColumnColors} from '../../../common/components/dashboard/utils/constants/chartColumnsColors';
import getRevenueChartColumns from '../../../common/components/dashboard/utils/getRevenueChartColumns';
import {Liferay} from '../../../common/services/liferay';
import {LiferayAPIs} from '../../../common/services/liferay/common/enums/apis';
import {Filters} from '../../../common/utils/constants/filters';
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
		const opportunities = await retry<any>(() =>
			fetch(
				`/o/c/opportunitysfs?pageSize=200&sort=closeDate:desc&filter=${Filters.REVENUE_DASHBOARD.opportunities}`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			)
		);

		const myUserAccount = await retry<any>(() =>
			fetch(`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/my-user-account`, {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			})
		);

		const account =
			myUserAccount.accountBriefs[0]?.externalReferenceCode &&
			(await retry<any>(() =>
				fetch(
					`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/accounts/by-external-reference-code/${myUserAccount.accountBriefs[0]?.externalReferenceCode}`,
					{
						headers: {
							'accept': 'application/json',
							'x-csrf-token': Liferay.authToken,
						},
					}
				)
			));

		const currency = account ? account.currency : 'USD';

		if (opportunities) {
			setCurrencyData(currency);

			getRevenueChartColumns(
				currency,
				opportunities,
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
			className="dashboard-revenue-chart justify-content-between pb-7"
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
