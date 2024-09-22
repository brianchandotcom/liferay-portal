/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {format} from 'date-fns';
import {useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import EmptyState from '../../../../components/EmptyState';
import Table from '../../../../components/Table/Table';
import {ORDER_CUSTOM_FIELDS} from '../../../../enums/Order';
import useMarketplaceSpringBootOAuth2 from '../../../../hooks/useMarketplaceSpringBootOAuth2';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import {copyToClipboard} from '../../../../utils/browser';

type DataSourcesProps = {
	analyticsGroupId: string;
};

const dataSourceTypes = {
	active: 'success',
	inactive: 'danger',
};

const DataSources: React.FC<DataSourcesProps> = ({analyticsGroupId}) => {
	const marketplaceSpringBootOAuth2 = useMarketplaceSpringBootOAuth2();

	const {
		data: projectDataSources,
		isValidating,
		mutate,
	} = useSWR(`/analytics/project/group-${analyticsGroupId}/data-source`, () =>
		marketplaceSpringBootOAuth2.getAnalyticsProjectDataSource(
			analyticsGroupId
		)
	);

	const {items = [], total} = projectDataSources || {};

	if (total === 0) {
		return (
			<EmptyState
				description="No data source was found, copy the token above and associate it with a Liferay DXP."
				type="BLANK"
			>
				<ClayButton
					disabled={isValidating}
					displayType="secondary"
					onClick={() => mutate((data) => data, {revalidate: true})}
					size="sm"
				>
					Refresh
				</ClayButton>
			</EmptyState>
		);
	}

	return (
		<Table
			columns={[
				{key: 'name', title: 'Name'},
				{
					key: 'url',
					render: (URL) => (
						<a href={URL} rel="noopener " target="_blank">
							{URL}
						</a>
					),
					title: 'URL',
				},
				{
					key: 'providerType',
					title: 'Source',
				},
				{
					key: 'createDate',
					render: (createDate) =>
						format(new Date(createDate), 'dd MMM, yyyy'),
					title: 'Date Added',
				},
				{
					key: 'status',
					render: (status = '') => (
						<ClayLabel
							displayType={
								(dataSourceTypes as any)[
									status.toLowerCase()
								] || 'secondary'
							}
						>
							{status}
						</ClayLabel>
					),
					title: 'Status',
				},
			]}
			rows={items}
		/>
	);
};

const ConnectionTokens = () => {
	const marketplaceSpringBootOAuth2 = useMarketplaceSpringBootOAuth2();

	const {placedOrder} = useOutletContext<{placedOrder: PlacedOrder}>();

	const analyticsGroupId =
		placedOrder.customFields[ORDER_CUSTOM_FIELDS.ANALYTICS_GROUP_ID];

	const {data: projectDataSourceToken = ''} = useSWR(
		`/analytics/project/group-${analyticsGroupId}/data-source/token`,
		() =>
			marketplaceSpringBootOAuth2.getAnalyticsProjectDataSourceToken(
				analyticsGroupId
			)
	);

	return (
		<div className="py-4">
			<div className="border col-6 col-lg-6 col-md-12 p-4">
				<div className="align-items-center d-flex justify-content-between">
					<h3>Connect Your Liferay DXP Analytics</h3>

					<div
						className="align-items-center d-flex justify-content-center"
						style={{
							backgroundColor: '#dadada',
							borderRadius: '50%',
							height: 40,
							width: 40,
						}}
					>
						<ClayIcon symbol="diagram" />
					</div>
				</div>

				<div className="mt-2 py-3">
					<label htmlFor="token">
						Copy this token to your Liferay DXP Instance
					</label>

					<div className="align-items-center d-flex">
						<ClayInput
							disabled
							id="token"
							name="token"
							readOnly
							style={{paddingRight: 20}}
							value={
								projectDataSourceToken
									? `${projectDataSourceToken.substring(0, 50)}...`
									: ''
							}
						/>

						<ClayIcon
							color="gray"
							onClick={() => {
								copyToClipboard(projectDataSourceToken);

								Liferay.Util.openToast({
									message: i18n.sub(
										'copied-x-to-the-clipboard',
										'token'
									),
									title: i18n.translate('success'),
								});
							}}
							style={{
								backgroundColor: 'white',
								cursor: 'pointer',
								marginLeft: -30,
							}}
							symbol="copy"
						/>
					</div>
				</div>

				<a
					href="https://learn.liferay.com/en/w/analytics-cloud/getting-started/connecting-liferay-dxp-to-analytics-cloud"
					rel="noopener noreferrer"
					target="_blank"
				>
					Click here to learn how to connect Liferay DXP to Analytics
					Cloud
				</a>
			</div>

			<div className="border mt-4 p-4">
				<h3 className="mb-4">Data Sources</h3>

				<DataSources analyticsGroupId={analyticsGroupId} />
			</div>
		</div>
	);
};

export default ConnectionTokens;
