/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import {mockPreferenceReq} from '~/test/graphql-data';
import mockStore from '~/test/mock-store';

import Form from '../index';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider addTypename={false} mocks={[mockPreferenceReq()]}>
				<Form
					initialValues={{
						foo: '',
					}}
					onSubmit={jest.fn()}
				>
					<Form.Form>
						<Form.DateInput
							label="Foo Date"
							name="foo"
							{...props}
						/>
					</Form.Form>
				</Form>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('DateInput', () => {
	const labelContent = 'Foo Date';

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with label', () => {
		const {queryByText} = render(<DefaultComponent label={labelContent} />);

		expect(queryByText(labelContent)).toBeTruthy();
	});

	it('renders as required', () => {
		const {queryByText} = render(
			<DefaultComponent label={labelContent} required />
		);

		expect(queryByText(labelContent).closest('label')).toHaveClass(
			'required'
		);
	});
});
