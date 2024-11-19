/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import i18n from '~/common/I18n';

import SVFilter from '../../components/SVFilter';
import SVSearch from '../../components/SVSearch';
import SVTable from '../../components/SVTable';
import {IRow} from '../../components/SVTable/SVTable';
import {ITicket} from '../../interfaces/ITicket';

import './SecurityVulnerabilitiesList.css';
import useJiraData from '../../hooks/useJiraData';
import {FILTER_OPTIONS} from '../../utils/constants/filtersOptions';

export interface IFilters {
	affectedVersions: string[];
	categories: string[];
	classifications: string[];
	fixVersions: string[];
	search: string;
	severities: string[];
	sort: string;
}

const SecurityVulnerabilitiesList = () => {
	const [filters, setFilters] = useState<IFilters>({
		affectedVersions: [],
		categories: [],
		classifications: [],
		fixVersions: [],
		search: '',
		severities: [],
		sort: '',
	});

	const {jiraData} = useJiraData();
	const [rows, setRows] = useState<IRow[]>([]);

	useEffect(() => {
		if (jiraData) {
			const newRows = jiraData.map((ticket: ITicket) => ({
				affectedVersions: ticket.affectedVersions?.join(', '),
				category: ticket.category,
				classification: ticket.classification,
				prioritySummary: (
					<div className="sv-priority-summary">
						<div className="mr-1 px-2 sv-severity text-center">
							{ticket.severity}
						</div>
						<div className="sv-summary">{ticket.summary}</div>
					</div>
				),
				publishedDate: ticket.publishedDate,
			}));
			setRows(newRows);
		}
	}, [jiraData]);

	const handleFilterChange = (newFilters: IFilters) => {
		setFilters((prevFilters) => ({
			...prevFilters,
			...newFilters,
		}));
	};

	const handleSearchChange = (term: string) => {
		setFilters((prevFilters) => ({
			...prevFilters,
			search: term,
		}));
	};

	const columns = [
		{
			columnKey: 'prioritySummary',
			label: i18n.translate('priority-summary'),
		},
		{
			columnKey: 'category',
			label: i18n.translate('category'),
		},
		{
			columnKey: 'classification',
			label: i18n.translate('classification'),
		},
		{
			columnKey: 'affectedVersions',
			label: i18n.translate('affected-versions'),
		},
		{
			columnKey: 'publishedDate',
			label: i18n.translate('published'),
		},
	];

	return (
		<>
			<div className="align-items-center d-flex flex-column sv-content">
				<div className="align-items-center d-flex flex-column justify-content-center my-5 sv-header text-center">
					<h1 className="my-4">{i18n.translate('cve-reports')}</h1>

					<SVSearch
						onChange={handleSearchChange}
						term={filters.search}
					/>
				</div>
			</div>

			<div className="row sv-content">
				<div className="col-3">
					<SVFilter
						filterOptions={FILTER_OPTIONS}
						filters={filters}
						onChange={handleFilterChange}
					/>
				</div>

				<div className="col">
					{rows.length ? (
						<SVTable columns={columns} rows={rows} />
					) : (
						<p>Loading...</p>
					)}
				</div>
			</div>
		</>
	);
};

export default SecurityVulnerabilitiesList;
