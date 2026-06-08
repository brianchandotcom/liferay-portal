/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import MetricValue from '../MetricValue';
import {MetricType} from '../metrics';

jest.unmock('react-dom');

describe('MetricValue', () => {
	it('renders the component', () => {
		const {container} = render(<MetricValue value="100K" />);

		expect(container).toMatchSnapshot();
	});

	it('renders the component with number type', () => {
		const {container} = render(
			<MetricValue type={MetricType.Number} value="100K" />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders the component with percentage ', () => {
		const {container} = render(
			<MetricValue type={MetricType.Percentage} value="100%" />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders the component with time ', () => {
		const {container} = render(
			<MetricValue type={MetricType.Time} value="12m 40s" />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders the component with ratings ', () => {
		const {container} = render(
			<MetricValue type={MetricType.Ratings} value="10/10" />
		);

		expect(container).toMatchSnapshot();
	});
});
