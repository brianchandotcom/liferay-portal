/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import {
	RechartsTooltip,
	getAxisTickText,
	getTextWidth,
	getYAxisLabel,
	getYAxisWidth,
} from '../recharts';

jest.unmock('react-dom');

describe('Recharts Util', () => {
	describe('getTextWidth', () => {
		it('returns text width', () => {

			// jest-canvas-mock v2: measureText returns {width: text.length}, TEXT_PADDING=4
			// 'test'.length === 4, so Math.ceil(4) + 4 = 8

			expect(getTextWidth('test')).toEqual(8);
		});
	});

	describe('getAxisTickText', () => {
		it('returns a function', () => {
			expect(getAxisTickText('x')).toBeFunction();
		});

		it('renders when returned function is called', () => {
			const {container} = render(
				getAxisTickText('x')({
					payload: {offset: 2, value: 4},
					textAnchor: 'middle',
					x: 12,
					y: 12,
				})
			);

			const textEl = container.querySelector('text');

			expect(textEl).toBeInTheDocument();
			expect(textEl).toHaveAttribute('text-anchor', 'middle');
		});
	});

	describe('RechartsTooltip', () => {
		it('renders', () => {
			const {container, getByText} = render(
				<RechartsTooltip
					dateTitle="12-12-12"
					rows={[{label: 'test', value: 123}]}
					title="Test Title"
				/>
			);

			expect(
				container.querySelector('.bb-tooltip-container')
			).toBeInTheDocument();
			expect(getByText('Test Title')).toBeInTheDocument();
			expect(getByText('test')).toBeInTheDocument();
		});
	});

	describe('getYAxisLabel', () => {
		it('returns a function', () => {
			expect(getYAxisLabel('Test')).toBeFunction();
		});

		it('renders when returned function is called', () => {
			const {container} = render(
				getYAxisLabel('Test')({
					viewBox: {
						height: 14,
						width: 26,
						x: 12,
						y: 24,
					},
				})
			);

			const textEl = container.querySelector('text');

			expect(textEl).toBeInTheDocument();
			expect(textEl).toHaveTextContent('Test');
		});
	});

	describe('getYAxisWidth', () => {
		it('maxes y-axis width', () => {

			// jest-canvas-mock: measureText returns {width: text.length}
			// 'test test'.length = 9, Math.ceil(9) + 4 = 13
			// 'meow'.length = 4, Math.ceil(4) + 4 = 8
			// minWidth default = 30, max(13, 8, 30) = 30

			expect(
				getYAxisWidth([{title: 'test test'}, {title: 'meow'}], 'title')
			).toBe(30);
		});

		it('minWidths for y-axis width', () => {
			const minWidth = 60;

			expect(getYAxisWidth([{title: 'test'}], 'title', minWidth)).toBe(
				minWidth
			);
		});
	});
});
