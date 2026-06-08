/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '~/test/mock-modal';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {open} from '~/shared/actions/modals';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import IndividualAttributes from '../IndividualAttributes';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
	}),
}));

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({timeZoneId: 'UTC'}),
}));

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<IndividualAttributes groupId="23" {...props} />
		</StaticRouter>
	</Provider>
);

describe('IndividualAttributes', () => {
	let OriginalDate;

	beforeAll(() => {
		OriginalDate = global.Date;

		global.Date = class extends Date {
			constructor() {
				super();

				return new OriginalDate(0);
			}
		};
	});

	afterAll(() => {
		global.Date = OriginalDate;
	});

	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('opens modal after click on fielName', async () => {
		const {container, getByText} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		fireEvent.click(getByText('testFildName0'));

		expect(open).toBeCalled();
	});
});
