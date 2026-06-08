/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {mockIndividual, mockSegment} from '~/test/data';

import Avatar from '../Avatar';

jest.unmock('react-dom');

describe('Avatar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Avatar entity={mockIndividual()} />);
		expect(container).toMatchSnapshot();
	});

	it('renders with a first name initial', () => {
		const {getByText} = render(
			<Avatar
				entity={mockIndividual(0, {
					familyName: undefined,
					givenName: 'foo',
				})}
			/>
		);

		expect(getByText('F')).toBeTruthy();
	});

	it('renders with a first and last name', () => {
		const {getByText} = render(
			<Avatar
				entity={mockIndividual(0, {
					familyName: 'bar',
					givenName: 'foo',
				})}
			/>
		);

		expect(getByText('FB')).toBeTruthy();
	});

	it('renders if null is used as the first name', () => {
		const {container} = render(
			<Avatar
				entity={mockIndividual(0, {familyName: null, givenName: null})}
			/>
		);
		expect(container).toBeTruthy();
	});

	it('renders with a specific size', () => {
		const {container} = render(
			<Avatar entity={mockIndividual()} size="xl" />
		);
		expect(container.querySelector('.sticker-xl')).toBeTruthy();
	});

	it('renders with a specific color id', () => {
		const {container} = render(<Avatar entity={mockIndividual(4)} />);
		expect(container.querySelector('.sticker-chartOrange')).toBeTruthy();
	});

	it('renders with an image', () => {
		const {container} = render(
			<Avatar
				entity={mockIndividual(0, {
					image: 'http://i.imgur.com/G5pfP.jpg',
				})}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders an icon if a segment is passed', () => {
		const {container} = render(<Avatar entity={mockSegment()} />);
		expect(
			container.querySelector('.lexicon-icon-faro_contacts_segments')
		).toBeTruthy();
	});
});
