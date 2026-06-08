/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BasePage from '~/settings/components/base-page/BasePage';
import {getDefinitions} from '~/shared/util/breadcrumbs';

import TabsCard from '../components/TabsCard';

interface IEventAttributesProps {
	groupId: string;
}

const EventAttributes: React.FC<IEventAttributesProps> = ({groupId}) => (
	<BasePage
		breadcrumbItems={[
			getDefinitions({groupId}),
			{active: true, label: Liferay.Language.get('event-attributes')},
		]}
		pageDescription={Liferay.Language.get(
			'attributes-provide-additional-context-for-events.-they-are-usually-event-specific-but-can-be-used-by-more-than-one.-global-attributes-will-be-sent-with-all-events-without-needing-to-be-configured'
		)}
		pageTitle={Liferay.Language.get('event-attributes')}
	>
		<TabsCard groupId={groupId} />
	</BasePage>
);

export default EventAttributes;
