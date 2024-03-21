/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import RelativeTime from '@yaireo/relative-time';
import React from 'react';

import {Ticket} from '../types';

const relativeTime = new RelativeTime();

const DEFAULT_TICKETS = [
	`Ticket #1234 closed with status "Resolved" by administrator`,
	`Ticket #4566 closed with status "Won't fix" by administrator`,
];

type Props = {
	tickets: Ticket[];
};

const DisplayTickets = ({tickets}: Props) => {
	if (!tickets.length) {
		return (
			<>
				{DEFAULT_TICKETS.map((defaultTicket, index) => (
					<li key={index}>{defaultTicket}</li>
				))}
			</>
		);
	}

	return (
		<>
			{tickets.map((recentTicket, index) => (
				<li className="pb-2" key={index}>
					<span>
						{`Ticket #${recentTicket.id}`}
						<em>{recentTicket.subject}</em>
						{`was updated with status "${
							recentTicket.ticketStatus
						}" for support region ${
							recentTicket.region
						} ${relativeTime.from(recentTicket.dateCreated)}. `}
					</span>

					{!!recentTicket.suggestions?.length && (
						<div className="m-2 p-2">
							<em>Update:</em> Here are some suggestions for
							resources re: this ticket:&nbsp;
							{recentTicket.suggestions.map(
								(suggestion, index) => (
									<span key={index}>
										<a
											href={suggestion.assetURL}
											key={index}
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
		</>
	);
};

const RecentActivity = ({tickets}: Props) => {
	return (
		<div className="col px-0">
			<div className="bg-neutral-1 my-3 p-3 w-100">
				<h2>Recent Activity</h2>

				<ul>
					<DisplayTickets tickets={tickets} />
				</ul>
			</div>
		</div>
	);
};

export {RecentActivity};
