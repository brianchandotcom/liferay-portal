/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import ReactDOM from 'react-dom';

import TrackedBehaviorsList from '../TrackedBehaviorsList';

jest.unmock('react-dom');

ReactDOM.createPortal = jest.fn();

describe('TrackedBehaviorsList', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<TrackedBehaviorsList authorized />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('renders with disabled restrict access checkboxes if the user is not authorized', () => {
		const {container} = render(
			<TrackedBehaviorsList authorized={false} groupId="23" />
		);

		jest.runAllTimers();

		expect(
			container.querySelectorAll('input[type=checkbox]:disabled').length
		).toEqual(4);
	});

	it('renders the restrict access checkbbox in a checked state if the behavior has restricted access', () => {
		const {container} = render(
			<TrackedBehaviorsList authorized groupId="23" />
		);

		jest.runAllTimers();

		expect(
			container.querySelectorAll('input[type=checkbox]')[0].checked
		).toBeTrue();
	});

	it('does not contain a search bar or filter capability', () => {
		const {container} = render(
			<TrackedBehaviorsList authorized groupId="23" />
		);

		jest.runAllTimers();

		expect(container.querySelector('.input-group.search')).toBeNull();
		expect(container.querySelector('.filter-and-order-root')).toBeNull();
	});
});
