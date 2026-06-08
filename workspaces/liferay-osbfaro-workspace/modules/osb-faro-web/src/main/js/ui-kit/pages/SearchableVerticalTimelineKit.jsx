/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import SearchableVerticalTimeline from '~/shared/components/SearchableVerticalTimelineDeprecated';
import {formatSessions} from '~/shared/util/activitiesDeprecated';
import * as data from '~/test/data';

const dataSourceFn = ({channelId, groupId}) =>
	Promise.resolve(data.mockSearch(data.mockActivity, 2)).then(
		({items, total}) => ({
			items: formatSessions(items, groupId, channelId),
			total,
		})
	);

const HEADER_LABELS = {
	count: Liferay.Language.get('activity-count'),
	label: Liferay.Language.get('time'),
	title: Liferay.Language.get('session'),
};

export default class SearchableVerticalTimelineKit extends React.Component {
	render() {
		return (
			<div>
				<SearchableVerticalTimeline
					dataSourceFn={dataSourceFn}
					dataSourceParams={{channelId: '321', groupId: '23'}}
					headerLabels={HEADER_LABELS}
				/>
			</div>
		);
	}
}
