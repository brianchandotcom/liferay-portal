/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import SlaCardsList from '.';

describe('SLA Card', () => {
	const koroneikiAccount = {
		slaCurrent: 'Limited Subscription',
		slaCurrentEndDate: '2022-06-16T00:00:00Z',
		slaCurrentStartDate: '2022-06-16T00:00:00Z',
		slaExpired: 'Gold Subscription',
		slaExpiredEndDate: '2018-07-25T00:00:00Z',
		slaExpiredStartDate: '2017-08-25T00:00:00Z',
		slaFuture: 'Platinum Subscription',
		slaFutureEndDate: '2024-07-25T00:00:00Z',
		slaFutureStartDate: '2023-08-25T00:00:00Z',
	};

	it('displays Support Level title', () => {
		render(<SlaCardsList koroneikiAccount={koroneikiAccount} />);

		const slaTitle = screen.getByRole('heading', {name: /support level/i});
		expect(slaTitle).toHaveTextContent('Support Level');
	});

	it('displays Limited Support Level type', () => {
		render(<SlaCardsList koroneikiAccount={koroneikiAccount} />);

		const linkElementNameslaCurrent = screen.getByText(/limited/i);
		expect(linkElementNameslaCurrent).toBeInTheDocument();
	});

	it('displays Gold Support Level type', () => {
		render(<SlaCardsList koroneikiAccount={koroneikiAccount} />);

		const linkElementNameslaExpired = screen.getByText(/gold/i);
		expect(linkElementNameslaExpired).toBeInTheDocument();
	});

	it('displays Platinum Support Level type', () => {
		render(<SlaCardsList koroneikiAccount={koroneikiAccount} />);

		const linkElementNameslaFuture = screen.getByText(/platinum/i);
		expect(linkElementNameslaFuture).toBeInTheDocument();
	});

	it('displays Premium Support Level type', () => {
		const koroneikiAccount = {
			slaExpired: 'Gold Subscription',
			slaExpiredEndDate: '2018-07-25T00:00:00Z',
			slaExpiredStartDate: '2017-08-25T00:00:00Z',
			slaFuture: 'Premium Subscription',
			slaFutureEndDate: '2024-07-25T00:00:00Z',
			slaFutureStartDate: '2023-08-25T00:00:00Z',
		};
		render(<SlaCardsList koroneikiAccount={koroneikiAccount} />);

		const linkElementNameslaFuture = screen.getByText(/premium/i);
		expect(linkElementNameslaFuture).toBeInTheDocument();
	});

	it('shows SLA Card start and end date', () => {
		render(<SlaCardsList koroneikiAccount={koroneikiAccount} />);

		const linkElementEndDate = screen.getByText('06/15/2022', {
			exact: false,
		});
		expect(linkElementEndDate).toHaveTextContent('06/15/2022');

		const linkElementStartDate = screen.getByText('06/15/2022', {
			exact: false,
		});
		expect(linkElementStartDate).toHaveTextContent('06/15/2022');
	});

	it('displays a message when the projectd do not have Sla Support', () => {
		const projectNoSlaMock = {};

		render(<SlaCardsList koroneikiAccount={projectNoSlaMock} />);
		const linkElement = screen.getByText(
			/support level is displayed here/i
		);
		expect(linkElement).toHaveTextContent(
			"The project's Support Level is displayed here for projects with ticketing support."
		);
	});

	it('displays an order from highest to lowest when user has multiple status (Current > Future > Expired)', async () => {
		const user = userEvent.setup();

		render(<SlaCardsList koroneikiAccount={koroneikiAccount} />);

		const linkCurrentStatus = screen.getByText(/current/i);

		expect(linkCurrentStatus).toBeInTheDocument();
		expect(screen.getByRole('button')).toBeInTheDocument();
		await user.click(screen.getByRole('button'));

		const linkFutureStatus = screen.getByText(/future/i);
		expect(linkFutureStatus).toBeInTheDocument();
		await user.click(screen.getByRole('button'));

		const linkExpiredStatus = screen.getByText(/expired/i);
		expect(linkExpiredStatus).toBeInTheDocument();
	});
});
