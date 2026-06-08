/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import AddReport from '../AddReport';

jest.unmock('react-dom');

describe('AddReport', () => {
	it('renders without analytics-add-report-empty-dashboard class', () => {
		const {container} = render(<AddReport />);

		expect(
			container.querySelector('.analytics-add-report-empty-dashboard')
		).toBeFalsy();
	});

	it('renders with analytics-add-report-empty-dashboard class', () => {
		const {container} = render(<AddReport isEmptyDashboard />);

		expect(
			container.querySelector('.analytics-add-report-empty-dashboard')
		).toBeTruthy();
	});

	it('renders a form AddReport', () => {
		const {container, getByText} = render(<AddReport />);

		fireEvent.click(getByText('Add Report'));

		expect(container).toMatchSnapshot();
	});

	it('renders an empty state when closeReport method is called', () => {
		const {container, getByText} = render(<AddReport isEmptyDashboard />);

		fireEvent.click(getByText('Add Report'));
		fireEvent.click(getByText('Cancel'));

		expect(
			container.querySelector('.analytics-add-report-empty-dashboard')
		).toBeTruthy();
	});

	it('is false when some form field has not filled in', () => {
		const {getByText} = render(<AddReport />);

		fireEvent.click(getByText('Add Report'));

		expect(getByText('Save')).toBeDisabled();
	});

	it('is true when all form field has filled in', () => {
		const {getByLabelText, getByText} = render(<AddReport />);

		fireEvent.click(getByText('Add Report'));

		fireEvent.input(getByLabelText('Report Name'), {
			target: {value: 'jacksontesting'},
		});
		fireEvent.change(getByLabelText('Metric'), {
			target: {value: 'clicksMetric'},
		});

		expect(getByLabelText('Report Name').value).toBe('jacksontesting');
		expect(getByLabelText('Metric').value).toBe('clicksMetric');

		expect(getByText('Save')).not.toBeDisabled();
	});

	it('closes form when the user saves the report', () => {
		const {getByLabelText, getByText} = render(
			<AddReport onGetReport={jest.fn()} />
		);

		fireEvent.click(getByText('Add Report'));

		fireEvent.input(getByLabelText('Report Name'), {
			target: {value: 'jacksontesting'},
		});
		fireEvent.change(getByLabelText('Metric'), {
			target: {value: 'clicksMetric'},
		});

		fireEvent.click(getByText('Save'));

		expect(getByText('Add Report')).toBeTruthy();
	});

	it('closes form when the user cancels the report addition', () => {
		const {getByText} = render(<AddReport onGetReport={jest.fn()} />);

		fireEvent.click(getByText('Add Report'));
		fireEvent.click(getByText('Cancel'));

		expect(getByText('Add Report')).toBeTruthy();
	});
});
