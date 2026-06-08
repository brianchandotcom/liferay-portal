/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useParams} from 'react-router-dom';
import BasePage from '~/shared/components/base-page';
import {useChannelContext} from '~/shared/context/channel';
import * as breadcrumbs from '~/shared/util/breadcrumbs';

import IndividualsOverviewCDP from './IndividualsOverviewCDP';

const IndividualsDashboardCDP = () => {
	const {channelId = '', groupId = ''} = useParams<{
		channelId: string;
		groupId: string;
	}>();
	const {selectedChannel} = useChannelContext();

	return (
		<BasePage
			className="individuals-dashboard-root"
			documentTitle={Liferay.Language.get('individuals')}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel && selectedChannel.name,
					}),
					breadcrumbs.getEntityName({
						label: Liferay.Language.get('individuals'),
					}),
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					title={Liferay.Language.get('individuals')}
				/>
			</BasePage.Header>

			<IndividualsOverviewCDP />
		</BasePage>
	);
};

export default IndividualsDashboardCDP;
