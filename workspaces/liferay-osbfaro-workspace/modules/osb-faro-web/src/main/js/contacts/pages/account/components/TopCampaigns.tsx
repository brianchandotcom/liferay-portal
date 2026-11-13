import * as API from 'shared/api';
import Card from 'shared/components/Card';
import ClayLink from '@clayui/link';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import React from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import {columns, FrontendDataSet} from 'shared/components/FrontendDataSet';
import {DropdownRangeKey} from 'shared/components/dropdown-range-key/DropdownRangeKey';
import {IAccountCampaign} from 'shared/api/campaigns';
import {pickBy} from 'lodash';
import {Routes, setUriQueryValues, toRoute} from 'shared/util/router';
import {Text} from '@clayui/core';
import {useParams} from 'react-router-dom';
import {useRangeQueryValues} from 'shared/hooks/useRangeQueryValues';
import {useRangeSelectorChange} from 'shared/hooks/useRangeSelectorChange';
import {useRequest} from 'shared/hooks/useRequest';

interface ITopCampaignsProps {
	accountName?: string;
}

const TopCampaigns: React.FC<ITopCampaignsProps> = ({accountName}) => {
	const {channelId = '', groupId = '', id = ''} = useParams();

	const {apiRangeParams, rangeSelectors, routeRangeParams} =
		useRangeQueryValues();

	const linkQueryValues = {
		...routeRangeParams,
		...pickBy({accountId: id, accountName}),
	};

	const {handleRangeSelectorChange} = useRangeSelectorChange();

	const {data, error, loading} = useRequest({
		dataSourceFn: API.campaigns.fetchTopCampaigns,
		skipRequest: !id,
		variables: {
			accountId: id,
			channelId,
			groupId,
			rangeQueryValues: apiRangeParams,
		},
	});

	return (
		<Card minHeight={260} testId="top-campaigns">
			<Card.Title className="align-items-center d-flex justify-content-between p-3">
				<Text size={5} weight="semi-bold">
					{Liferay.Language.get('top-campaigns').toUpperCase()}
				</Text>

				<DropdownRangeKey
					legacy={false}
					onRangeSelectorChange={handleRangeSelectorChange}
					rangeSelectors={rangeSelectors}
				/>
			</Card.Title>

			<Card.Body className="p-0">
				{id && (
					<StatesRenderer error={error} loading={loading}>
						<StatesRenderer.Loading spacer />

						<StatesRenderer.Error>
							<ErrorDisplay />
						</StatesRenderer.Error>

						<StatesRenderer.Success>
							<FrontendDataSet
								customDataRenderers={{
									campaignNameRenderer: ({
										itemData,
										value,
									}: {
										itemData: IAccountCampaign;
										value: string;
									}) =>
										columns.nameAndLinkRenderer({
											channelId,
											groupId,
											itemData,
											queryValues: linkQueryValues,
											route: Routes.CAMPAIGNS_DETAIL,
											value,
										}),
									countRenderer: columns.countRenderer,
									dateRenderer: columns.dateRenderer,
								}}
								emptyState={{
									description: Liferay.Language.get(
										'this-account-has-not-been-touched-by-any-campaign-yet'
									),
									title: Liferay.Language.get(
										'no-campaigns-found'
									),
								}}
								id="top-campaigns-dataset"
								items={data ?? []}
								showManagementBar={false}
								showPagination={false}
								showSearch={false}
								views={[
									{
										contentRenderer: 'table',
										default: true,
										initialPaginationDelta: 5,
										label: Liferay.Language.get(
											'default-view'
										),
										name: 'table',
										schema: {
											fields: [
												{
													contentRenderer:
														'campaignNameRenderer',
													fieldName: 'campaignName',
													label: Liferay.Language.get(
														'campaign-name'
													),
													truncate: true,
												},
												{
													contentRenderer:
														'countRenderer',
													fieldName:
														'individualsTouched',
													label: Liferay.Language.get(
														'individuals-touched'
													),
												},
												{
													contentRenderer:
														'dateRenderer',
													fieldName: 'lastTouchDate',
													label: Liferay.Language.get(
														'last-touch'
													),
												},
											],
										},
										thumbnail: 'table',
									},
								]}
							/>
						</StatesRenderer.Success>
					</StatesRenderer>
				)}
			</Card.Body>

			{id && (
				<Card.Footer className="d-flex justify-content-end">
					<ClayLink
						borderless
						button
						className="button-root rounded-lg"
						displayType="primary"
						href={setUriQueryValues(
							linkQueryValues,
							toRoute(Routes.CAMPAIGNS, {channelId, groupId})
						)}
						small
					>
						{Liferay.Language.get('view-all')}
					</ClayLink>
				</Card.Footer>
			)}
		</Card>
	);
};

export default TopCampaigns;
