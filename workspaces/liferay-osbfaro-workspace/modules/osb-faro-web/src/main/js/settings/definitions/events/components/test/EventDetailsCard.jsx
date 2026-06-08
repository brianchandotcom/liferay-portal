/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import EventDetailsCard from '../EventDetailsCard';

jest.unmock('react-dom');

describe('EventDetailsCard', () => {
	const eventAttributes = range(5).map((i) =>
		data.mockEventAttributeDefinition(i)
	);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<EventDetailsCard
						eventAttributes={eventAttributes}
						eventName="viewArticle"
						groupId="23"
					/>
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('matches the displayed code with the selected attributes', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<EventDetailsCard
						eventAttributes={eventAttributes}
						eventName="viewArticle"
						groupId="23"
					/>
				</StaticRouter>
			</Provider>
		);

		fireEvent.click(container.querySelector('.clickable'));

		expect(container.querySelector('.copy-button')).toHaveAttribute(
			'data-clipboard-text',
			[
				"Analytics.track('viewArticle', {",
				"\n\t'name-1': 'samplevalue-1',",
				"\n\t'name-2': 'samplevalue-2',",
				"\n\t'name-3': 'samplevalue-3',",
				"\n\t'name-4': 'samplevalue-4',",
				'\n});',
			].join('')
		);
	});
});
