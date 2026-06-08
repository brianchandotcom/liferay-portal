/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render, waitFor} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import mockDate from '~/test/mock-date';

import VerticalTimeline from '../VerticalTimeline';

jest.unmock('react-dom');

const ITEMS = [
	{
		header: true,
		title: 'Yesterday',
	},
	{
		attributes: {
			contentLanguageID: undefined,
			header: 'Session Attributes',
			screenHeight: '1229',
			screenWidth: '1541',
			timezoneOffset: '-07:00',
			userAgent:
				'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36',
		},
		browserName: 'Firefox',
		device: 'Unknown',
		endTime: undefined,
		nestedItems: [
			{
				attributes: {
					canonicalUrl: 'http://192.168.86.193:3001/',
					header: 'Event Attributes',
					referrer: '',
					title: 'Liferay Home Page',
					url: 'http://192.168.86.193:3001/',
				},
				subtitle: 'www.liferay.com/testing',
				symbol: 'web-content',
				time: 1518648993917,
				title: 'Visited Liferay: Testing',
			},
			{
				subtitle: 'www.liferay.com/testing 2',
				symbol: 'web-content',
				time: 1518648993917,
				title: 'Visited Liferay: Testing 2',
			},
		],
		subtitle: '3 Document Downloads, 2 Form Submissions, 24 Page Visits',
		symbol: 'web-content',
		time: 1518648993917,
		title: 'Opened Email',
		type: 'Download',
	},
	{
		header: true,
		title: 'Today',
	},
	{
		attributes: {
			header: 'Session Attributes',
			screenHeight: '1229',
			screenWidth: '1541',
			timezoneOffset: '-07:00',
			userAgent:
				'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36',
		},
		browserName: 'Firefox',
		device: 'Mobile',
		endTime: 'Wed Sep 01 20:52:49 GMT 2021',
		nestedItems: [
			{
				attributes: {
					canonicalUrl: 'http://192.168.86.193:3001/',
					header: 'Event Attributes',
					referrer: '',
					title: 'Liferay Home Page',
					url: 'http://192.168.86.193:3001/',
				},
				subtitle: 'www.liferay.com/testing',
				symbol: 'web-content',
				time: 1518648993917,
				title: 'Visited Liferay: Testing',
			},
			{
				subtitle: 'www.liferay.com/testing 2',
				symbol: 'web-content',
				time: 1518648993917,
				title: 'Visited Liferay: Testing 2',
			},
		],
		subtitle: '3 Document Downloads, 2 Form Submissions, 24 Page Visits',
		symbol: 'web-content',
		time: 1518648993917,
		title: 'Opened Email',
		type: 'Download',
	},
	{
		attributes: {
			header: 'Session Attributes',
			screenHeight: '1229',
			screenWidth: '1541',
			timezoneOffset: '-07:00',
			userAgent:
				'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36',
		},
		browserName: 'Firefox',
		device: 'Desktop',
		endTime: 'Wed Sep 01 20:52:49 GMT 2021',
		nestedItems: [
			{
				attributes: {
					canonicalUrl: 'http://192.168.86.193:3001/',
					header: 'Event Attributes',
					referrer: '',
					title: 'Liferay Home Page',
					url: 'http://192.168.86.193:3001/',
				},
				description:
					'Liferay: Digital experience software tailored to your needs',
				subtitle: 'www.liferay.com/testing',
				symbol: 'web-content',
				time: 1518648993917,
				title: 'Visited Liferay: Testing',
			},
			{
				attributes: {
					canonicalUrl: 'http://192.168.86.193:3001/',
					header: 'Event Attributes',
					referrer: '',
					title: 'Liferay Home Page',
					url: 'http://192.168.86.193:3001/',
				},
				subtitle: 'www.liferay.com/testing 2',
				symbol: 'web-content',
				time: 1518648993917,
				title: 'Visited Liferay: Testing 2',
			},
		],
		subtitle: '3 Document Downloads, 2 Form Submissions, 24 Page Visits',
		symbol: 'web-content',
		time: 1518648993917,
		title: 'Opened Email',
		type: 'Download',
		url: 'www.liferay.com',
	},
];

const SESSION_ATTRIBUTES_TITLE = 'Session Attributes';

const DefaultComponent = (props) => (
	<StaticRouter>
		<VerticalTimeline {...props} />
	</StaticRouter>
);

describe('VerticalTimeline', () => {
	afterEach(cleanup);

	beforeAll(mockDate);

	it('renders with a header and initialExpanded', () => {
		const {container} = render(
			<DefaultComponent
				headerLabels={{
					count: 'count',
					label: 'label',
					title: 'title',
				}}
				initialExpanded
				items={ITEMS}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders on loading state', () => {
		const {container} = render(<DefaultComponent loading />);

		expect(container.querySelector('.loading-root')).toBeInTheDocument();
	});

	it('expands TimelineItem when clicked', async () => {
		const {container, getAllByText} = render(
			<DefaultComponent
				headerLabels={{
					count: 'count',
					label: 'label',
					title: 'title',
				}}
				initialExpanded
				items={ITEMS}
			/>
		);

		fireEvent.click(
			container.getElementsByClassName(
				'timeline-panel-body-content selectable'
			)[0]
		);

		const sessionAttributes = await waitFor(
			() => getAllByText(/Session Attributes/)[0]
		);

		expect(sessionAttributes).toHaveTextContent(SESSION_ATTRIBUTES_TITLE);
	});
});
