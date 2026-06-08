/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import Form from '~/shared/components/form';

import BasicSettings from '../BasicSettings';

jest.unmock('react-dom');

describe('BasicSettings', () => {
	it('renders', () => {
		const {container} = render(
			<Form initialValues={{name: ''}}>
				{({errors}) => (
					<Form.Form>
						<BasicSettings
							errors={errors}
							name="Test"
							onSetDisabled={jest.fn()}
						/>
					</Form.Form>
				)}
			</Form>
		);

		expect(container).toMatchSnapshot();
	});
});
