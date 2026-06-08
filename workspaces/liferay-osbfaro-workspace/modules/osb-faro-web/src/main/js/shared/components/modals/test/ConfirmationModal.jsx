/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

import ConfirmationModal from '../ConfirmationModal';

jest.unmock('react-dom');

const MESSAGE = 'message';

const DefaultComponent = (props) => (
	<ConfirmationModal {...props} onClose={noop} />
);

describe('ConfirmationModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);
		expect(container).toMatchSnapshot();
	});

	it('renders with a message', () => {
		const message = 'This is a message.';

		const {getByText} = render(
			<DefaultComponent message="This is a message." />
		);

		expect(getByText(message)).toBeTruthy();
	});

	it('renders with a warning submit button', () => {
		const {container, getByText} = render(
			<ConfirmationModal
				submitButtonDisplay="warning"
				submitMessage="delete"
			/>
		);

		expect(container.querySelector('.btn-warning')).toBeTruthy();
		expect(getByText('delete')).toBeTruthy();
	});

	it('renders with a custom cancel button text', () => {
		const {getByText} = render(<ConfirmationModal cancelMessage="leave" />);

		expect(getByText('leave')).toBeTruthy();
	});

	it('submits when the promisse is rejected', () => {
		const {getByText} = render(
			<ConfirmationModal
				onClose={noop}
				onSubmit={() => Promise.reject()}
				submitMessage={MESSAGE}
			/>
		);

		fireEvent.click(getByText(MESSAGE));

		expect(getByText(MESSAGE)).toBeTruthy();
	});

	it('submits when the promisse is resolved', () => {
		const {getByText} = render(
			<ConfirmationModal
				onClose={noop}
				onSubmit={() => Promise.resolve()}
				submitMessage={MESSAGE}
			/>
		);

		fireEvent.click(getByText(MESSAGE));

		expect(getByText(MESSAGE)).toBeTruthy();
	});

	it('submits when the submit is not a promise', () => {
		const {getByText} = render(
			<ConfirmationModal
				onClose={noop}
				onSubmit={noop}
				submitMessage={MESSAGE}
			/>
		);

		fireEvent.click(getByText(MESSAGE));

		expect(getByText(MESSAGE)).toBeTruthy();
	});
});
