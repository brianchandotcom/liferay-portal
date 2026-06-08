/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';
import {useParams} from 'react-router-dom';
import * as API from '~/shared/api';
import BasePage from '~/shared/components/base-page';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {useChannelContext} from '~/shared/context/channel';
import {useDataSources} from '~/shared/context/dataSources';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {FeatureName, useLimitReached} from '~/shared/hooks/useLimitReached';
import {useRequest} from '~/shared/hooks/useRequest';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {Routes, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

import EventAnalysisListCard from '../hocs/EventAnalysisListCard';

const List = () => {
	const {selectedChannel} = useChannelContext();
	const {channelId = '', groupId = ''} = useParams<{
		channelId: string;
		groupId: string;
	}>();
	const currentUser = useCurrentUser();

	const {empty, error, loading} = useDataSources();

	const {
		data: usageData,
		error: usageError,
		loading: usageLoading,
		refetch,
	} = useRequest({
		dataSourceFn: API.projects.fetchFeatureUsages,
		variables: {groupId},
	});

	const hasLimitReached = useLimitReached({
		data: usageData,
		featureName: FeatureName.EventAnalysis,
	});

	const pageAction = [
		{
			button: true,
			disabled:
				empty || error || usageLoading || usageError || hasLimitReached,
			displayType: 'primary',
			href: toRoute(Routes.EVENT_ANALYSIS_CREATE, {channelId, groupId}),
			label: Liferay.Language.get('create-analysis'),
		},
	];

	const authorized = currentUser.isAdmin();

	return (
		<BasePage documentTitle={Liferay.Language.get('event-analysis')}>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel?.name,
					}),
				]}
				groupId={groupId}
			>
				<BasePage.Row>
					<BasePage.Header.TitleSection
						title={Liferay.Language.get('event-analysis')}
					/>

					<BasePage.Header.Section>
						<BasePage.Header.PageActions actions={pageAction} />

						{hasLimitReached && (
							<ClayButton
								borderless
								className="ml-2"
								data-tooltip-align="right"
								displayType="unstyled"
								size="sm"
								title={Liferay.Language.get(
									'a-maximum-number-of-event-analyses-has-been-reached-delete-an-existing-analysis-to-create-a-new-one'
								)}
							>
								<ClayIcon
									className="text-secondary"
									symbol="exclamation-full"
								/>
							</ClayButton>
						)}
					</BasePage.Header.Section>
				</BasePage.Row>
			</BasePage.Header>

			<BasePage.Body>
				<StatesRenderer empty={empty} error={error} loading={loading}>
					<StatesRenderer.Loading />

					<StatesRenderer.Empty
						description={
							<>
								{authorized
									? Liferay.Language.get(
											'connect-a-data-source-to-get-started'
										)
									: Liferay.Language.get(
											'please-contact-your-workspace-administrator-to-add-data-sources'
										)}

								<ClayLink
									className="d-block mb-3"
									href={URLConstants.DataSourceConnection}
									key="DOCUMENTATION"
									target="_blank"
								>
									{Liferay.Language.get(
										'access-our-documentation-to-learn-more'
									)}
								</ClayLink>

								{authorized && (
									<ClayLink
										button
										className="button-root"
										displayType="primary"
										href={toRoute(
											Routes.SETTINGS_DATA_SOURCE_LIST,
											{
												groupId,
											}
										)}
									>
										{Liferay.Language.get(
											'connect-data-source'
										)}
									</ClayLink>
								)}
							</>
						}
						displayCard
						title={Liferay.Language.get(
							'no-data-sources-connected'
						)}
					/>

					<StatesRenderer.Success>
						<EventAnalysisListCard onItemsChange={refetch} />
					</StatesRenderer.Success>
				</StatesRenderer>
			</BasePage.Body>
		</BasePage>
	);
};

export default List;
