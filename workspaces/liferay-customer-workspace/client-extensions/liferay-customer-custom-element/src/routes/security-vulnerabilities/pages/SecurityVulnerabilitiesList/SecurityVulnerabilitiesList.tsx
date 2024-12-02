/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/common/I18n';

import SVFilter from '../../components/SVFilter';
import SVSearch from '../../components/SVSearch';
import SVTable from '../../components/SVTable';

import './SecurityVulnerabilitiesList.css';

import {useMemo} from 'react';
import {Link} from 'react-router-dom';
import {Liferay} from '~/common/services/liferay';
import {FORMAT_DATE_TYPES} from '~/common/utils/constants';

import {IRow} from '../../components/SVTable/SVTable';
import SVAffectedVersions from '../../components/SVTable/components/SVAffectedVersions';
import {IJiraIssue} from '../../hooks/useJiraIssue';
import useJiraSearch from '../../hooks/useJiraSearch';
import {FILTER_OPTIONS} from '../../utils/constants/filterOptions';
import {JiraEnum} from '../../utils/constants/jiraEnum';
import {SORT_OPTIONS} from '../../utils/constants/sortOptions';

const SecurityVulnerabilitiesList = () => {
	const {jiraSearch, loading, searchParams, updateSearchParams} =
		useJiraSearch();

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
			columnKey: 'published',
			label: i18n.translate('published'),
		},
	];

	const formatPublishedDate = (
		dateString: string | undefined,
		formatType: keyof typeof FORMAT_DATE_TYPES
	): string => {
		if (!dateString) {
			return 'Invalid Date';
		}

		const formattedDateString = dateString.replace(
			/([+-]\d{2})(\d{2})$/,
			'$1:$2'
		);
		const date = new Date(formattedDateString);

		if (isNaN(date.getTime())) {
			return 'Invalid Date';
		}

		const options = FORMAT_DATE_TYPES[formatType];

		return date.toLocaleDateString(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			options
		);
	};

	const rows = useMemo(() => {
		if (jiraSearch?.[JiraEnum.ISSUES]) {
			return jiraSearch?.[JiraEnum.ISSUES].map((issue: IJiraIssue) => ({
				affectedVersions: (
					<div>
						<SVAffectedVersions
							affectedVersions={
								issue[JiraEnum.FIELDS]?.[
									JiraEnum.AFFECTED_VERSIONS
								]
							}
						/>
					</div>
				),
				category: issue[JiraEnum.FIELDS]?.[JiraEnum.CATEGORY],
				classification:
					issue[JiraEnum.FIELDS]?.[JiraEnum.CLASSIFICATION],
				prioritySummary: (
					<div className="sv-priority-summary">
						<div className="align-items-center d-flex">
							<div className="mr-1 px-2 sv-severity text-center">
								{issue[JiraEnum.FIELDS]?.[JiraEnum.SEVERITY]}
							</div>

							<div className="font-weight-bold sv-name">
								<Link
									className="sv-name-link"
									to={`/ticket/${issue[JiraEnum.FIELDS]?.[JiraEnum.CVE_IDS]}`}
								>
									{issue[JiraEnum.FIELDS]?.[JiraEnum.CVE_IDS]}
								</Link>
							</div>
						</div>
						<div className="sv-summary">
							{issue[JiraEnum.FIELDS]?.[JiraEnum.SUMMARY]}
						</div>
					</div>
				),
				published: formatPublishedDate(
					issue[JiraEnum.FIELDS]?.[JiraEnum.PUBLISHED_DATE],
					'day2DMonthSYearN'
				),
			}));
		}
		else {
			return undefined;
		}
	}, [jiraSearch]);

	return (
		<>
			<div className="align-items-center d-flex flex-column mt-3 sv-header">
				<div className="align-items-center d-flex flex-column justify-content-center my-5 sv-search text-center">
					<h1 className="my-4 text-neutral-0">
						{i18n.translate('liferay-security-reports')}
					</h1>

					<SVSearch
						keywords={searchParams.get(JiraEnum.KEYWORDS) || ''}
						onChange={(keywords) =>
							updateSearchParams({[JiraEnum.KEYWORDS]: keywords})
						}
					/>
				</div>
			</div>

			<div className="d-flex justify-content-center">
				<div className="row sv-table-content">
					<div className="col-3">
						<SVFilter
							filterOptions={FILTER_OPTIONS}
							onChange={(params) => updateSearchParams(params)}
							params={searchParams}
							sortOptions={SORT_OPTIONS}
						/>
					</div>

					<div className="col-9">
						{loading ? (
							<span className="cp-spinner ml-2 spinner-border spinner-border-sm"></span>
						) : rows?.length ? (
							<SVTable
								columns={columns}
								rows={rows as unknown as IRow[]}
							/>
						) : (
							<div className="py-2">
								{i18n.translate(
									'the-requested-search-does-not-exist-in-our-database-please-try-again-with-different-criteria'
								)}
							</div>
						)}
					</div>
				</div>
			</div>
		</>
	);
};

export default SecurityVulnerabilitiesList;
