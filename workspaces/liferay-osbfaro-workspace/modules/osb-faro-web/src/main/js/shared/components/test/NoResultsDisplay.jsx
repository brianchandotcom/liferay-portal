/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import NoResultsDisplay, {getFormattedTitle} from '../NoResultsDisplay';

jest.unmock('react-dom');

describe('NoResultsDisplay', () => {
	afterEach(cleanup);

	it('renders component', () => {
		const {container} = render(<NoResultsDisplay />);

		expect(container).toMatchSnapshot();
	});

	it('renders component with primary color', () => {
		const {container} = render(<NoResultsDisplay primary />);

		expect(container.querySelector('.no-results-root')).toHaveClass(
			'no-results-primary'
		);
	});

	it('renders component with description', () => {
		const description = 'No results with description';

		const {getByText} = render(
			<NoResultsDisplay description={description} />
		);

		expect(getByText(description)).toBeTruthy();
	});

	it('renders component with title', () => {
		const title = 'No results with title';

		const {getByText} = render(<NoResultsDisplay title={title} />);

		expect(getByText(title)).toBeTruthy();
	});

	it('renders component with icon', () => {
		const {container} = render(
			<NoResultsDisplay icon={{symbol: 'home'}} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders component with icon without border', () => {
		const {container} = render(
			<NoResultsDisplay icon={{border: false, symbol: 'home'}} />
		);

		expect(container.querySelector('.no-results-icon-border')).toBeNull();
	});

	it('renders component with icon and size sm', () => {
		const {container} = render(
			<NoResultsDisplay icon={{size: 'sm', symbol: 'home'}} />
		);

		expect(
			container.querySelector('.lexicon-icon-home.icon-size-sm')
		).toBeTruthy();
	});
});

describe('NoResultsDisplay - getFormattedTitle util', () => {
	it('returns formatted title', () => {
		expect(getFormattedTitle()).toEqual('There are no items found.');
	});

	it('returns formatted title passing a title', () => {
		expect(
			getFormattedTitle(
				undefined,
				Liferay.Language.get(
					'there-are-no-x-that-match-the-selected-criteria'
				)
			)
		).toEqual('There are no items that match the selected criteria.');
	});

	it('returns formatted title passing a name', () => {
		expect(getFormattedTitle(Liferay.Language.get('segments'))).toEqual(
			'There are no Segments found.'
		);
	});
});
