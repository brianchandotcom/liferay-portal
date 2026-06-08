/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	cleanup,
	fireEvent,
	render,
	screen,
	waitFor,
} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import {open} from '~/shared/actions/modals';
import {EntityTypes} from '~/shared/util/constants';
import {DataSource} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';
import * as pedantic from '~/test/pedantic';

import {DeleteDataSource} from '../DeleteDataSource';

jest.unmock('react-dom');

jest.mock('~/shared/actions/modals', () => ({
	actionTypes: {
		CLOSE_ALL_MODALS: 'CLOSE_ALL_MODALS',
		CLOSE_MODAL: 'CLOSE_MODAL',
		OPEN_MODAL: 'OPEN_MODAL',
	},
	close: jest.fn(() => ({meta: {}, payload: {}, type: 'close'})),
	modalTypes: {
		CONFIRMATION_MODAL: 'CONFIRMATION_MODAL',
		SEARCHABLE_ENTITIES_TABLE_MODAL: 'SEARCHABLE_ENTITIES_TABLE_MODAL',
	},
	open: jest.fn(() => ({meta: {}, payload: {}, type: 'open'})),
}));

describe('DeleteDataSource', () => {
	beforeEach(() => {
		pedantic.disable();
	});

	afterEach(() => {
		pedantic.enable();
		cleanup();
	});

	const dataSource = data.getImmutableMock(
		DataSource,
		data.mockLiferayDataSource
	);

	const entitiesCount = {
		[EntityTypes.Account]: 123,
		[EntityTypes.Asset]: 23,
		[EntityTypes.Individual]: 1,
		[EntityTypes.IndividualsSegment]: 12,
		[EntityTypes.Page]: 1234,
	};

	const groupId = '23';

	const store = mockStore(
		fromJS({
			projects: {
				[groupId]: {
					data: {
						timeZone: {
							timeZoneId: 'UTC',
						},
					},
				},
			},
		})
	);

	const DefaultComponent = (props) => (
		<Provider store={store}>
			<MemoryRouter>
				<DeleteDataSource
					actionRequestFn={jest.fn()}
					close={jest.fn()}
					dataSource={dataSource}
					deleteMessage="Test delete message"
					deletePhrase={Liferay.Language.get('remove-x')}
					entitiesCount={entitiesCount}
					groupId={groupId}
					id="123"
					open={open}
					pageActionConfirmationText="Confirm?"
					pageActionText={Liferay.Language.get('delete-data-source')}
					{...props}
				/>
			</MemoryRouter>
		</Provider>
	);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('successfullies validate if the user typed in the full string to delete the datasource', async () => {
		render(<DefaultComponent />);

		const deleteButton = screen
			.getByText('Delete Data Source')
			.closest('button');
		const confirmationInput = screen.getByTestId('confirmation-input');

		fireEvent.change(confirmationInput, {
			target: {value: `remove ${dataSource.name}`},
		});

		await waitFor(() => expect(deleteButton).not.toBeDisabled());
	});

	it('does not enable delete button if validation is unsuccessful', async () => {
		render(<DefaultComponent />);

		const deleteButton = screen
			.getByText('Delete Data Source')
			.closest('button');
		const confirmationInput = screen.getByTestId('confirmation-input');

		fireEvent.change(confirmationInput, {
			target: {value: 'wrong string'},
		});

		fireEvent.blur(confirmationInput);

		// Wait for Formik validation to settle

		await waitFor(() => expect(deleteButton).toBeDisabled());
	});

	it('opens the data source entities modal', async () => {
		render(<DefaultComponent />);

		fireEvent.click(screen.getByText('12 Segments'));

		expect(open).toHaveBeenCalledWith(
			'SEARCHABLE_ENTITIES_TABLE_MODAL',
			expect.any(Object)
		);
	});
});
