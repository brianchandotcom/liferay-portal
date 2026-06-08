/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {mockEventAttributeDefinition} from '~/test/data';

import {RelationalOperators} from '../../../../utils/constants';
import AttributeConjunctionInput from '../index';

jest.unmock('react-dom');

describe('AttributeConjunctionInput', () => {
	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<AttributeConjunctionInput
				attributes={range(4).map((index) =>
					mockEventAttributeDefinition(index)
				)}
				conjunctionCriterion={{
					operatorName: RelationalOperators.EQ,
					propertyName: 'attribute/1',
					value: 'test value',
				}}
				onChange={jest.fn()}
				touched={{attribute: true, attributeValue: true}}
				valid={{attribute: true, attributeValue: true}}
			/>
		);
		fireEvent.click(getAllByText('displayName-1')[0]);

		expect(getByText('displayName-0')).toBeTruthy();
		expect(getAllByText('displayName-1')[1]).toBeTruthy();
		expect(getByText('displayName-2')).toBeTruthy();
		expect(getByText('displayName-3')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
