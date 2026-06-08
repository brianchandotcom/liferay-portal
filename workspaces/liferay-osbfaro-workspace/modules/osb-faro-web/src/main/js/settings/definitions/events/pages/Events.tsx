/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BasePage from '~/settings/components/base-page/BasePage';
import {getDefinitions} from '~/shared/util/breadcrumbs';

import TabsCard from '../components/TabsCard';

interface IEventsProps {
	groupId: string;
}

const Events: React.FC<IEventsProps> = ({groupId}) => (
	<BasePage
		breadcrumbItems={[
			getDefinitions({groupId}),
			{active: true, label: Liferay.Language.get('events')},
		]}
		pageDescription={Liferay.Language.get(
			'this-is-the-data-model-of-events-sent-to-analytics-cloud'
		)}
		pageTitle={Liferay.Language.get('events')}
	>
		<TabsCard groupId={groupId} />
	</BasePage>
);

export default Events;
