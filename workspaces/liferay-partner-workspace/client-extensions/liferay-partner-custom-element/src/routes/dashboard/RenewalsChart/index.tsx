/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {useEffect, useState} from 'react';

import './index.css';
import Container from '../../../common/components/dashboard/components/Container';
import {status} from '../../../common/components/dashboard/utils/constants/statusColumns';
import getFilteredRenewals from '../../../common/components/dashboard/utils/getFilteredRenewalsData';
import {siteURL} from '../../../common/components/dashboard/utils/siteURL';
import {Liferay} from '../../../common/services/liferay';
import {Filters} from '../../../common/utils/constants/filters';
import {retry} from '../../../common/utils/retry';

export default function () {
	const [data, setData] = useState();
	const [isLoading, setIsLoading] = useState(false);

	const getRenewalsData = async () => {
		setIsLoading(true);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		const opportunities = await retry<any>(() =>
			fetch(
				`/o/c/opportunitysfs?pageSize=200&sort=closeDate:asc&filter=${Filters.RENEWAL_DASHBOARD.renewals}`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			)
		);

		if (opportunities) {
			setData(opportunities);
			setIsLoading(false);

			return;
		}
	};

	useEffect(() => {
		getRenewalsData();
	}, []);

	const renewalsData = getFilteredRenewals(data);

	const getCurrentStatusColor = (item: any) => {
		if (item?.expirationDays <= 5) {
			return status[5];
		}
		else if (item?.expirationDays <= 15) {
			return status[15];
		}
		else if (item?.expirationDays <= 30) {
			return status[30];
		}
	};

	const getExpirationText = (item: any) => {
		if (item.expirationDays > 1) {
			return `Expires in ${item.expirationDays} days`;
		}

		if (item.expirationDays === 1) {
			return `Expires in 1 day`;
		}

		if (item.expirationDays === 0) {
			return 'Expires today';
		}

		if (item.expirationDays === -1) {
			return 'Expired 1 day ago';
		}

		return `Expired ${Math.abs(item.expirationDays)} days ago`;
	};

	const buildChart = () => {
		if (isLoading) {
			return <ClayLoadingIndicator className="mb-10 mt-10" size="md" />;
		}

		if (!renewalsData.length && !isLoading) {
			return (
				<ClayAlert
					className="h-75 mx-auto text-center"
					displayType="info"
					title="Info:"
				>
					You have no expiring renewals at this time
				</ClayAlert>
			);
		}

		return (
			<div className="align-items-baseline d-flex flex-column justify-content-center px-2">
				{renewalsData?.map((item, index) => {
					getCurrentStatusColor(item);

					return (
						<div
							className="align-items-center d-flex flex-row justify-content-center mb-4"
							key={index}
						>
							<div
								className={classNames(
									'mr-3 status-bar-vertical',
									getCurrentStatusColor(item)
								)}
							></div>

							<div>
								<div className="font-weight-semi-bold responsive-text">
									{item.opportunityName}
								</div>

								<div>
									<span className="font-weight-semi-bold">
										{getExpirationText(item)}
									</span>
									&nbsp;
									<span className="ml-2">
										{item.closeDate}
									</span>
								</div>
							</div>
						</div>
					);
				})}
			</div>
		);
	};

	return (
		<Container
			className="dashboard-renewal-chart justify-content-between"
			footer={
				<div className="pt-4">
					<ClayButton
						className="bg-neutral-0 border-brand-primary-darken-1 text-brand-primary-darken-1"
						displayType="secondary"
						onClick={() =>
							Liferay.Util.navigate(`${siteURL}/sales/renewals`)
						}
						size="sm"
						type="button"
					>
						View all
					</ClayButton>
				</div>
			}
			title="Renewals"
		>
			{buildChart()}
		</Container>
	);
}
