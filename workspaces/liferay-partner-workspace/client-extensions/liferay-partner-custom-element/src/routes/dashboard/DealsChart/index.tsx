/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayChart from '@clayui/charts';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useMemo, useState} from 'react';

import Container from '../../../common/components/dashboard/components/Container';
import {dealsChartColumnColors} from '../../../common/components/dashboard/utils/constants/chartColumnsColors';
import getLeadsChartValues from '../../../common/components/dashboard/utils/getLeadsChartValues';
import {getOpportunitiesChartValues} from '../../../common/components/dashboard/utils/getOpportunitiesChartValues';
import {siteURL} from '../../../common/components/dashboard/utils/siteURL';
import {Liferay} from '../../../common/services/liferay';

import './index.css';

const DealsChart = () => {
	const [opportunities, setOpportunities] = useState([]);
	const [leads, setLeads] = useState([]);
	const [loading, setLoading] = useState(false);

	const getOpportunities = async () => {
		setLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch('/o/c/opportunitysfs?pageSize=200', {
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		});

		if (response.ok) {
			const data = await response.json();
			setOpportunities(data?.items);

			return;
		}

		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});

		setLoading(false);
	};

	const getLeads = async () => {
		setLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const response = await fetch('/o/c/leadsfs?pageSize=200', {
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		});

		if (response.ok) {
			const data = await response.json();
			setLeads(data?.items);

			return;
		}

		Liferay.Util.openToast({
			message: 'An unexpected error occured.',
			type: 'danger',
		});

		setLoading(false);
	};

	useEffect(() => {
		getOpportunities();
		getLeads();
	}, []);

	const opportunitiesChartValues = useMemo(
		() => getOpportunitiesChartValues(opportunities),
		[opportunities]
	);

	const leadsChartValues = getLeadsChartValues(leads);

	const totalRejectedChartValues = useMemo(() => {
		return (
			opportunitiesChartValues?.rejected?.map(
				(chartValue, index) =>
					chartValue + leadsChartValues?.rejected[index]
			) || []
		);
	}, [leadsChartValues?.rejected, opportunitiesChartValues?.rejected]);

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
					['Approved', ...opportunitiesChartValues?.approved],
					['Rejected', ...totalRejectedChartValues],
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

		if (!loading && !(opportunitiesChartValues || leadsChartValues)) {
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
