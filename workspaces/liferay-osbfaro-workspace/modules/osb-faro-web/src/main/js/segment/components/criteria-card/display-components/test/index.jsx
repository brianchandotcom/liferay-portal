/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {List, Map} from 'immutable';
import React from 'react';
import {RelationalOperators} from '~/segment/segment-editor/dynamic/utils/constants';
import {SegmentTypes} from '~/shared/util/constants';
import {Property} from '~/shared/util/records';
import * as data from '~/test/data';

import DisplayComponent from '../index';

jest.unmock('react-dom');

describe('DisplayComponent', () => {
	it.each`
		propertyKey     | entityName
		${'account'}    | ${'Account'}
		${'session'}    | ${'Session'}
		${'interest'}   | ${'Internet'}
		${'web'}        | ${'Web'}
		${'individual'} | ${'Individual'}
	`('renders $displayName for $propertyKey', ({entityName, propertyKey}) => {
		const mockCriterion = {
			operatorName: 'test-operator',
			propertyName: 'foo/bar',
			value: Map({
				criterionGroup: Map({
					items: List([
						Map({
							operatorName: RelationalOperators.EQ,
							propertyName: 'foo/bar',
							value: 'this is a description',
						}),
						Map({
							operatorName: RelationalOperators.EQ,
							propertyName: 'score',
							value: 'true',
						}),
					]),
				}),
				operator: RelationalOperators.GE,
				value: 32,
			}),
		};

		const mockProperty = data.getImmutableMock(
			Property,
			data.mockProperty,
			1,
			{
				entityName,
				label: 'description',
				name: 'foo/bar',
				propertyKey,
				type: 'text',
			}
		);

		const {container, getByText} = render(
			<DisplayComponent
				criterion={mockCriterion}
				property={mockProperty}
				segmentType={SegmentTypes.Batch}
			/>
		);

		expect(getByText(entityName)).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});
