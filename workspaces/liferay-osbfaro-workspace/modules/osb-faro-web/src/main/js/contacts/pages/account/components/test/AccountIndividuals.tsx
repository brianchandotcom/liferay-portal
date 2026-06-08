/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import AccountIndividuals from '../AccountIndividuals';

jest.unmock('react-dom');

let lastFDSProps: any;

jest.mock('@liferay/frontend-data-set-web', () => ({
	...jest.requireActual('@liferay/frontend-data-set-web'),
	FrontendDataSet: (props: any) => {
		lastFDSProps = props;

		return <div data-testid="fds-component" id={props.id} />;
	},
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({channelId: '456', groupId: '23', id: 'acc-1'}),
}));

describe('AccountIndividuals', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		lastFDSProps = undefined;
	});

	afterEach(cleanup);

	it('renders the card title', () => {
		render(<AccountIndividuals />);

		expect(screen.getByText('Account Individuals')).toBeInTheDocument();
	});

	it('renders the card description', () => {
		render(<AccountIndividuals />);

		expect(
			screen.getByText(
				'Lists all individuals associated with this account.'
			)
		).toBeInTheDocument();
	});

	it('renders the FrontendDataSet with the dataset id', () => {
		render(<AccountIndividuals />);

		expect(screen.getByTestId('fds-component')).toHaveAttribute(
			'id',
			'account-individuals-dataset'
		);
	});

	it('calls the account individuals api with the group and account ids', () => {
		render(<AccountIndividuals />);

		expect(lastFDSProps.apiURL).toBe(
			'/o/faro/contacts/23/account/acc-1/individuals?channelId=456'
		);
	});

	it('configures the dataset with pagination shown', () => {
		render(<AccountIndividuals />);

		expect(lastFDSProps.showPagination).toBe(true);
		expect(lastFDSProps.pagination).toBeDefined();
	});

	it('declares the four expected sortable columns', () => {
		render(<AccountIndividuals />);

		const fields = lastFDSProps.views[0].schema.fields;

		expect(fields).toHaveLength(4);
		expect(fields.map((field: any) => field.fieldName)).toEqual([
			'name',
			'department',
			'jobTitle',
			'lastActivityDate',
		]);
		expect(fields.every((field: any) => field.sortable === true)).toBe(
			true
		);
	});

	it('labels the columns from the language bundle', () => {
		render(<AccountIndividuals />);

		const fields = lastFDSProps.views[0].schema.fields;

		expect(fields[0].label).toBe('Individual Name');
		expect(fields[1].label).toBe('Department');
		expect(fields[2].label).toBe('Job Title');
		expect(fields[3].label).toBe('Last Active');
	});

	it('wires the individual name renderer to the contacts individual route', () => {
		render(<AccountIndividuals />);

		const renderer =
			lastFDSProps.customDataRenderers.individualNameRenderer;

		const link = renderer({
			itemData: {id: 'individual-1'},
			value: 'Ada Lovelace',
		});

		expect(link.props.href).toContain(
			'/contacts/individuals/known-individuals/individual-1'
		);
		expect(link.props.children).toBe('Ada Lovelace');
	});

	it('formats the last active date', () => {
		render(<AccountIndividuals />);

		const renderer = lastFDSProps.customDataRenderers.lastActiveRenderer;

		const cell = renderer({value: '2026-05-01T10:23:00Z'});

		expect(cell.props.children).toBeTruthy();
	});
});
