import * as API from 'shared/api';
import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, fireEvent, render, screen, waitFor} from '@testing-library/react';
import {Formik} from 'formik';
import {MemoryRouter} from 'react-router';
import {modalTypes} from 'shared/actions/modals';
import {Provider} from 'react-redux';
import {SegmentCategories, SegmentTypes} from 'shared/util/constants';
import {Toolbar} from '../Toolbar';

jest.unmock('react-dom');

const renderToolbar = (props = {}, store = mockStore()) =>
	render(
		<Provider store={store}>
			<MemoryRouter>
				<Formik initialValues={{includeAnonymousUsers: false}}>
					<Toolbar
						channelId='321'
						criteria={data.mockNewCriteria(1, {valid: false})}
						groupId='123'
						segmentCategory={SegmentCategories.Individual}
						segmentType={SegmentTypes.Batch}
						{...props}
					/>
				</Formik>
			</MemoryRouter>
		</Provider>
	);

describe('Toolbar', () => {
	afterEach(() => {
		jest.clearAllMocks();

		cleanup();
	});

	it('should render the new segment title and actions', () => {
		renderToolbar();

		expect(
			screen.getByRole('heading', {level: 1, name: /new segment/i})
		).toBeInTheDocument();
		expect(screen.getByRole('link', {name: /cancel/i})).toBeInTheDocument();
		expect(screen.getByRole('button', {name: /save segment/i})).toHaveAttribute(
			'type',
			'submit'
		);
	});

	it('should render the edit segment title and the delete action when editing', () => {
		const onDeleteSegment = jest.fn();

		renderToolbar({id: '1', onDeleteSegment});

		expect(
			screen.getByRole('heading', {level: 1, name: /edit segment/i})
		).toBeInTheDocument();

		const deleteButton = screen.getByRole('button', {
			name: /delete segment/i
		});

		expect(deleteButton).toHaveAttribute('type', 'button');

		fireEvent.click(deleteButton);

		expect(onDeleteSegment).toHaveBeenCalledTimes(1);
	});

	it('should render the anonymous toggle and total members only for batch segments', () => {
		const {unmount} = renderToolbar();

		expect(
			screen.getByRole('switch', {name: /include anonymous/i})
		).toBeInTheDocument();
		expect(screen.getByText(/total members/i)).toBeInTheDocument();

		unmount();

		renderToolbar({segmentType: SegmentTypes.RealTime});

		expect(screen.queryByRole('switch')).not.toBeInTheDocument();
		expect(screen.queryByText(/total members/i)).not.toBeInTheDocument();
	});

	it('should open the accounts modal for account segments', async () => {
		API.accounts.searchByFilter.mockReturnValue(
			Promise.resolve({items: [], totalCount: 1})
		);

		const store = mockStore();

		const dispatchSpy = jest.spyOn(store, 'dispatch');

		renderToolbar(
			{
				criteria: data.mockNewCriteria(1, {valid: true}),
				segmentCategory: SegmentCategories.Account
			},
			store
		);

		const previewButton = screen.getByTestId('preview-criteria-button');

		await waitFor(() => expect(previewButton).toBeEnabled());

		fireEvent.click(previewButton);

		expect(dispatchSpy).toHaveBeenCalledWith(
			expect.objectContaining({
				payload: expect.objectContaining({
					props: expect.objectContaining({
						entityLabel: 'Accounts',
						title: 'Segment Accounts'
					}),
					type: modalTypes.SEARCHABLE_ENTITIES_TABLE_MODAL
				})
			})
		);
	});

	it('should open the segment membership modal w/ anonymous individuals and their accounts for individual segments', async () => {
		API.individuals.search.mockReturnValue(
			Promise.resolve({items: [], total: 1})
		);

		const store = mockStore();

		const dispatchSpy = jest.spyOn(store, 'dispatch');

		renderToolbar(
			{
				criteria: data.mockNewCriteria(1, {valid: true}),
				criteriaString: 'filter',
				includeAnonymousUsers: true
			},
			store
		);

		const previewButton = screen.getByTestId('preview-criteria-button');

		await waitFor(() => expect(previewButton).toBeEnabled());

		fireEvent.click(previewButton);

		expect(dispatchSpy).toHaveBeenCalledWith(
			expect.objectContaining({
				payload: expect.objectContaining({
					props: expect.objectContaining({
						columns: [
							expect.objectContaining({
								accessor: 'name',
								className: 'w-50'
							}),
							expect.objectContaining({
								accessor: 'accountName',
								className: 'w-50',
								label: 'Account Name'
							})
						],
						entityLabel: 'Individuals',
						title: 'Segment Membership'
					}),
					type: modalTypes.SEARCHABLE_ENTITIES_TABLE_MODAL
				})
			})
		);

		const {dataSourceFn} = dispatchSpy.mock.calls.find(
			([action]) =>
				action.payload?.type === modalTypes.SEARCHABLE_ENTITIES_TABLE_MODAL
		)[0].payload.props;

		API.individuals.search.mockClear();

		dataSourceFn({delta: 10, page: 1});

		expect(API.individuals.search).toHaveBeenCalledWith(
			expect.objectContaining({
				channelId: '321',
				delta: 10,
				filter: 'filter',
				groupId: '123',
				includeAnonymousUsers: true,
				page: 1
			})
		);
	});

	it('should render w/ preview button disabled if criteria is valid and total members count is equal to 0', async () => {
		API.individuals.search.mockReturnValue(Promise.resolve({total: 0}));

		renderToolbar({criteria: data.mockNewCriteria(1, {valid: true})});

		await waitFor(() => expect(API.individuals.search).toHaveBeenCalled());

		expect(screen.getByTestId('preview-criteria-button')).toBeDisabled();
	});

	it('should render w/ preview button disabled if criteria is not valid', () => {
		renderToolbar();

		expect(screen.getByTestId('preview-criteria-button')).toBeDisabled();
	});

	it('should render w/ preview button enabled if total members count is bigger thant 0', async () => {
		API.individuals.search.mockReturnValue(Promise.resolve({total: 1}));

		renderToolbar({criteria: data.mockNewCriteria(1, {valid: true})});

		await waitFor(() =>
			expect(screen.getByTestId('preview-criteria-button')).toBeEnabled()
		);
	});
});
