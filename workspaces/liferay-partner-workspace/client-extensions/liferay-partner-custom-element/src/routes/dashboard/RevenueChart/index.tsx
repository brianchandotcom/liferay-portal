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

		const myUserAccountResponse = await retry<Response>(() =>
			fetch(`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/my-user-account`, {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			})
		);
		const myUserAccount = await myUserAccountResponse.json();

		const accountResponse =
			myUserAccount.accountBriefs[0]?.externalReferenceCode &&
			(await retry<Response>(() =>
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

		const account = await accountResponse?.json();

		const currency = account ? account.currency : 'USD';

		if (
			growthRevenueResponseNewProject.ok &&
			renewalRevenueResponse.ok &&
			growthRevenueResponseNewBusiness.ok &&
			currency
		) {
			const growthRevenueResponseNewProjectData = await growthRevenueResponseNewProject.json();
			const growthRevenueResponseNewBusinessData = await growthRevenueResponseNewBusiness.json();
			const renewalRevenueData = await renewalRevenueResponse.json();

			setCurrencyData(currency);

			getRevenueChartColumns(
				currency,
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
