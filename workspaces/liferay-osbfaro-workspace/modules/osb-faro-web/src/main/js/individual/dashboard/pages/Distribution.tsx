/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {get} from 'lodash';
import React from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {useParams} from 'react-router-dom';
import DistributionBase, {
	CONTEXT_OPTIONS,
} from '~/contacts/components/Distribution';
import {
	INDIVIDUALS_DASHBOARD_DISTRUBTIONS_KEY,
	fetchIndividualsDistribution,
} from '~/shared/actions/distributions';
import * as API from '~/shared/api';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {useDataSources} from '~/shared/context/dataSources';
import {compose, withQuery} from '~/shared/hoc';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {Sizes} from '~/shared/util/constants';
import {Routes, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

const Distribution = DistributionBase as React.ComponentType<any>;

const connector = connect(null, {
	fetchDistribution: fetchIndividualsDistribution,
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IIndividualsDistributionProps extends PropsFromRedux {
	knownIndividualCount: number | null;
}

export const IndividualsDistribution = function IndividualsDistribution({
	knownIndividualCount,
	...otherProps
}: IIndividualsDistributionProps) {
	const {groupId} = useParams();
	const dataSourceStates = useDataSources();
	const currentUser = useCurrentUser();
	const authorized = currentUser.isAdmin();

	return (
		<StatesRenderer {...dataSourceStates}>
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
								{Liferay.Language.get('connect-data-source')}
							</ClayLink>
						)}
					</>
				}
				displayCard
				title={Liferay.Language.get('no-data-sources-connected')}
			/>

			<StatesRenderer.Success>
				<div className="container-fluid individuals-dashboard-distribution-root">
					<div className="row">
						<div className="col-xl-12">
							<Distribution
								contextOptions={[CONTEXT_OPTIONS[0]]}
								distributionsKey={
									INDIVIDUALS_DASHBOARD_DISTRUBTIONS_KEY
								}
								knownIndividualCount={knownIndividualCount}
								pageContainer
								{...otherProps}
								noResultsRenderer={() => (
									<NoResultsDisplay
										description={
											<>
												{Liferay.Language.get(
													'try-choosing-a-different-breakdown'
												)}

												<ClayLink
													className="d-block"
													href={
														URLConstants.IndividualsDashboardBreakdownDocumentation
													}
													key="DOCUMENTATION"
													target="_blank"
												>
													{Liferay.Language.get(
														'learn-more-about-distribution'
													)}
												</ClayLink>
											</>
										}
										icon={{
											border: false,
											size: Sizes.XXXLarge,
											symbol: 'ac_satellite',
										}}
										title={Liferay.Language.get(
											'there-are-no-results-found'
										)}
									/>
								)}
							/>
						</div>
					</div>
				</div>
			</StatesRenderer.Success>
		</StatesRenderer>
	);
};

export default compose<any>(
	withQuery(
		({channelId, groupId}: {channelId: string; groupId: string}) =>
			API.individuals.search({
				channelId,
				groupId,
				includeAnonymousUsers: false,
			}),
		(val: unknown) => val,
		({data, error}: {data: unknown; error: unknown}) => ({
			knownIndividualCount: error ? 0 : get(data, 'total', null),
		})
	),
	connector
)(IndividualsDistribution);
