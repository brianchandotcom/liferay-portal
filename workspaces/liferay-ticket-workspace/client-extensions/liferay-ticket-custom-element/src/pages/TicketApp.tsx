/* eslint-disable quote-props */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {QueryClient, useMutation} from 'react-query';

import {useRecentTickets} from '../hooks/useRecentTickets';
import {useTickets} from '../hooks/useTickets';
import {generateNewTicket} from '../services/tickets';

import '../styles/TicketApp.css';
import {RecentActivity} from '../components/RecentActivity';
import {TicketGrid} from '../components/TicketGrid';
import {Liferay} from '../services/liferay';
import TicketsByStatusDashboard from './TicketsByStatusDashboard';

const initialFilterState = {
	field: '',
	value: '',
};

const filters = [
	{
		field: 'ticketStatus',
		text: 'Open issues',
		value: 'open',
	},
	{
		field: 'ticketStatus',
		text: 'Queued issues',
		value: 'queued',
	},
	{
		field: 'priority',
		text: 'Major Priority issues',
		value: 'major',
	},
	{
		field: 'resolution',
		text: 'Unresolved issues',
		value: 'unresolved',
	},
];

const compareFilters = (filterA: any, filterB: any) =>
	JSON.stringify(filterA) === JSON.stringify(filterB);

type AppProps = {
	queryClient: QueryClient;
};

const App: React.FC<AppProps> = ({queryClient}) => {
	const [filter, setFilter] = useState(initialFilterState);
	const [page, setPage] = useState(1);
	const [pageSize, setPageSize] = useState(20);
	const [search, setSearch] = useState<string>('');

	const recentTickets = useRecentTickets();
	const {rows: tickets, totalCount} = useTickets({
		filter,
		page,
		pageSize,
		search,
	});

	const mutation = useMutation({
		mutationFn: generateNewTicket,
		onSuccess: () => {
			queryClient.invalidateQueries();

			setPage(1);

			Liferay.Util.openToast({
				message: 'A new ticket was added!',
				type: 'success',
			});
		},
	});

	return (
		<>
			<header className="align-items-center bg-light mb-3 p-3 row">
				<h1>Your Tickets</h1>
				<button
					className="btn btn-primary ml-auto"
					onClick={(event) => {
						mutation.mutate();

						event.preventDefault();
					}}
				>
					Generate a New Ticket
				</button>
			</header>

			<main className="p-0 row">
				<div className="col-md-10 m-0 p-0 pr-3">
					<input className="form-control mb-3 w-100"
						onChange={(event) => {
							setSearch(event.target.value);
							setPage(1);
						}}
						placeholder="Search Tickets"
						type="text"
					></input>

					<TicketGrid tickets={tickets} />
					
					<div className="my-3">
						<ClayPaginationBarWithBasicItems
							active={page}
							activeDelta={pageSize}
							ellipsisBuffer={3}
							ellipsisProps={{
								'aria-label': 'More',
								'title': 'More',
							}}
							onActiveChange={setPage}
							onDeltaChange={setPageSize}
							spritemap={Liferay.Icons.spritemap}
							totalItems={totalCount}
						/>
					</div>
				</div>

				<nav className="col-md-2 ml-auto">
					<h6 className="text-uppercase">Filters</h6>
					<ul>
						{filters.map((_filter, index) => {
							const isFilterSelected = compareFilters(
								filter,
								_filter
							);

							return (
								<li key={index}>
									<a
										className={
											isFilterSelected
												? 'font-weight-bold'
												: undefined
										}
										href="/"
										onClick={(event) => {
											setPage(1);
											setFilter(
												isFilterSelected
													? initialFilterState
													: _filter
											);

											event.preventDefault();
										}}
									>
										{_filter.text}
									</a>
								</li>
							);
						})}
					</ul>
				</nav>
			</main>

			<RecentActivity tickets={recentTickets} />
		</>
	);
};

export default App;
