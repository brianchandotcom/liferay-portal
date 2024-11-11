/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import i18n from '~/common/I18n';
import {FORMAT_DATE_TYPES} from '~/common/utils/constants';
import getDateCustomFormat from '~/common/utils/getDateCustomFormat';

import SVFilter from '../../components/SVFilter';
import SVSearch from '../../components/SVSearch';
import SVTable from '../../components/SVTable';
import {ITicket} from '../../interfaces/ITicket';

import './SecurityVulnerabilitiesList.css';

export interface IFilterOptions {
	categories: string[];
	classifications: string[];
	severities: string[];
	sorts: string[];
	versions: string[];
}

export interface IFilters {
	categories: string[];
	classifications: string[];
	search: string;
	severities: string[];
	sort: string;
	versions: string[];
}

const SecurityVulnerabilitiesList = () => {
	const [tickets, setTickets] = useState<ITicket[]>([]);
	const [filterOptions, setFilterOptions] = useState<IFilterOptions>({
		categories: [],
		classifications: [],
		severities: [],
		sorts: [],
		versions: [],
	});
	const [filters, setFilters] = useState<IFilters>({
		categories: [],
		classifications: [],
		search: '',
		severities: [],
		sort: '',
		versions: [],
	});

	useEffect(() => {
		const fetchTickets = async () => {
			const data: ITicket[] = [
				{
					category: ['Paas', 'Saas', 'Self-Hosted'],
					classification: 'Confirmed Vulnerability',
					date: '2024-01-01T00:00:00.000Z',
					id: 1,
					name: 'CVE-2024-38002',
					severity: 'Critical',
					summary: 'Regular users can edit',
					versions: ['2024.Q4', '2024.Q3'],
				},
				{
					category: ['Paas'],
					classification: 'Ignored',
					date: '2024-11-01T00:00:00.000Z',
					id: 2,
					name: 'CVE-2024-38012',
					severity: 'High',
					summary: 'Regular users can edit',
					versions: ['2024.Q4', '2024.Q3', '2024.Q2'],
				},
				{
					category: ['Saas'],
					classification: 'False Positive',
					date: '2022-01-01T00:00:00.000Z',
					id: 3,
					name: 'CVE-2024-38022',
					severity: 'Medium',
					summary: 'Regular users can edit',
					versions: ['2024.Q4'],
				},
				{
					category: ['Saas', 'Self-Hosted'],
					classification: 'False Positive',
					date: '2022-01-01T00:00:00.000Z',
					id: 4,
					name: 'CVE-2024-38003',
					severity: 'Low',
					summary: 'Regular users can edit',
					versions: ['2024.Q4', '2024.Q2'],
				},
				{
					category: ['Paas', 'Docker'],
					classification: 'Threat Information',
					date: '2023-01-01T00:00:00.000Z',
					id: 5,
					name: 'CVA-2024-38013',
					severity: 'None',
					summary: 'Regular users can edit',
					versions: ['2024.Q4'],
				},
			];

			setTickets(data);
		};

		fetchTickets();
	}, []);

	useEffect(() => {
		const fetchFilterOptions = async () => {
			const data = {
				categories: ['Paas', 'Saas', 'Self-Hosted', 'Docker'],
				classifications: [
					'Confirmed Vulnerability',
					'Ignored',
					'False Positive',
					'Advisory',
					'Threat Information',
				],
				severities: ['Critical', 'High', 'Medium', 'Low', 'None'],
				sorts: ['Newest', 'Oldest'],
				versions: ['2024.Q4', '2024.Q3', '2024.Q2', '2024.Q1'],
			};

			setFilterOptions(data);
		};

		fetchFilterOptions();
	}, []);

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

	const filteredTickets = tickets
		.filter((ticket) => {
			const matchesSearch =
				ticket.name
					?.toLowerCase()
					.includes(filters.search.toLowerCase()) ||
				ticket.id?.toString().includes(filters.search);

			const matchesCategory =
				!filters.categories.length ||
				ticket.category?.some((ticketCategory) =>
					filters.categories.includes(ticketCategory)
				);

			const matchesClassification =
				!filters.classifications.length ||
				filters.classifications.includes(
					ticket.classification as string
				);

			const matchesVersion =
				!filters.versions.length ||
				ticket.versions?.some((ticketVersion) =>
					filters.versions.includes(ticketVersion)
				);

			const matchesSeverity =
				!filters.severities.length ||
				filters.severities.includes(ticket.severity as string);

			return (
				matchesSearch &&
				matchesCategory &&
				matchesClassification &&
				matchesVersion &&
				matchesSeverity
			);
		})
		.sort((a, b) => {
			const dateA = new Date(a.date as string).getTime();
			const dateB = new Date(b.date as string).getTime();

			if (filters.sort === 'Newest') {
				return dateB - dateA;
			}
			else {
				return dateA - dateB;
			}
		});

	const columns = [
		{
			columnKey: 'prioritySummary',
			label: 'Priority & Summary',
		},
		{
			columnKey: 'category',
			label: 'Category',
		},
		{
			columnKey: 'classification',
			label: 'Classification',
		},
		{
			columnKey: 'versions',
			label: 'Versions',
		},
		{
			columnKey: 'date',
			label: 'Date',
		},
	];

	const rows = filteredTickets.map((ticket) => ({
		category: ticket.category?.join(', '),
		classification: ticket.classification,
		date: getDateCustomFormat(
			ticket.date,
			FORMAT_DATE_TYPES.day2DMonthSYearN
		),
		id: ticket.id?.toString(),
		prioritySummary: (
			<div className="sv-priority-summary">
				<div className="align-items-center d-flex">
					<div className="mr-1 px-2 sv-severity text-center">
						{ticket.severity}
					</div>
					<div className="sv-name">
						<Link
							className="ticket-name-link"
							to={`/ticket/${ticket.id}`}
						>
							{ticket.name}
						</Link>
					</div>
				</div>
				<div className="sv-summary">{ticket.summary}</div>
			</div>
		),
		versions: ticket.versions?.join(', '),
	}));

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
						filterOptions={filterOptions}
						filters={filters}
						onChange={handleFilterChange}
					/>
				</div>

				<div className="col">
					<SVTable columns={columns} rows={rows} />
				</div>
			</div>
		</>
	);
};

export default SecurityVulnerabilitiesList;
