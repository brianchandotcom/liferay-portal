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

import {createCustomValueMap} from '../../../utils/custom-inputs';
import CustomSelectEntityInput from '../CustomSelectEntityInput';

jest.unmock('react-dom');

const mockSegment = data.getImmutableMock(Segment, data.mockSegment, 0, {
	referencedObjects: {
		organizations: {
			123: data.mockGraphqlOrganization('123', {
				name: 'Foo Organization',
			}),
		},
	},
});

const mockValue = createCustomValueMap([
	{key: 'criterionGroup', value: [{operatorName: 'eq', value: ''}]},
]);

const defaultProps = {
	displayValue: 'Address',
	onChange: jest.fn(),
	operatorRenderer: () => <div>operator</div>,
	property: new Property(),
	touched: false,
	valid: true,
	value: mockValue,
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<ReferencedObjectsProvider segment={mockSegment}>
			<CustomSelectEntityInput {...defaultProps} {...props} />
		</ReferencedObjectsProvider>
	</Provider>
);

describe('CustomSelectEntityInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent valid={false} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with a preselected entity', () => {
		const {getByText} = render(
			<DefaultComponent
				entityType={EntityType.Organizations}
				value={createCustomValueMap([
					{
						key: 'criterionGroup',
						value: [{operatorName: 'eq', value: '123'}],
					},
				])}
			/>
		);

		expect(getByText('Foo Organization')).toBeTruthy();
	});
});
