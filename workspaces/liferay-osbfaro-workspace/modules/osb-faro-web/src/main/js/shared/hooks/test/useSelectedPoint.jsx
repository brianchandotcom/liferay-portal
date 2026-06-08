/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {useSelectedPoint} from '~/shared/hooks/useSelectedPoint';

jest.unmock('react-dom');

describe('useSelectedPoint', () => {
	it('does not have value on the first render', () => {
		let result = null;

		const Component = () => {
			result = useSelectedPoint();

			return null;
		};

		render(<Component />);

		jest.runAllTimers();

		expect(result.hasSelectedPoint).toBeFalse();
		expect(result.selectedPoint).toBeFalsy();
	});

	it('returns dispatched value', () => {
		let result = null;

		const Component = () => {
			result = useSelectedPoint();

			return null;
		};

		render(<Component />);

		const newPoint = 1;

		jest.runAllTimers();

		result.onPointSelect(newPoint);

		jest.runAllTimers();

		expect(result.hasSelectedPoint).toBeTrue();
		expect(result.selectedPoint).toBe(newPoint);
	});
});
