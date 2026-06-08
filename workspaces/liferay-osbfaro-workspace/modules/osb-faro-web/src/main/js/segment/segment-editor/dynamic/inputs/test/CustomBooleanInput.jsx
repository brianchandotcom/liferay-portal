/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Property} from '~/shared/util/records';

import CustomBooleanInput from '../CustomBooleanInput';

jest.unmock('react-dom');

describe('CustomBooleanInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<CustomBooleanInput
				displayValue="Do Not Call"
				operatorRenderer={() => <div>operator</div>}
				property={new Property({entityName: 'Organization'})}
				value={fromJS({
					criterionGroup: {
						conjunctionName: 'and',
						groupId: '123',
						items: [
							{
								operatorName: 'eq',
								propertyName: 'custom/doNotCall/value',
								value: 'true',
							},
						],
					},
				})}
			/>
		);
		fireEvent.click(getByText('True'));

		expect(getAllByText('True')[1]).toBeTruthy();
		expect(getByText('False')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});
