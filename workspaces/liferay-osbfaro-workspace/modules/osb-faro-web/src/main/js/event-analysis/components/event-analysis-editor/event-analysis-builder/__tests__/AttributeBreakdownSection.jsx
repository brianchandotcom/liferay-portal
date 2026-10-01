import AttributeBreakdownSection from '../AttributeBreakdownSection';
import mockStore from 'test/mock-store';
import React from 'react';
import {AttributesContext} from '../../context/attributes';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {InMemoryCache} from '@apollo/client';
import {MemoryRouter, Route, Routes as RouterRoutes} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';
import {fireEvent, render, screen} from '@testing-library/react';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const WrappedComponent = ({eventId, ...attributes}) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/event-analysis']}>
			<RouterRoutes>
				<Route
					element={
						<MockedProvider
							cache={
								new InMemoryCache({
									addTypename: false,
									freezeResults: false
								})
							}
						>
							<DndProvider backend={HTML5Backend}>
								<AttributesContext.Provider
									value={{
										attributes: {},
										breakdownOrder: [],
										breakdowns: {},
										...attributes
									}}
								>
									<AttributeBreakdownSection eventId={eventId} />
								</AttributesContext.Provider>
							</DndProvider>
						</MockedProvider>
					}
					path={`${Routes.EVENT_ANALYSIS}/*`}
				/>
			</RouterRoutes>
		</MemoryRouter>
	</Provider>
);

describe('AttributeBreakdownSection', () => {
	jest.useFakeTimers();

	it('does not render without an event', () => {
		const {container} = render(<WrappedComponent />);

		expect(
			container.querySelector('.attribute-breakdown-section-root')
		).toBeNull();
	});

	it('renders only the title and the add button without breakdowns', () => {
		const {container} = render(<WrappedComponent eventId='1' />);

		expect(
			screen.getByRole('heading', {name: /breakdown.by/i})
		).toBeInTheDocument();
		expect(
			screen.getByRole('button', {name: /add.breakdown/i})
		).toBeInTheDocument();
		expect(container.querySelector('.attribute-list')).toBeNull();
	});

	it('hides the add button when there are 5 breakdowns', () => {
		render(
			<WrappedComponent
				attributes={{
					1: {
						dataType: 'STRING',
						displayName: 'Title',
						id: '1',
						name: 'title'
					},
					123123: {
						dataType: 'STRING',
						displayName: 'Job Title',
						id: '123123',
						name: 'jobTitle'
					},
					321321: {
						dataType: 'STRING',
						displayName: 'Article Title',
						id: '321321',
						name: 'articleTitle'
					},
					400: {
						dataType: 'STRING',
						displayName: 'Author',
						id: '400',
						name: 'author'
					},
					500: {
						dataType: 'STRING',
						displayName: 'Date',
						id: '500',
						name: 'date'
					}
				}}
				breakdownOrder={['1', '321321', '123123', '400', '500']}
				breakdowns={{
					1: {
						attributeId: '1',
						dataType: 'STRING',
						type: 'event'
					},
					123123: {
						attributeId: '123123',
						dataType: 'STRING',
						type: 'event'
					},
					321321: {
						attributeId: '321321',
						dataType: 'STRING',
						type: 'event'
					},
					400: {
						attributeId: '400',
						dataType: 'STRING',
						type: 'event'
					},
					500: {
						attributeId: '500',
						dataType: 'STRING',
						type: 'event'
					}
				}}
				eventId='2'
			/>
		);

		expect(
			screen.queryByRole('button', {name: /add.breakdown/i})
		).toBeNull();
	});

	it('renders the breakdowns and focuses the section when one is removed', () => {
		const deleteBreakdown = jest.fn();

		const {container} = render(
			<WrappedComponent
				attributes={{
					123123: {
						displayName: 'Job Title',
						id: '123123',
						name: 'jobTitle'
					},
					321321: {
						displayName: 'Article Title',
						id: '321321',
						name: 'articleTitle'
					}
				}}
				breakdownOrder={['321321', '123123']}
				breakdowns={{
					123123: {
						attributeId: '123123',
						dataType: 'STRING',
						id: '123123',
						type: 'event'
					},
					321321: {
						attributeId: '321321',
						dataType: 'STRING',
						id: '321321',
						type: 'event'
					}
				}}
				deleteBreakdown={deleteBreakdown}
				eventId='2'
			/>
		);

		expect(
			container.querySelectorAll('.attribute-list .attribute-chip-container')
		).toHaveLength(2);

		fireEvent.click(container.querySelector('.remove-button'));

		jest.runAllTimers();

		expect(deleteBreakdown).toHaveBeenCalledWith({id: '321321'});
		expect(document.activeElement).toBe(
			container.querySelector('.attribute-breakdown-section-root')
		);
	});
});
