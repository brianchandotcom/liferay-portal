/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import DataGrid from 'react-data-grid';

import 'react-data-grid/lib/styles.css';

import {Liferay} from '../services/liferay';
import {Ticket} from '../types';

const TicketGrid = ({tickets}: {tickets: Ticket[]}) => (
	<DataGrid
		columns={[
			{
				formatter: ({row}: {row: Ticket}) => (
					<span>
						{!!row.suggestions.length && (
							<ClayIcon
								className="mr-1"
								spritemap={Liferay.Icons.spritemap}
								symbol="link"
							/>
						)}
						{row.subject}
					</span>
				),
				key: 'subject',
				name: 'Subject',
				resizable: true,
				width: '50%',
			} as any,
			{
				key: 'resolution',
				name: 'Resolution',
				resizable: true,
				width: '15%',
			},
			{
				formatter: ({row}: {row: Ticket}) => (
					<span>
						{row.ticketStatus === 'Queued' && (
							<ClayIcon
								className="mr-1"
								spritemap={Liferay.Icons.spritemap}
								symbol="bolt"
							/>
						)}
						{row.ticketStatus}
					</span>
				),
				key: 'ticketStatus',
				name: 'Status',
				resizable: true,
				width: '15%',
			},
			{
				key: 'priority',
				name: 'Priority',
				resizable: true,
				width: '10%',
			},
			{key: 'type', name: 'Type', resizable: true, width: '10%'},
			{
				key: 'region',
				name: 'Region',
				resizable: true,
				width: '10%',
			},
		]}
		rowKeyGetter={(row) => row.id}
		rows={tickets}
	/>
);

export {TicketGrid};
