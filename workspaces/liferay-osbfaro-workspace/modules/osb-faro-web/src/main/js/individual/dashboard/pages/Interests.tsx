/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';
import {useParams} from 'react-router-dom';
import BasePage from '~/shared/components/base-page';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {useDataSources} from '~/shared/context/dataSources';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {Routes, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

import Interests from '../hocs/Interests';

const InterestsPage = () => {
	const {groupId} = useParams();
	const currentUser = useCurrentUser();
	const authorized = currentUser.isAdmin();

	const dataSourceStates = useDataSources();

	return (
		<BasePage.Body pageContainer>
			<StatesRenderer {...dataSourceStates}>
				<StatesRenderer.Empty
					description={
						<>
							{authorized
								? Liferay.Language.get(
										'connect-a-data-source-with-sites-data'
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
						'no-sites-synced-from-data-sources'
					)}
				/>

				<StatesRenderer.Success>
					<div className="individuals-dashboard-interests-root">
						<Interests />
					</div>
				</StatesRenderer.Success>
			</StatesRenderer>
		</BasePage.Body>
	);
};

export default InterestsPage;
