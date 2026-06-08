/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {ReferencedObjectsProvider} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {
	CustomFunctionOperators,
	FunctionalOperators,
	PropertyTypes,
	RelationalOperators,
	isKnown,
} from '~/segment/segment-editor/dynamic/utils/constants';
import {createCustomValueMap} from '~/segment/segment-editor/dynamic/utils/custom-inputs';
import {Property, Segment} from '~/shared/util/records';
import * as data from '~/test/data';

import OrganizationDisplay from '../OrganizationDisplay';

jest.unmock('react-dom');

const mockCriterion = {
	operatorName: CustomFunctionOperators.OrganizationsFilter,
	propertyName: 'name',
	value: createCustomValueMap([
		{
			key: 'criterionGroup',
			value: [
				{
					operatorName: FunctionalOperators.Contains,
					propertyName: 'name',
					value: 'foo organization name',
				},
			],
		},
	]),
};

const mockProperty = {
	entityName: 'Organization',
	label: 'Name',
	name: 'name',
	propertykey: 'organization',
	type: PropertyTypes.OrganizationText,
};

const defaultProps = {
	criterion: mockCriterion,
	property: data.getImmutableMock(
		Property,
		data.mockProperty,
		1,
		mockProperty
	),
};

const mockSegment = data.getImmutableMock(Segment, data.mockSegment, 0, {
	referencedObjects: {
		organizations: {
			123: data.mockGraphqlOrganization('123', {
				name: 'Foo Organization',
			}),
		},
	},
});

const DefaultComponent = (props) => (
	<ReferencedObjectsProvider segment={mockSegment}>
		<OrganizationDisplay {...defaultProps} {...props} />
	</ReferencedObjectsProvider>
);

describe('OrganizationDisplay', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders as a Date Time criteria', () => {
		const {container} = render(
			<DefaultComponent
				criterion={{
					...mockCriterion,
					propertyName: 'dateModified',
					value: createCustomValueMap([
						{
							key: 'criterionGroup',
							value: [
								{
									operatorName: RelationalOperators.EQ,
									propertyName: 'dateModified',
									value: '2020-02-11T22:16:41.799Z',
								},
							],
						},
					]),
				}}
				property={data.getImmutableMock(
					Property,
					data.mockProperty,
					1,
					{
						...mockProperty,
						label: 'Date Modified',
						name: 'dateModified',
						type: PropertyTypes.OrganizationDateTime,
					}
				)}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as a known criteria', () => {
		const {container} = render(
			<DefaultComponent
				criterion={{
					...mockCriterion,
					value: createCustomValueMap([
						{
							key: 'criterionGroup',
							value: [
								{
									operatorName: isKnown,
									propertyName: 'dateModified',
									value: null,
								},
							],
						},
					]),
				}}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as a referenced entity criteria', () => {
		const {container} = render(
			<DefaultComponent
				criterion={{
					...mockCriterion,
					propertyName: 'organization',
					value: createCustomValueMap([
						{
							key: 'criterionGroup',
							value: [
								{
									operatorName: RelationalOperators.EQ,
									propertyName: 'organization',
									value: '123',
								},
							],
						},
					]),
				}}
				property={data.getImmutableMock(
					Property,
					data.mockProperty,
					1,
					{
						...mockProperty,
						label: 'Organization',
						name: 'organization',
						type: PropertyTypes.OrganizationSelectText,
					}
				)}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
