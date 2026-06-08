/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {Formik} from 'formik';
import React from 'react';
import * as data from '~/test/data';

import {FormSelectFieldInput} from '../SelectFieldInput';

jest.unmock('react-dom');

const Wrapper = ({children}) => (
	<Formik initialValues={{foo: ''}} onSubmit={() => {}}>
		{children}
	</Formik>
);

describe('SelectFieldInput', () => {
	it('renders', () => {
		const {getByPlaceholderText} = render(
			<Wrapper>
				<FormSelectFieldInput
					field={{name: 'foo'}}
					form={data.mockForm()}
					groupId="23"
					name="foo"
				/>
			</Wrapper>
		);

		expect(
			getByPlaceholderText(Liferay.Language.get('select-field'))
		).toBeInTheDocument();
	});

	it('renders with a label', () => {
		const label = 'bar';

		const {getByText} = render(
			<Wrapper>
				<FormSelectFieldInput
					field={{name: 'foo'}}
					form={data.mockForm()}
					groupId="23"
					label={label}
					name="foo"
				/>
			</Wrapper>
		);

		expect(getByText(label)).toBeTruthy();
	});
});
