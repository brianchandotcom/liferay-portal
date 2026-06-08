/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {ReferencedObjectsProvider} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {Segment} from '~/shared/util/records';
import * as data from '~/test/data';

import CriteriaCard from '../index';

jest.unmock('react-dom');

describe('CriteriaCard', () => {
	const innerHeight = window.innerHeight;

	afterEach(() => {
		cleanup();

		window.innerHeight = innerHeight;
	});

	const mockSegment = data.getImmutableMock(Segment, data.mockSegment, 0, {
		referencedObjects: {
			fieldMappings: {
				individual: {
					demographics: {
						firstName: {
							context: 'demographics',
							id: null,
							name: 'firstName',
							ownerType: 'individual',
							propertyKey: '',
							rawType: 'text',
							type: 'text',
						},
					},
				},
			},
		},
	});

	it('renders', () => {
		const {container} = render(
			<ReferencedObjectsProvider segment={mockSegment}>
				<CriteriaCard
					criteriaString="demographics/firstName/value eq 'Test'"
					segment={mockSegment}
				/>
			</ReferencedObjectsProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ anonymous label', () => {
		const {queryByText} = render(
			<ReferencedObjectsProvider segment={mockSegment}>
				<CriteriaCard
					criteriaString="demographics/name/value eq 'Test'"
					includeAnonymousUsers
					segment={mockSegment}
				/>
			</ReferencedObjectsProvider>
		);

		expect(queryByText('Includes Anonymous Individuals')).toBeTruthy();
	});
});
