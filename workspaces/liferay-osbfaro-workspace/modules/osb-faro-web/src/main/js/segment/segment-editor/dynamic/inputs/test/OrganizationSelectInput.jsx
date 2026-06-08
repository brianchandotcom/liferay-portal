/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {Property} from '~/shared/util/records';
import mockStore from '~/test/mock-store';

import {createCustomValueMap} from '../../utils/custom-inputs';
import OrganizationSelectInput from '../OrganizationSelectInput';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{key: 'criterionGroup', value: [{operatorName: 'eq', value: ''}]},
]);

const defaultProps = {
	displayValue: 'fooProperty',
	operatorRenderer: () => <div>test</div>,
	property: new Property(),
	touched: false,
	valid: false,
	value: mockValue,
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<OrganizationSelectInput {...defaultProps} {...props} />
	</Provider>
);

describe('OrganizationSelectInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
