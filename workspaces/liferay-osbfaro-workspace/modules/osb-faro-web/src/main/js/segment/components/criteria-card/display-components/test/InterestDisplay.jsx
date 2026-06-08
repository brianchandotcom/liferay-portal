/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {List, Map} from 'immutable';
import React from 'react';
import {
	CustomFunctionOperators,
	PropertyTypes,
	RelationalOperators,
} from '~/segment/segment-editor/dynamic/utils/constants';
import {Property} from '~/shared/util/records';
import * as data from '~/test/data';

import InterestDisplay from '../InterestDisplay';

jest.unmock('react-dom');

describe('InterestDisplay', () => {
	const mockCriterion = {
		operatorName: CustomFunctionOperators.InterestsFilter,
		propertyName: 'name',
		value: Map({
			criterionGroup: Map({
				items: List([
					Map({
						operatorName: RelationalOperators.EQ,
						propertyName: 'name',
						value: 'Tests',
					}),
					Map({
						operatorName: RelationalOperators.EQ,
						propertyName: 'score',
						value: 'true',
					}),
				]),
			}),
		}),
	};

	const mockProperty = data.getImmutableMock(Property, data.mockProperty, 1, {
		entityName: 'Individual',
		label: 'name',
		name: 'name',
		propertykey: 'interest',
		type: PropertyTypes.Interest,
	});

	it('renders', () => {
		const {container} = render(
			<InterestDisplay
				criterion={mockCriterion}
				property={mockProperty}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
