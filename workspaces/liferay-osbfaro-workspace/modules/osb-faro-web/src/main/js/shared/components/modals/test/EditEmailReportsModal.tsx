/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {act, fireEvent, render, waitFor} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import {Frequency} from '~/settings/channels/components/EmailReports';
import {addAlert} from '~/shared/actions/alerts';
import {close} from '~/shared/actions/modals';
import mockStore from '~/test/mock-store';

import EditEmailReportsModal from '../EditEmailReportsModal';

jest.mock('~/shared/actions/alerts', () => ({
	actionTypes: {},
	addAlert: jest.fn(() => ({
		meta: {},
		payload: {},
		type: 'addAlert',
	})),
}));

jest.unmock('react-dom');

const DefaultComponent = (
	props: Omit<
		React.ComponentProps<typeof EditEmailReportsModal>,
		'onCancel' | 'onSave'
	>
) => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider
				cache={
					new InMemoryCache({
						addTypename: false,
					})
				}
			>
				<EditEmailReportsModal
					onCancel={close}
					onSave={() => addAlert()}
					{...props}
				/>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('EditEmailReportsModal', () => {
	it('renders', () => {
		const {container} = render(
			<DefaultComponent
				report={{enabled: false, frequency: Frequency.Monthly}}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('clicks the toggle switch, check if its value has changed to true and if frequency options are enabled', () => {
		const {container} = render(
			<DefaultComponent
				report={{enabled: false, frequency: Frequency.Monthly}}
			/>
		);

		const frequency = document.getElementById('frequency');

		const toggleSwitch = document.querySelector(
			'input.toggle-switch-check'
		);

		expect(container).toContainElement(toggleSwitch as HTMLElement);

		expect(container).toContainElement(frequency);

		expect(toggleSwitch).toHaveAttribute('value', 'false');

		expect(frequency).toHaveAttribute('disabled');

		fireEvent.click(toggleSwitch!);

		expect(toggleSwitch).toHaveAttribute('value', 'true');

		expect(frequency).toBeEnabled();

		expect(frequency).not.toHaveAttribute('disabled');
	});

	it('configures email reports with the MONTHLY option', async () => {
		render(
			<DefaultComponent
				report={{enabled: true, frequency: Frequency.Monthly}}
			/>
		);

		const frequency = document.getElementById('frequency');

		const monthly = document.querySelector('option[value="monthly"]');

		const saveBtn = document.querySelector(
			'.modal-footer button[type="submit"]'
		);

		const toggleSwitch = document.querySelector(
			'input.toggle-switch-check'
		);

		fireEvent.click(toggleSwitch!);

		fireEvent.click(frequency!);

		fireEvent.click(monthly!);

		fireEvent.click(saveBtn!);

		act(() => {
			jest.advanceTimersByTime(0);
		});

		await waitFor(() => expect(addAlert).toHaveBeenCalled());
	});

	it('configures email reports with the WEEKLY option', async () => {
		render(
			<DefaultComponent
				report={{enabled: true, frequency: Frequency.Weekly}}
			/>
		);

		const frequency = document.getElementById('frequency');

		const saveBtn = document.querySelector(
			'.modal-footer button[type="submit"]'
		);

		const toggleSwitch = document.querySelector(
			'input.toggle-switch-check'
		);

		const weekly = document.querySelector('option[value="weekly"]');

		fireEvent.click(toggleSwitch!);

		fireEvent.click(frequency!);

		fireEvent.click(weekly!);

		fireEvent.click(saveBtn!);

		act(() => {
			jest.advanceTimersByTime(0);
		});

		await waitFor(() => expect(addAlert).toHaveBeenCalled());
	});

	it('configures email reports with the DAILY option', async () => {
		render(
			<DefaultComponent
				report={{enabled: true, frequency: Frequency.Daily}}
			/>
		);

		const daily = document.querySelector('option[value="daily"]');

		const frequency = document.getElementById('frequency');

		const saveBtn = document.querySelector(
			'.modal-footer button[type="submit"]'
		);

		const toggleSwitch = document.querySelector(
			'input.toggle-switch-check'
		);

		fireEvent.click(toggleSwitch!);

		fireEvent.click(frequency!);

		fireEvent.click(daily!);

		fireEvent.click(saveBtn!);

		act(() => {
			jest.advanceTimersByTime(0);
		});

		await waitFor(() => expect(addAlert).toHaveBeenCalled());
	});
});
