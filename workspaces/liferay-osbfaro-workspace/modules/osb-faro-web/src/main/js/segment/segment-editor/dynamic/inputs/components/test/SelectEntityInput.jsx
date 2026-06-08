/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {
	EntityType,
	ReferencedObjectsProvider,
} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {Property, Segment} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import SelectEntityInput from '../SelectEntityInput';

jest.unmock('react-dom');

const mockSegment = data.getImmutableMock(Segment, data.mockSegment, 0, {
	referencedObjects: {
		organizations: {
			123: data.mockGraphqlOrganization('123', {
				dataSourceName: 'Foo DataSource',
				name: 'Foo Organization',
			}),
		},
	},
});

const defaultProps = {
	displayValue: 'Address',
	onValidChange: jest.fn(),
	operatorRenderer: () => <div>operator</div>,
	property: new Property(),
	touched: false,
	valid: true,
	value: '',
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<ReferencedObjectsProvider segment={mockSegment}>
			<SelectEntityInput {...defaultProps} {...props} />
		</ReferencedObjectsProvider>
	</Provider>
);

describe('SelectEntityInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent valid={false} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with a preselected entity', () => {
		const {getByText} = render(
			<DefaultComponent
				entityType={EntityType.Organizations}
				onChange={jest.fn()}
				value="123"
			/>
		);

		expect(getByText('Foo Organization')).toBeTruthy();
		expect(getByText('Foo DataSource')).toBeTruthy();
	});

	it('renders with an error in the select input if the input is touched and valid is false', () => {
		const {container} = render(<DefaultComponent touched valid={false} />);

		expect(
			container.querySelector('.input-group-item.has-error')
		).toBeTruthy();
	});
});
