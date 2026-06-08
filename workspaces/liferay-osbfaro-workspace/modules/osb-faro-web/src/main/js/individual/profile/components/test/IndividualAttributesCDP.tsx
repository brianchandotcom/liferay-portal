/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';

import IndividualAttributesCDP from '../IndividualAttributesCDP';

jest.unmock('react-dom');

describe('IndividualAttributesCDP', () => {
	const mockProperties = {
		birthDate: '2020-01-01T00:00:00.000Z',
		email: 'test@example.com',
		familyName: 'Bar',
		givenName: 'Foo',
		jobTitle: 'Developer',
		languageId: 'en_US',
		middleName: 'Baz',
		prefix: 'Mr.',
		screenName: 'foobar',
		suffix: 'Jr.',
		userId: '123',
		uuid: 'uuid-123',
	};

	it('renders', () => {
		const {container} = render(
			<IndividualAttributesCDP propertiesData={fromJS(mockProperties)} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders the empty state when showEmptyState is true', () => {
		const {getByText, queryByText} = render(
			<IndividualAttributesCDP
				propertiesData={fromJS(mockProperties)}
				showEmptyState
			>
				<div>empty state rendered</div>
			</IndividualAttributesCDP>
		);

		expect(getByText('empty state rendered')).toBeTruthy();
		expect(queryByText('screenName')).toBeNull();
	});

	it('correctlies format the birth date', () => {
		const {getByText} = render(
			<IndividualAttributesCDP propertiesData={fromJS(mockProperties)} />
		);

		expect(getByText('2020-01-01')).toBeTruthy();
	});

	it('displays the fallback dash for missing values', () => {
		const {getAllByText} = render(
			<IndividualAttributesCDP propertiesData={fromJS({})} />
		);

		const dashes = getAllByText('-');
		expect(dashes.length).toBeGreaterThan(0);
	});
});
