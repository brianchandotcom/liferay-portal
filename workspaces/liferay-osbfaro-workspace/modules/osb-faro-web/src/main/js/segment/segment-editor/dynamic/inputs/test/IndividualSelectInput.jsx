/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {Property} from '~/shared/util/records';
import mockStore from '~/test/mock-store';

import IndividualSelectInput from '../IndividualSelectInput';

jest.unmock('react-dom');

const defaultProps = {
	displayValue: 'fooProperty',
	operatorRenderer: () => <div>test</div>,
	property: new Property(),
	touched: false,
	valid: false,
	value: '123',
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<IndividualSelectInput {...defaultProps} {...props} />
	</Provider>
);

describe('IndividualSelectInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
