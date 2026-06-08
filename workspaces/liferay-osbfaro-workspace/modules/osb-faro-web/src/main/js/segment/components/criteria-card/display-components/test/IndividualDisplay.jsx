/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {ReferencedObjectsProvider} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {
	PropertyTypes,
	RelationalOperators,
} from '~/segment/segment-editor/dynamic/utils/constants';
import {Property, Segment} from '~/shared/util/records';
import * as data from '~/test/data';

import IndividualDisplay from '../IndividualDisplay';

jest.unmock('react-dom');

describe('IndividualDisplay', () => {
	const propertyName = 'demographics/givenName/value';

	const mockCriterion = {
		operatorName: RelationalOperators.EQ,
		propertyName,
		value: 'Test',
	};

	const mockProperty = data.getImmutableMock(Property, data.mockProperty, 1, {
		entityName: 'Individual',
		label: 'name',
		name: propertyName,
		propertykey: 'individual',
		type: PropertyTypes.Text,
	});

	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<IndividualDisplay
				criterion={mockCriterion}
				property={mockProperty}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ a knownType', () => {
		const criterion = {...mockCriterion};

		criterion.value = null;

		const {getByText} = render(
			<IndividualDisplay criterion={criterion} property={mockProperty} />
		);

		expect(getByText('is unknown')).toBeTruthy();
	});

	it('renders w/ a duration type', () => {
		const criterion = {...mockCriterion};

		criterion.value = null;

		const {getByText} = render(
			<IndividualDisplay
				criterion={{
					operatorName: RelationalOperators.GT,
					propertyName: 'demographics/duration/value',
					value: 3661000,
				}}
				property={data.getImmutableMock(
					Property,
					data.mockProperty,
					1,
					{
						entityName: 'Individual',
						label: 'Duration',
						name: 'demographics/duration/value',
						propertykey: 'duration',
						type: PropertyTypes.Duration,
					}
				)}
			/>
		);

		expect(getByText('01:01:01')).toBeTruthy();
	});

	it('renders w/ a SelectInput type', () => {
		const criterion = {...mockCriterion};

		criterion.value = null;

		const mockSegment = data.getImmutableMock(
			Segment,
			data.mockSegment,
			0,
			{
				referencedObjects: {
					roles: {
						123123: {id: '123123', name: 'Admin'},
					},
				},
			}
		);

		const {getByText} = render(
			<ReferencedObjectsProvider segment={mockSegment}>
				<IndividualDisplay
					criterion={{
						operatorName: RelationalOperators.EQ,
						propertyName: 'roleIds',
						value: '123123',
					}}
					property={data.getImmutableMock(
						Property,
						data.mockProperty,
						1,
						{
							entityName: 'Individual',
							label: 'Role',
							name: 'roleIds',
							type: PropertyTypes.SelectText,
						}
					)}
				/>
			</ReferencedObjectsProvider>
		);

		expect(getByText("'Admin'")).toBeTruthy();
	});
});
