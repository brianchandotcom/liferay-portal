/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import {
	SegmentActivationFrequencyTypes,
	SegmentActivationScheduleTypes,
	SegmentTypes,
} from '~/shared/util/constants';
import mockStore from '~/test/mock-store';

import SegmentActivationCard from '../SegmentActivationCard';

jest.unmock('react-dom');

const WrapperComponent: React.FC<{children: React.ReactNode}> = ({
	children,
}) => (
	<Provider store={mockStore()}>
		<BrowserRouter>{children}</BrowserRouter>
	</Provider>
);

jest.mock(
	'~/shared/components/DateRangeInput',
	() =>
		function MockDateInput({value}: {value: {end: string; start: string}}) {
			return (
				<div data-testid="mock-date-input">
					{value.start} - {value.end}
				</div>
			);
		}
);

describe('SegmentActivationCard', () => {
	it('renders when frequency type is batch', () => {
		const {container} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={fromJS({
						frequencyType:
							SegmentActivationFrequencyTypes.Indefinitely,
						scheduleType: SegmentActivationScheduleTypes.Batch,
					})}
					segmentType={SegmentTypes.Batch}
				/>
			</WrapperComponent>
		);
		expect(container).toMatchSnapshot();
	});

	it('renders when frequency type is real time', () => {
		const {container} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={fromJS({
						frequencyType:
							SegmentActivationFrequencyTypes.Indefinitely,
						scheduleType: SegmentActivationScheduleTypes.RealTime,
					})}
					segmentType={SegmentTypes.Batch}
				/>
			</WrapperComponent>
		);
		expect(container).toMatchSnapshot();
	});

	it('renders with dates when frequency type is between', () => {
		const {container} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={fromJS({
						frequencyType: SegmentActivationFrequencyTypes.Between,
						scheduleEndDate: '1757818800000',
						scheduleStartDate: '1756004400000',
						scheduleType: SegmentActivationScheduleTypes.Batch,
					})}
					segmentType={SegmentTypes.Batch}
				/>
			</WrapperComponent>
		);
		expect(container).toMatchSnapshot();
	});

	it('renders the modal if the edit button is clicked', async () => {
		const {findByTestId, findByText, getByTestId} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={fromJS({
						frequencyType:
							SegmentActivationFrequencyTypes.Indefinitely,
						scheduleType: SegmentActivationScheduleTypes.Batch,
					})}
					segmentType={SegmentTypes.Batch}
				/>
			</WrapperComponent>
		);

		const editButton = getByTestId('edit-activation-button');
		fireEvent.click(editButton);

		const modal = await findByTestId('segment-activation-modal');
		expect(modal).toBeInTheDocument();

		const modalHeader = await findByText('Configure Activation');
		expect(modalHeader).toBeInTheDocument();
	});

	it('renders the modal with the expected configurations - indefinitely', async () => {
		const {findByTestId, findByText, getByTestId} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={fromJS({
						frequencyType:
							SegmentActivationFrequencyTypes.Indefinitely,
						scheduleType: SegmentActivationScheduleTypes.RealTime,
					})}
					segmentType={SegmentTypes.RealTime}
				/>
			</WrapperComponent>
		);

		const editButton = getByTestId('edit-activation-button');
		fireEvent.click(editButton);

		const modal = await findByTestId('segment-activation-modal');
		expect(modal).toBeInTheDocument();

		expect(await findByText('Configure Activation')).toBeInTheDocument();

		expect(await findByText('indefinitely')).toBeInTheDocument();

		expect(await findByText('Real-Time')).toBeInTheDocument();
	});

	it('renders the modal with the expected configurations - between', async () => {
		const {findByTestId, findByText, getByTestId} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={fromJS({
						frequencyType: SegmentActivationFrequencyTypes.Between,
						scheduleEndDate: '1757818800000',
						scheduleStartDate: '1756004400000',
						scheduleType: SegmentActivationScheduleTypes.RealTime,
					})}
					segmentType={SegmentTypes.RealTime}
				/>
			</WrapperComponent>
		);

		const editButton = getByTestId('edit-activation-button');
		fireEvent.click(editButton);

		const modal = await findByTestId('segment-activation-modal');
		expect(modal).toBeInTheDocument();

		expect(await findByText('Configure Activation')).toBeInTheDocument();

		expect(await findByText('between')).toBeInTheDocument();

		expect(getByTestId('mock-date-input')).toBeInTheDocument();

		expect(await findByText('Real-Time')).toBeInTheDocument();
	});
});
