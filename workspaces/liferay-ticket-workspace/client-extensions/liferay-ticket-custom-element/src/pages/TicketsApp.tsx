/* eslint-disable quote-props */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useContext, useState} from 'react';
import {QueryClient, useMutation} from 'react-query';

import {RecentActivity} from '../components/RecentActivity';
import {TicketGrid} from '../components/TicketGrid';
import {QueryClientContext} from '../context';
import {useRecentTickets} from '../hooks/useRecentTickets';
import {useTickets} from '../hooks/useTickets';
import {Liferay} from '../services/liferay';
import {generateNewTicket} from '../services/tickets';

type Filter = {
	field: string;
	text: string;
	value: string;
};

const initialFilterState: Filter = {
	field: '',
	text: '',
	value: '',
};

const filters: Filter[] = [
	{
		field: '',
		text: 'No Filter',
		value: '',
	},
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

const App: React.FC = () => {
	const queryClient: QueryClient = useContext(QueryClientContext);

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

	const generateNewTicketMutation = useMutation({
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
		<ClayIconSpriteContext.Consumer>
			{(spritemap) => (
				<>
					<div className="autofit-padded autofit-row bg-neutral-1 justify-content-between mb-3 p-3 rounded">
						<div className="autofit-col text-11">Your Tickets</div>
						<div className="autofit-col">
							<ClayButton
								displayType="primary"
								onClick={(event) => {
									event.preventDefault();

									generateNewTicketMutation.mutate();
								}}
							>
								Generate a New Ticket
							</ClayButton>
						</div>
					</div>

					<div className="autofit-padded autofit-row">
						<div className="autofit-col autofit-col-expand">
							<input
								className="form-control mb-3 w-100"
								onChange={(event) => {
									setSearch(event.target.value);

									setPage(1);
								}}
								placeholder="Search Tickets"
								type="text"
							></input>
						</div>

						<div className="autofit-col">
							<Picker
								aria-labelledby="picker-label"
								className="text-1 text-weight-bold"
								items={filters}
								onSelectionChange={(
									selectedFilterValue: any
								) => {
									setPage(1);

									const selectedFilter = filters.find(
										(filter) =>
											filter.value === selectedFilterValue
									);

									if (selectedFilter) {
										setFilter(selectedFilter);
									}
								}}
								placeholder="Select a Filter"
							>
								{(item) => (
									<Option key={item.value}>
										{item.text}
									</Option>
								)}
							</Picker>
						</div>
					</div>

					<div className="autofit-padded autofit-row">
						<div className="autofit-col w-100">
							<TicketGrid tickets={tickets} />
						</div>
					</div>

					<div className="autofit-padded autofit-row">
						<div className="autofit-col">
							<div className="my-3">
								<ClayPaginationBarWithBasicItems
									active={page}
									activeDelta={pageSize}
									ellipsisBuffer={3}
									onActiveChange={setPage}
									onDeltaChange={setPageSize}
									spritemap={spritemap}
									totalItems={totalCount}
								/>
							</div>
						</div>
					</div>

					<div className="autofit-padded autofit-row">
						<RecentActivity tickets={recentTickets} />
					</div>
				</>
			)}
		</ClayIconSpriteContext.Consumer>
	);
};

export default App;
