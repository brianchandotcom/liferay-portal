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
import {ObjectActionName} from '../../../common/enums/objectActionName';
import {PermissionActionType} from '../../../common/enums/permissionActionType';
import {PRMPageRoute} from '../../../common/enums/prmPageRoute';
import usePermissionActions from '../../../common/hooks/usePermissionActions';
import {Filters} from '../../../common/utils/constants/filters';
import {retry} from '../../../common/utils/retry';

const DealsChart = () => {
	const [rejectedLeads, setRejectedLeads] = useState([]);
	const [submittedLeads, setSubmittedLeads] = useState([]);
	const [approvedLeads, setApprovedLeads] = useState([]);

	const [loading, setLoading] = useState(false);
	const actions = usePermissionActions(ObjectActionName.DEAL_REGISTRATION);

	const getLeads = async () => {
		setLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const leads = await retry<any>(() =>
			fetch(
				`/o/c/leadsfs?pageSize=200&filter=${Filters.DEAL_DASHBOARD.deals}`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			)
		);

		if (leads) {
			const currentYear = new Date().getFullYear();

			const approvedData = leads.items.filter(
				(lead: {
					createdDate: string;
					isConverted: boolean;
					leadStatus: string;
				}) => {
					const createDateYear = new Date(
						lead.createdDate
					).getFullYear();

					return lead.isConverted && createDateYear === currentYear;
				}
			);
			const rejectedData = leads.items.filter(
				(lead: {createdDate: string; leadStatus: string}) => {
					const createDateYear = new Date(
						lead.createdDate
					).getFullYear();

					return (
						lead.leadStatus === 'CAM rejected' &&
						createDateYear === currentYear
					);
				}
			);
			const sumbittedData = leads.items.filter(
				(lead: {
					createdDate: string;
					isConverted: boolean;
					leadStatus: string;
				}) => {
					const createDateYear = new Date(
						lead.createdDate
					).getFullYear();

					return (
						lead.leadStatus !== 'CAM rejected' &&
						!lead.isConverted &&
						createDateYear === currentYear
					);
				}
			);

			setApprovedLeads(approvedData);
			setRejectedLeads(rejectedData);
			setSubmittedLeads(sumbittedData);

			setLoading(false);

			return;
		}
	};

	useEffect(() => {
		getLeads();
	}, []);

	const leadsChartValues = getLeadsChartValues(
		submittedLeads,
		approvedLeads,
		rejectedLeads
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
			return <ClayLoadingIndicator className="mb-10 mt-10" size="md" />;
		}

		if (!loading && !leadsChartValues) {
			return (
				<ClayAlert
					className="mx-auto text-center w-75"
					displayType="info"
					title="Info:"
				>
					No Data Available
				</ClayAlert>
			);
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
			className="deals-chart-card-height justify-content-between"
			footer={
				<div className="pt-5">
					<ClayButton
						className="bg-neutral-0 border-brand-primary-darken-1 text-brand-primary-darken-1"
						displayType="secondary"
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/sales/deal-registrations`
							)
						}
						size="sm"
						type="button"
					>
						View All
					</ClayButton>

					{actions?.includes(PermissionActionType.CREATE) && (
						<ClayButton
							className="btn btn-primary ml-4"
							displayType="primary"
							onClick={() =>
								Liferay.Util.navigate(
									`${siteURL}/${PRMPageRoute.CREATE_DEAL_REGISTRATION}`
								)
							}
							size="sm"
							type="button"
						>
							Register New Deal
						</ClayButton>
					)}
				</div>
			}
			title="Deal Registrations"
		>
			<Chart />
		</Container>
	);
};

export default DealsChart;
