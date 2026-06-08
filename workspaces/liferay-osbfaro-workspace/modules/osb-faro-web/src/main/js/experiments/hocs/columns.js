/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNil} from 'lodash';
import moment from 'moment';
import momentTimezone from 'moment-timezone';
import React from 'react';
import {Link, useParams} from 'react-router-dom';
import {getStatusColor, getStatusName} from '~/experiments/util/experiments';
import Label from '~/shared/components/Label';
import TextTruncate from '~/shared/components/TextTruncate';
import {
	DateCell,
	TableDataCell,
} from '~/shared/components/table/cell-components';
import {applyTimeZone, formatDateToTimeZone} from '~/shared/util/date';
import {Routes} from '~/shared/util/router';
import {getUrl} from '~/shared/util/urls';
import {getSafeDecodedURIComponent} from '~/shared/util/util';

const ExperimentListTitle = ({id, title, touchpoint}) => {
	const {channelId, groupId} = useParams();

	const url = getUrl(Routes.TESTS_OVERVIEW, {
		params: {channelId, groupId, id},
		query: {},
	});

	return (
		<td className="table-cell-expand">
			<Link className="table-title" to={url}>
				<TextTruncate title={title || touchpoint}>
					{title || '-'}
				</TextTruncate>
			</Link>
		</td>
	);
};

const Columns = function Columns(timeZoneId) {
	return [
		{
			accessor: 'name',
			cellRenderer: ({data: {id, name, pageURL}}) => (
				<ExperimentListTitle
					id={id}
					title={name}
					touchpoint={pageURL}
				/>
			),
			className: 'table-cell-expand',
			label: Liferay.Language.get('name'),
		},
		{
			accessor: 'pageURL',
			cellRenderer: ({data: {pageURL}}) => (
				<TableDataCell
					firstColumn={false}
					title={getSafeDecodedURIComponent(pageURL)}
				/>
			),
			className: 'table-cell-expand',
			label: Liferay.Language.get('url').toUpperCase(),
			width: '300px',
		},
		{
			accessor: 'status',
			dataFormatter: (value) => (
				<Label
					className="experiment-status"
					display={getStatusColor(value)}
					key="status"
					size="lg"
				>
					{getStatusName(value)}
				</Label>
			),
			label: Liferay.Language.get('status'),
		},
		{
			accessor: 'type',
			dataFormatter: (value) => (value === 'AB' ? 'A/B' : value),
			label: Liferay.Language.get('type'),
		},
		{
			accessor: 'createDate',
			cellRenderer: DateCell,
			cellRendererProps: {
				dateFormatter: (date) =>
					formatDateToTimeZone(date, 'll', timeZoneId),
				datePath: 'createDate',
			},
			className: 'table-column-text-end',
			label: Liferay.Language.get('created'),
		},
		{
			accessor: 'modifiedDate',
			cellRenderer: DateCell,
			cellRendererProps: {
				dateFormatter: (modifiedDate) => {
					if (!isNil(modifiedDate)) {
						const timeZonedDate = applyTimeZone(
							modifiedDate,
							timeZoneId
						);

						return momentTimezone()
							.tz(timeZoneId)
							.diff(timeZonedDate, 'day') > 0
							? formatDateToTimeZone(
									modifiedDate,
									'll',
									timeZoneId
								)
							: moment.utc(modifiedDate).fromNow();
					}
				},
				datePath: 'modifiedDate',
			},
			className: 'table-column-text-end',
			label: Liferay.Language.get('last-modified'),
		},
	];
};

export default Columns;
