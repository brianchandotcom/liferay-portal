/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {useParams} from 'react-router-dom';
import DistributionCard from '~/contacts/components/distribution-card';
import {fetchIndividualsDistribution} from '~/shared/actions/distributions';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {Routes, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';
import {toPromise} from '~/shared/util/validators';

const connector = connect(null, {
	fetchDistribution: fetchIndividualsDistribution,
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IIndividualsDistributionCardProps
	extends React.HTMLAttributes<HTMLElement>,
		PropsFromRedux {
	id?: string;
	showAddDataSource?: boolean;
}

const IndividualsDistributionCard: React.FC<
	IIndividualsDistributionCardProps
> = ({fetchDistribution, id, ...otherProps}) => {
	const {channelId = '', groupId = ''} = useParams<{
		channelId: string;
		groupId: string;
	}>();

	return (
		<DistributionCard
			channelId={channelId}
			distributionKey="individualsDashboard"
			fetchDistribution={(name) => toPromise(fetchDistribution(name))}
			groupId={groupId}
			id={id ?? groupId}
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
					title={Liferay.Language.get('there-are-no-results-found')}
				/>
			)}
			viewAllLink={toRoute(Routes.CONTACTS_INDIVIDUALS_DISTRIBUTION, {
				channelId,
				groupId,
			})}
			{...otherProps}
		/>
	);
};

export default connector(IndividualsDistributionCard);
