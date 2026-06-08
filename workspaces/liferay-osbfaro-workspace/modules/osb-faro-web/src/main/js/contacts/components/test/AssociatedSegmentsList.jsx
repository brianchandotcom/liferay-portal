/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {Project} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import AssociatedSegmentsList from '../AssociatedSegmentsList';

jest.unmock('react-dom');

const defaultProps = {
	project: new Project(
		data.mockProject(23, {
			faroSubscription: fromJS(data.mockSubscription()),
		})
	),
};

const WrappedComponent = () => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<StaticRouter>
				<AssociatedSegmentsList
					channelId="123123"
					dataSourceFn={() => Promise.resolve({})}
					groupId="23"
					id="test"
					total={2}
				/>
			</StaticRouter>
		</StaticRouter>
	</Provider>
);

describe('AssociatedSegmentsList', () => {
	it('renders card header with segment total', () => {
		const {container} = render(<WrappedComponent {...defaultProps} />);

		expect(container.querySelector('.card-title')).toHaveTextContent(
			'Associated Segments'
		);
		expect(container.querySelector('.secondary-info b')).toHaveTextContent(
			'2'
		);
	});

	it('renders loading state without ghost table', () => {
		const {container} = render(<WrappedComponent {...defaultProps} />);

		expect(container.querySelector('.loading-root')).toBeTruthy();
		expect(container.querySelector('table')).toBeFalsy();
	});
});
