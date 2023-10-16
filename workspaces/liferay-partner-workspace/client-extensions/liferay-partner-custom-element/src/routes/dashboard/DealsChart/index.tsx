/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayChart from '@clayui/charts';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useState} from 'react';

import Container from '../../../common/components/dashboard/components/Container';
import {dealsChartColumnColors} from '../../../common/components/dashboard/utils/constants/chartColumnsColors';
import getLeadsChartValues from '../../../common/components/dashboard/utils/getLeadsChartValues';
import {siteURL} from '../../../common/components/dashboard/utils/siteURL';
import {Liferay} from '../../../common/services/liferay';

import './index.css';
import {retry} from '../../../common/utils/retry';

const DealsChart = () => {
	const [rejectedLeads, setRejectedLeads] = useState([]);
	const [submittedLeads, setSubmittedLeads] = useState([]);
	const [approvedLeads, setApprovedLeads] = useState([]);

	const [loading, setLoading] = useState(false);

	const getLeads = async () => {
		setLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const responseApproved = await retry<Response>(() =>
			fetch(
				"/o/c/leadsfs?pageSize=200&filter=leadType eq 'Partner Qualified Lead (PQL)' and leadStatus eq 'Qualified'",
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			)
		);

		const responseRejected = await retry<Response>(() =>
			fetch(
				"/o/c/leadsfs?pageSize=200&filter=leadType eq 'Partner Qualified Lead (PQL)' and leadStatus eq 'CAM rejected'",
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			)
		);

		const responseSubmitted = await retry<Response>(() =>
			fetch(
				"/o/c/leadsfs?pageSize=200&filter=leadType eq 'Partner Qualified Lead (PQL)' and leadStatus ne 'Qualified' and leadStatus ne 'CAM rejected'",
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			)
		);

		if (
			responseApproved.ok &&
			responseRejected.ok &&
			responseSubmitted.ok
		) {
			const approvedData = await responseApproved.json();
			const rejectedData = await responseRejected.json();
			const sumbittedData = await responseSubmitted.json();

			setApprovedLeads(approvedData?.items);
			setRejectedLeads(rejectedData?.items);
			setSubmittedLeads(sumbittedData?.items);

			return;
		}

		setLoading(false);
	};

	useEffect(() => {
		getLeads();
	}, []);

	const leadsChartValues = getLeadsChartValues(
		rejectedLeads,
		submittedLeads,
		approvedLeads
	);

	const Chart = () => {
		const chart = {
			bar: {
				radius: {
					ratio: 0.2,
				},
				width: {
					ratio: 0.3,
				},
			},
			data: {
				colors: dealsChartColumnColors,
				columns: [
					['x', 'Q1', 'Q2', 'Q3', 'Q4'],
					['Submitted', ...leadsChartValues?.submitted],
					['Approved', ...leadsChartValues?.approved],
					['Rejected', ...leadsChartValues?.rejected],
				],
				groups: [['submitted', 'approved']],
				order: 'desc',
				type: 'bar',
				types: {
					approved: 'bar',
					rejected: 'spline',
					submitted: 'bar',
				},
				x: 'x',
			},
			grid: {
				y: {
					lines: [{value: 100}, {value: 200}, {value: 300}],
				},
			},
		};
		if (loading) {
			<ClayLoadingIndicator className="mb-10 mt-9" size="md" />;
		}

		if (!loading && !leadsChartValues) {
			<ClayAlert
				className="mx-auto w-50"
				displayType="info"
				title="Info:"
			>
				No Data Available
			</ClayAlert>;
		}

		return (
			<ClayChart
				axis={{
					x: {
						show: true,
						type: 'category',
					},
				}}
				bar={chart.bar}
				data={chart.data}
				grid={chart.grid}
			/>
		);
	};

	return (
		<Container
			className="deals-chart-card-height"
			footer={
				<>
					<ClayButton
						className="border-brand-primary-darken-1 mt-2 text-brand-primary-darken-1"
						displayType="secondary"
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/sales/deal-registrations`
							)
						}
						type="button"
					>
						View All
					</ClayButton>
					<ClayButton
						className="btn btn-primary ml-4 mt-2"
						displayType="primary"
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/sales/deal-registrations/new`
							)
						}
						type="button"
					>
						Register New Deal
					</ClayButton>
				</>
			}
			title="Deal Registrations"
		>
			<Chart />
		</Container>
	);
};

export default DealsChart;
