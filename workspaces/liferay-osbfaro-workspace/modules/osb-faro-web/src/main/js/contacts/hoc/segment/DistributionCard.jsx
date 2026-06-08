/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';
import {connect} from 'react-redux';
import DistributionCard from '~/contacts/components/distribution-card';
import {fetchDistribution} from '~/shared/actions/distributions';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {Routes, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

const SegmentDistributionCard = ({channelId, groupId, id, ...otherProps}) => (
	<DistributionCard
		channelId={channelId}
		distributionKey={id}
		groupId={groupId}
		id={id}
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
								URLConstants.SegmentsDistributionDocumentationLink
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
		showContext
		viewAllLink={toRoute(Routes.CONTACTS_SEGMENT_DISTRIBUTION, {
			channelId,
			groupId,
			id,
		})}
		{...otherProps}
	/>
);

export default connect(null, {fetchDistribution})(SegmentDistributionCard);
