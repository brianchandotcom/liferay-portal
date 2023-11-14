/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useEffect, useState} from 'react';

import './index.css';
import Container from '../../../common/components/dashboard/components/Container';
import DonutChart from '../../../common/components/dashboard/components/DonutChart';
import {mdfChartColumnColors} from '../../../common/components/dashboard/utils/constants/chartColumnsColors';
import getChartColumns from '../../../common/components/dashboard/utils/getChartColumns';
import {siteURL} from '../../../common/components/dashboard/utils/siteURL';
import {ObjectActionName} from '../../../common/enums/objectActionName';
import {PermissionActionType} from '../../../common/enums/permissionActionType';
import {PRMPageRoute} from '../../../common/enums/prmPageRoute';
import usePermissionActions from '../../../common/hooks/usePermissionActions';
import {Liferay} from '../../../common/services/liferay';
import {LiferayAPIs} from '../../../common/services/liferay/common/enums/apis';
import {Filters} from '../../../common/utils/constants/filters';
import {retry} from '../../../common/utils/retry';

const MDFRequestChart = () => {
	const [columnsMDFChart, setColumnsMDFChart] = useState([]);
	const [titleChart, setTitleChart] = useState('');
	const [valueChart, setValueChart] = useState('');
	const [currencyData, setCurrencyData] = useState('');

	const [loading, setLoading] = useState(false);
	const actions = usePermissionActions(ObjectActionName.MDF_REQUEST);

	const getMDFRequests = async () => {
		setLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const mdfRequests = await retry<any>(() =>
			fetch(
				`/o/c/mdfrequests?pageSize=-1&nestedFields=${Filters.MDF_DASHBOARD.fields}&filter=${Filters.MDF_DASHBOARD.requests}`,
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

		if (mdfRequests && currency) {
			setCurrencyData(currency);

			getChartColumns(
				currency,
				mdfRequests,
				setColumnsMDFChart,
				setTitleChart,
				setValueChart
			);

			setLoading(false);

			return;
		}

		setLoading(false);
	};

	useEffect(() => {
		getMDFRequests();
	}, []);

	const chartData = {
		colors: mdfChartColumnColors,
		columns: columnsMDFChart,
		type: 'donut',
	};

	return (
		<Container
			className="dashboard-mdf-chart justify-content-between"
			footer={
				<div className="mt-n2">
					<ClayButton
						className="bg-neutral-0 border-brand-primary-darken-1 text-brand-primary-darken-1"
						displayType="secondary"
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/marketing/mdf-requests`
							)
						}
						size="sm"
					>
						View all
					</ClayButton>

					{actions?.includes(PermissionActionType.CREATE) && (
						<ClayButton
							className="btn btn-primary ml-4"
							displayType="primary"
							onClick={() =>
								Liferay.Util.navigate(
									`${siteURL}/${PRMPageRoute.CREATE_MDF_REQUEST}`
								)
							}
							size="sm"
						>
							New MDF Request
						</ClayButton>
					)}
				</div>
			}
			title="Market Development Funds"
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
};

export default MDFRequestChart;
