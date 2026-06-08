/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BasePage from '~/settings/components/base-page/BasePage';
import {getDefinitions} from '~/shared/util/breadcrumbs';
import {Routes, toRoute} from '~/shared/util/router';

import BlockListCard from '../components/BlockListCard';

interface IBlockListProps {
	groupId: string;
}

const BlockList: React.FC<IBlockListProps> = ({groupId, ...otherProps}) => (
	<BasePage
		breadcrumbItems={[
			getDefinitions({groupId}),
			{
				href: toRoute(Routes.SETTINGS_DEFINITIONS_EVENTS_CUSTOM, {
					groupId,
				}),
				label: Liferay.Language.get('events'),
			},
			{active: true, label: Liferay.Language.get('block-list')},
		]}
		pageDescription={Liferay.Language.get(
			'blocked-events-are-not-processed-by-analytics-cloud'
		)}
		pageTitle={Liferay.Language.get('block-list')}
	>
		<BlockListCard {...otherProps} groupId={groupId} />
	</BasePage>
);

export default BlockList;
