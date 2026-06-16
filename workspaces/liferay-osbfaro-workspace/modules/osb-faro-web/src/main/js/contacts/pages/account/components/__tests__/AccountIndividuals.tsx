import AccountIndividuals from '../AccountIndividuals';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';

jest.unmock('react-dom');

let lastFDSProps: any;

jest.mock('@liferay/frontend-data-set-web', () => ({
	...jest.requireActual('@liferay/frontend-data-set-web'),
	FrontendDataSet: (props: any) => {
		lastFDSProps = props;

		return <div data-testid='fds-component' id={props.id} />;
	}
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({channelId: '456', groupId: '23', id: 'acc-1'})
}));

describe('AccountIndividuals', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		lastFDSProps = undefined;
	});

	afterEach(cleanup);

	it('should render the card title', () => {
		render(<AccountIndividuals />);

		expect(screen.getByText('Account Individuals')).toBeInTheDocument();
	});

	it('should render the card description', () => {
		render(<AccountIndividuals />);

		expect(
			screen.getByText(
				'Lists all individuals associated with this account.'
			)
		).toBeInTheDocument();
	});

	it('should render the FrontendDataSet with the dataset id', () => {
		render(<AccountIndividuals />);

		expect(screen.getByTestId('fds-component')).toHaveAttribute(
			'id',
			'account-individuals-dataset'
		);
	});

	it('should call the account individuals api with the group and account ids', () => {
		render(<AccountIndividuals />);

		expect(lastFDSProps.apiURL).toBe(
			'/o/faro/contacts/23/account/acc-1/individuals?channelId=456'
		);
	});

	it('should configure the dataset with pagination shown', () => {
		render(<AccountIndividuals />);

		expect(lastFDSProps.showPagination).toBe(true);
		expect(lastFDSProps.pagination).toBeDefined();
	});

	it('should declare the four expected sortable columns', () => {
		render(<AccountIndividuals />);

		const fields = lastFDSProps.views[0].schema.fields;

		expect(fields).toHaveLength(4);
		expect(fields.map((field: any) => field.fieldName)).toEqual([
			'name',
			'department',
			'jobTitle',
			'lastActivityDate'
		]);
		expect(fields.every((field: any) => field.sortable === true)).toBe(
			true
		);
	});

	it('should label the columns from the language bundle', () => {
		render(<AccountIndividuals />);

		const fields = lastFDSProps.views[0].schema.fields;

		expect(fields[0].label).toBe('Individual Name');
		expect(fields[1].label).toBe('Department');
		expect(fields[2].label).toBe('Job Title');
		expect(fields[3].label).toBe('Last Active');
	});

	it('should wire the individual name renderer to the contacts individual route', () => {
		render(<AccountIndividuals />);

		const renderer =
			lastFDSProps.customDataRenderers.individualNameRenderer;

		const link = renderer({
			itemData: {id: 'individual-1'},
			value: 'Ada Lovelace'
		});

		expect(link.props.href).toContain(
			'/contacts/individuals/known-individuals/individual-1'
		);
		expect(link.props.children).toBe('Ada Lovelace');
	});

	it('should format the last active date', () => {
		render(<AccountIndividuals />);

		const renderer = lastFDSProps.customDataRenderers.lastActiveRenderer;

		const cell = renderer({value: '2026-05-01T10:23:00Z'});

		expect(cell.props.children).toBeTruthy();
	});
});
