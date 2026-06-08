/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Property} from '~/shared/util/records';

import {RelationalOperators} from '../../utils/constants';
import {createCustomValueMap} from '../../utils/custom-inputs';
import InterestBooleanInput from '../InterestBooleanInput';

jest.unmock('react-dom');

describe('InterestBooleanInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<InterestBooleanInput
				property={new Property({entityName: 'Foo Entity'})}
				value={createCustomValueMap([
					{
						key: 'criterionGroup',
						value: [
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'name',
								value: 'foo interest',
							},
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'score',
								value: 'true',
							},
						],
					},
				])}
			/>
		);
		fireEvent.click(getByText('is'));

		expect(getAllByText('is')[1]).toBeTruthy();
		expect(getByText('is not')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});
