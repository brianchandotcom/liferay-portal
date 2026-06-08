/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import TooltipTemplate from '../TooltipTemplate';

jest.unmock('react-dom');

describe('TooltipTemplate', () => {
	it('renders', () => {
		const {container} = render(
			<TooltipTemplate>TooltipTemplate</TooltipTemplate>
		);

		expect(container).toMatchSnapshot();
	});
});

describe('TooltipTemplate.Body', () => {
	it('renders', () => {
		const {container} = render(
			<TooltipTemplate.Body>Body</TooltipTemplate.Body>
		);

		expect(container).toMatchSnapshot();
	});
});

describe('TooltipTemplate.Column', () => {
	it('renders', () => {
		const {container} = render(
			<TooltipTemplate.Column>Column</TooltipTemplate.Column>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as truncated', () => {
		const {container} = render(
			<TooltipTemplate.Column truncated>Column</TooltipTemplate.Column>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ right alignment', () => {
		const {container} = render(
			<TooltipTemplate.Column alignment="right">
				Column
			</TooltipTemplate.Column>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ weight of light', () => {
		const {container} = render(
			<TooltipTemplate.Column weight="light">
				Column
			</TooltipTemplate.Column>
		);

		expect(container).toMatchSnapshot();
	});
});

describe('TooltipTemplate.Header', () => {
	it('renders', () => {
		const {container} = render(
			<TooltipTemplate.Header>Header</TooltipTemplate.Header>
		);

		expect(container).toMatchSnapshot();
	});
});

describe('TooltipTemplate.Row', () => {
	it('renders', () => {
		const {container} = render(
			<TooltipTemplate.Row>Row</TooltipTemplate.Row>
		);

		expect(container).toMatchSnapshot();
	});
});
