/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

import DeleteConfirmationModal from '../DeleteConfirmationModal';

jest.unmock('react-dom');

const DefaultComponent = ({children, ...props}) => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider addTypename={false}>
				<DeleteConfirmationModal
					deleteConfirmationText="delete me"
					disabled={false}
					onClose={jest.fn()}
					onSubmit={jest.fn()}
					{...props}
				>
					{children}
				</DeleteConfirmationModal>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('DeleteConfirmationModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with custom title and message', () => {
		render(
			<DefaultComponent title="Custom Title">
				Custom Delete Message
			</DefaultComponent>
		);

		expect(screen.getByText('Custom Title')).toBeInTheDocument();
		expect(screen.getByText('Custom Delete Message')).toBeInTheDocument();
	});
});
