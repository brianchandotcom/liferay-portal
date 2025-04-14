/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './AssociatedTicketsContainer.css';

import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ChangeEvent, useCallback, useEffect, useRef, useState} from 'react';
import i18n from '~/utils/I18n';
import {ITicket} from '~/utils/types';

import TicketList from './TicketList/TicketList';

interface IProps {
	editing?: boolean;
	handleRemove?: (value: ITicket) => void;
	handleSelect?: (value: ITicket) => void;
	tickets: ITicket[];
}

const AssociatedTicketsContainer: React.FC<IProps> = ({
	editing,
	handleRemove = () => {},
	handleSelect = () => {},
	tickets,
}) => {
	const [expand, setExpand] = useState<boolean>(false);

	const [searchTerm, setSearchTerm] = useState<string>('');

	const handleOnChange = (event: ChangeEvent<HTMLInputElement>): void => {
		setSearchTerm(event.target.value);
	};

	const triggerElementRef = useRef(null);

	const handleExpand = (event: any, expand: boolean) => {
		triggerElementRef.current = event.target;

		setExpand(expand);
	};

	const openTicket = useCallback((ticket: ITicket) => {
		window.open(ticket.link, '_blank', 'noreferrer');
	}, []);

	useEffect(() => {}, []);

	return (
		<>
			{editing ? (
				<div className="associated-tickets-container p-3 w-100">
					<div className="associated-tickets-options pb-3">
						<div className="align-items-center d-flex position-relative w-100">
							<ClayInput
								className="associated-tickets-input px-3"
								onChange={handleOnChange}
								onClick={(event) => handleExpand(event, true)}
								placeholder={i18n.translate(
									'search-for-support-tickets'
								)}
								type="text"
								value={searchTerm}
							/>

							<ClayIcon
								className="associated-tickets-icon position-absolute"
								symbol="search"
							/>
						</div>

						<ClayDropDown.Menu
							active={expand}
							alignElementRef={triggerElementRef}
							autoBestAlign={false}
							onActiveChange={() => setExpand(!expand)}
							width="sm"
						>
							<TicketList
								primaryAction={handleSelect}
								secondaryAction={openTicket}
								tickets={tickets.filter(
									(ticket) =>
										!ticket.selected &&
										ticket.status !== 'closed' &&
										ticket.status !== 'solved' &&
										(String(ticket.ticketId)
											.toLowerCase()
											.includes(
												searchTerm.toLowerCase()
											) ||
											ticket.subject
												?.toLowerCase()
												.includes(
													searchTerm.toLowerCase()
												))
								)}
								type="option"
							/>
						</ClayDropDown.Menu>
					</div>

					<div className="pb-3 pl-1 text-neutral-8 text-paragraph-sm">
						{i18n.translate('tickets-impacting-this-event')}
					</div>

					<TicketList
						primaryAction={openTicket}
						secondaryAction={handleRemove}
						tickets={tickets.filter((ticket) => ticket.selected)}
						type="selected"
					/>
				</div>
			) : (
				<TicketList primaryAction={openTicket} tickets={tickets} />
			)}
		</>
	);
};

export default AssociatedTicketsContainer;
