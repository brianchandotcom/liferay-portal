/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Link, useParams} from 'react-router-dom';
import i18n from '~/common/I18n';

import useJiraIssue, {IJiraIssue} from '../../hooks/useJiraIssue';
import {JiraEnum} from '../../utils/constants/jiraEnum';

import './SecurityVulnerabilitiesItem.css';

import {useMemo} from 'react';

import SVTable from '../../components/SVTable';
import {IRow} from '../../components/SVTable/SVTable';
import SVAffectedVersions from '../../components/SVTable/components/SVAffectedVersions';
import useJiraSearch from '../../hooks/useJiraSearch';

const SecurityVulnerabilitiesItem = () => {
	const {id} = useParams();

	const {jiraIssue, loading: issueLoading} = useJiraIssue(id);
	const {jiraSearch, loading: searchLoading} = useJiraSearch();

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
	];

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
							<div
								className={`mr-1 px-2 sv-severity sv-severity-${issue[JiraEnum.FIELDS]?.[JiraEnum.SEVERITY]?.toLowerCase()} text-center`}
							>
								{issue[JiraEnum.FIELDS]?.[JiraEnum.SEVERITY]}
							</div>

							<div className="font-weight-bold sv-name">
								<Link
									className="sv-name-link"
									to={`/ticket/${issue?.[JiraEnum.KEY]}`}
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
			}));
		}
		else {
			return undefined;
		}
	}, [jiraSearch]);

	if (!id) {
		return <div>{i18n.translate('sorry-there-are-no-results-found')}</div>;
	}

	if (issueLoading) {
		return (
			<span className="cp-spinner ml-2 spinner-border spinner-border-sm"></span>
		);
	}

	if (!jiraIssue) {
		return <div>{i18n.translate('sorry-there-are-no-results-found')}</div>;
	}

	return (
		<div className="container-fluid container-fluid-max-xl sv-item">
			<div className="mb-3 sv-item-header">
				<div className="sv-breadcrumbs">
					<Link to="/">{i18n.translate('all-security-reports')}</Link>

					<span className="mx-2">/</span>

					{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.CVE_IDS]}
				</div>

				<div className="align-items-center d-flex my-3">
					<h1 className="mb-0">
						{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.CVE_IDS]}
					</h1>

					<span
						className={`sv-severity sv-severity-${jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.SEVERITY]?.toLowerCase()} text-center`}
					>
						{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.SEVERITY]}
					</span>
				</div>

				{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.SUMMARY] && (
					<span>
						{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.SUMMARY]}
					</span>
				)}
			</div>

			<div className="mb-3 row sv-issue-details">
				<div className="col-9">
					<div className="sv-item-description">
						<h6>{i18n.translate('summary')}</h6>

						<div>
							{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.DESCRIPTION]}
						</div>
					</div>
				</div>

				<div className="col-3">
					<div className="sv-item-details">
						{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.AFFECTS] && (
							<div className="mb-4">
								<h6>{i18n.translate('affects')}</h6>

								{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.AFFECTS]}
							</div>
						)}

						{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.CATEGORY] && (
							<div className="mb-4">
								<h6>{i18n.translate('category')}</h6>

								{
									jiraIssue[JiraEnum.FIELDS]?.[
										JiraEnum.CATEGORY
									]
								}
							</div>
						)}

						{jiraIssue[JiraEnum.FIELDS]?.[
							JiraEnum.CLASSIFICATION
						] && (
							<div className="mb-4">
								<h6>{i18n.translate('classification')}</h6>

								{
									jiraIssue[JiraEnum.FIELDS]?.[
										JiraEnum.CLASSIFICATION
									]
								}
							</div>
						)}

						{jiraIssue[JiraEnum.FIELDS]?.[
							JiraEnum.AFFECTED_VERSIONS
						] &&
							jiraIssue[JiraEnum.FIELDS]?.[
								JiraEnum.AFFECTED_VERSIONS
							].length > 0 && (
								<div className="mb-4">
									<h6>
										{i18n.translate('affected-versions')}
									</h6>

									{jiraIssue[JiraEnum.FIELDS]?.[
										JiraEnum.AFFECTED_VERSIONS
									]?.map((version) => (
										<div key={version}>{version}</div>
									))}
								</div>
							)}

						{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.FIX_VERSIONS] &&
							jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.FIX_VERSIONS]
								.length > 0 && (
								<div className="mb-4">
									<h6>{i18n.translate('fix-versions')}</h6>

									{jiraIssue[JiraEnum.FIELDS]?.[
										JiraEnum.FIX_VERSIONS
									]?.map((version) => (
										<div key={version}>{version}</div>
									))}
								</div>
							)}
					</div>
				</div>
			</div>

			{jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.FIX_VERSIONS] &&
				jiraIssue[JiraEnum.FIELDS]?.[JiraEnum.FIX_VERSIONS].length >
					0 &&
				jiraSearch && (
					<div className="sv-item-table">
						<h4 className="mb-3">
							{i18n.sub('additional-reports-fixed-in-x', [
								jiraIssue[JiraEnum.FIELDS]?.[
									JiraEnum.FIX_VERSIONS
								]
									?.map(
										(version, index, array) =>
											`${version}${index < array.length - 1 ? ', ' : ''}`
									)
									.join(''),
							])}
						</h4>

						{searchLoading ? (
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
				)}
		</div>
	);
};

export default SecurityVulnerabilitiesItem;
