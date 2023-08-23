import {useState} from 'react';
import {useMutation} from 'react-query';

import ClayAlert from '@clayui/alert';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';

import {useRecentTickets, useTickets, generateNewTicket} from './tickets';
import './TicketApp.css';
import {TicketGrid} from './TicketGrid';
import {RecentActivity} from './RecentActivity';

const initialToastState = {
	content: null,
	show: false,
	type: null,
};

const initialFilterState = {
	field: '',
	value: '',
};

const filters = [
	{
		field: 'ticketStatus',
		value: 'open',
		text: 'Open issues',
	},
	{
		field: 'ticketStatus',
		value: 'queued',
		text: 'Queued issues',
	},
	{
		field: 'priority',
		value: 'major',
		text: 'Major Priority issues',
	},
	{
		field: 'resolution',
		value: 'unresolved',
		text: 'Unresolved issues',
	},
];

function App({queryClient}) {
	const [filter, setFilter] = useState(initialFilterState);
	const [page, setPage] = useState(1);
	const [pageSize, setPageSize] = useState(20);
	const [search, setSearch] = useState();
	const [toastMessage, setToastMessage] = useState(initialToastState);

	const recentTickets = useRecentTickets();
	const tickets = useTickets(page, pageSize, filter, search);

	const mutation = useMutation({
		mutationFn: generateNewTicket,
		onSuccess: () => {
			queryClient.invalidateQueries();
			setPage(1);
			setToastMessage({
				content: 'A new ticket was added!',
				show: true,
				type: 'success',
			});
		},
	});

	return (
		<section className="container current-tickets m-0 p-0 row">
			<div className="col-md-2">
				<nav className="h-100 site-navigation">
					<h6 className="text-uppercase">Site</h6>
					<ul>
						<li>
							<a href="/">Dashboards</a>
						</li>
						<li>
							<a href="/">Projects</a>
						</li>
						<li>
							<a href="/">Issues</a>
						</li>
					</ul>
				</nav>
			</div>
			<div className="col-md-10">
				<header className="align-items-center bg-light mb-3 p-3 row">
					<h1> Your Tickets</h1>
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
				<main className="row p-0">
					<div className="col-md-10 m-0 p-0 pr-3">
						<input
							className="form-control mb-3 w-100"
							placeholder="Search Tickets"
							type="text"
							onChange={(event) => {
								setSearch(event.target.value);
								setPage(1);
							}}
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
								onDeltaChange={(pageSize) => {
									setPageSize(pageSize);
								}}
								onActiveChange={(page) => {
									setPage(page);
								}}
								spritemap={Liferay.Icons.spritemap}
								totalItems={tickets.totalCount}
							/>
						</div>
					</div>
					<nav className="col-md-2 ml-auto">
						<h6 className="text-uppercase">Filters</h6>
						<ul>
							{filters.map((thisFilter, index) => (
								<li key={index}>
									<a
										className={
											filter === thisFilter
												? 'font-weight-bold'
												: ''
										}
										href="/"
										onClick={(event) => {
											if (filter === thisFilter) {
												setFilter(initialFilterState);
												setPage(1);
											} else {
												setFilter(thisFilter);
												setPage(1);
											}
											event.preventDefault();
										}}
									>
										{thisFilter.text}
									</a>
								</li>
							))}
						</ul>
					</nav>
				</main>
			</div>
			<RecentActivity recentTickets={recentTickets} />

			{toastMessage.show && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={3000}
						displayType={toastMessage.type}
						onClose={() => setToastMessage(initialToastState)}
						spritemap={Liferay.Icons.spritemap}
					>
						{toastMessage.content}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</section>
	);
}

export default App;
