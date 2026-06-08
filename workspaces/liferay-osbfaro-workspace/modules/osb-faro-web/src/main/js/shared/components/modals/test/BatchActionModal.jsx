/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

import BatchActionModal from '../BatchActionModal';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider addTypename={false}>
				<BatchActionModal
					actionOptions={{
						actionCountString: '',
						options: [],
						optionsLabel: '',
					}}
					columns={[]}
					editableAttr=""
					fitContent={false}
					items={[]}
					onClose={jest.fn()}
					onSave={jest.fn()}
					title="Batch Action"
					{...props}
				/>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('BatchActionModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
