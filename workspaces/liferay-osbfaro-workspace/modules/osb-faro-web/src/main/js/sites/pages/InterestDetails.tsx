/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {pickBy} from 'lodash';
import React from 'react';
import {useParams} from 'react-router-dom';
import InterestDetails from '~/shared/components/InterestDetails';
import {useQueryRangeSelectors} from '~/shared/hooks/useQueryRangeSelectors';
import {Router} from '~/shared/types';
import {Routes, setUriQueryValues, toRoute} from '~/shared/util/router';

interface IInterestDetailsProps extends React.HTMLAttributes<HTMLDivElement> {
	router: Router;
}

const InterestDetailsPage: React.FC<IInterestDetailsProps> = ({router}) => {
	const {channelId, groupId} = useParams();
	const rangeSelectors = useQueryRangeSelectors();

	return (
		<div className="sites-dashboard-interest-details-root">
			<div className="row">
				<div className="col-xl-12">
					<div className="back-button-root mb-2">
						<ClayLink
							borderless
							button
							displayType="secondary"
							href={setUriQueryValues(
								pickBy({...rangeSelectors}),

								toRoute(Routes.SITES_INTERESTS, {
									channelId,
									groupId,
								})
							)}
						>
							<ClayIcon
								className="icon-root mr-2"
								symbol="angle-left-small"
							/>

							{Liferay.Language.get('back-to-interests')}
						</ClayLink>
					</div>

					<InterestDetails
						className="sites-interest-details-root"
						router={router}
					/>
				</div>
			</div>
		</div>
	);
};

export default InterestDetailsPage;
