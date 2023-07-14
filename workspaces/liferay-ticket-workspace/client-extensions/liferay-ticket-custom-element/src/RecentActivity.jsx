import RelativeTime from '@yaireo/relative-time';
const relativeTime = new RelativeTime();

export const RecentActivity = ({recentTickets}) => {
	return (
		<div className="col pr-0">
			<footer className="bg-light p-3 w-100 my-3">
				<h2>Recent Activity</h2>
				<ul>
					{recentTickets.length > 0 &&
						recentTickets.map((recentTicket, index) => (
							<li className="pb-2" key={index}>
								Ticket #{recentTicket.id} (
								<em>{recentTicket.subject}</em>) was updated
								with status "{recentTicket.ticketStatus}" for
								support region {recentTicket.supportRegion}{' '}
								{relativeTime.from(recentTicket.dateCreated)}.
								{recentTicket.suggestions &&
									recentTicket.suggestions.length > 0 && (
										<div className="m-2 p-2">
											<em>Update:</em> Here are some
											suggestions for resources re: this
											ticket:&nbsp;
											{recentTicket.suggestions.map(
												(suggestion, index) => (
													<span key={index}>
														<a
															key={index}
															href={
																suggestion.assetURL
															}
															rel="noreferrer"
															target="_blank"
														>
															{suggestion.text}
														</a>
														,&nbsp;
													</span>
												)
											)}
										</div>
									)}
							</li>
						))}
					{recentTickets.length === 0 && (
						<>
							<li>
								Ticket #1234 closed with status "Resolved" by
								administrator
							</li>
							<li>
								Ticket #4566 closed with status "Won't fix" by
								administrator
							</li>
						</>
					)}
				</ul>
			</footer>
		</div>
	);
};
