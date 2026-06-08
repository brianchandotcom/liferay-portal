/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import {EntityTypes} from '~/shared/util/constants';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import ActivitiesChartTimeline from '../ActivitiesChartTimeline';

jest.unmock('react-dom');

const {activityAggregations} = data.mockActivityHistory();

describe('ActivitiesChartTimeline', () => {
	it('renders', async () => {
		const {container} = render(
			<StaticRouter>
				<ActivitiesChartTimeline
					activitiesLabel={Liferay.Language.get(
						'accounts-activities-x'
					)}
					channelId="123123"
					entityType={EntityTypes.Account}
					groupId="23"
					history={activityAggregations}
					id="123"
					rangeSelectors={{
						rangeKey: '30',
					}}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelector('.activities-chart-timeline-root')
		).toBeInTheDocument();
	});
});
