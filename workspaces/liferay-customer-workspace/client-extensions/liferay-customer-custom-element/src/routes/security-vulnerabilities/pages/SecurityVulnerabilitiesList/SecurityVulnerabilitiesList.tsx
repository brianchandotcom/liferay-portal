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

import {IRow} from '../../components/SVTable/SVTable';
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

	const rows = useMemo(() => {
		if (jiraSearch?.[JiraEnum.ISSUES]) {
			return jiraSearch?.[JiraEnum.ISSUES].map((issue: IJiraIssue) => ({
				affectedVersions:
					issue[JiraEnum.FIELDS]?.[JiraEnum.AFFECTED_VERSIONS]?.join(
						', '
					),
				category: issue[JiraEnum.FIELDS]?.[JiraEnum.CATEGORY],
				classification:
					issue[JiraEnum.FIELDS]?.[JiraEnum.CLASSIFICATION],
				prioritySummary: (
					<div className="sv-priority-summary">
						<div className="mr-1 px-2 sv-severity text-center">
							{issue[JiraEnum.FIELDS]?.[JiraEnum.SEVERITY]}
						</div>
						<div className="sv-summary">
							{issue[JiraEnum.FIELDS]?.[JiraEnum.SUMMARY]}
						</div>
					</div>
				),
				published: issue[JiraEnum.FIELDS]?.[JiraEnum.PUBLISHED_DATE],
			}));
		}
		else {
			return undefined;
		}
	}, [jiraSearch]);

	return (
		<>
			<div className="align-items-center d-flex flex-column sv-content">
				<div className="align-items-center d-flex flex-column justify-content-center my-5 sv-header text-center">
					<h1 className="my-4">{i18n.translate('cve-reports')}</h1>

					<SVSearch
						keywords={searchParams.get(JiraEnum.KEYWORDS) || ''}
						onChange={(keywords) =>
							updateSearchParams({[JiraEnum.KEYWORDS]: keywords})
						}
					/>
				</div>
			</div>

			<div className="row sv-content">
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
						<p>{i18n.translate('no-results')}</p>
					)}
				</div>
			</div>
		</>
	);
};

export default SecurityVulnerabilitiesList;
