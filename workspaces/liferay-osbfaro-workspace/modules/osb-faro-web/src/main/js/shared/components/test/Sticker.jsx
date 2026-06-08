/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Sticker, {getDisplayForId, hashCode} from '../Sticker';

jest.unmock('react-dom');

describe('Sticker', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Sticker symbol="file" />);
		expect(container).toMatchSnapshot();
	});
});

describe('getDisplayForId', () => {
	it('returns a display type for a given number or string', () => {
		expect(getDisplayForId(1234)).toBe('chartSeaGreen');
		expect(getDisplayForId('fooBar')).toBe('chartLimeGreen');
	});

	it('accepts a custom array of displays', () => {
		const displays = ['customDisplayFoo', 'customDisplayBar'];

		expect(getDisplayForId(1, displays)).toBe('customDisplayBar');
		expect(getDisplayForId(2, displays)).toBe('customDisplayFoo');
	});

	it('returns a result if given an invalid id parameter', () => {
		expect(getDisplayForId(undefined)).toBe('chartBlue');
		expect(getDisplayForId(null)).toBe('chartBlue');
	});
});

describe('hashCode', () => {
	it('returns a unique number for a given string', () => {
		expect(hashCode('foo')).toBe(324);
		expect(hashCode('bar')).toBe(309);
		expect(hashCode('baz')).toBe(317);
	});
});
