/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '~/test/mock-modal';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {open} from '~/shared/actions/modals';
import {Property} from '~/shared/util/records';
import mockStore from '~/test/mock-store';

import SelectEntityFromModal from '../SelectEntityFromModal';

jest.unmock('react-dom');

const defaultProps = {
	columns: [],
	entity: {name: 'fooEntity'},
	property: new Property(),
	renderEntity: ({name}) => name,
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<SelectEntityFromModal {...defaultProps} {...props} />
	</Provider>
);

describe('SelectEntityFromModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('triggers modal when pressing Select button', () => {
		const {getByText} = render(<DefaultComponent />);

		fireEvent.click(getByText('Select'));

		jest.runAllTimers();

		expect(open).toBeCalled();
	});

	it('renders with a preselected entity', () => {
		const {queryByText} = render(<DefaultComponent />);

		expect(queryByText('fooEntity')).toBeTruthy();
	});
});
