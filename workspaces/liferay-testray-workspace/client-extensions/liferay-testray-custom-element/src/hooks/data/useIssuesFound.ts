/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import SearchBuilder from '../../core/SearchBuilder';
import {
	APIResponse,
	TestrayCaseResultIssue,
	TestrayIssue,
} from '../../services/rest';
import {testrayCaseResultsIssuesImpl} from '../../services/rest/TestrayCaseresultsIssues';
import {useFetch} from '../useFetch';

type useIssuesFoundProps = {
	buildId?: number;
	caseId?: number;
};

const useIssuesFound = ({buildId, caseId}: useIssuesFoundProps) => {
	const id = (buildId ?? caseId) as number;

	const {data} = useFetch<APIResponse<TestrayCaseResultIssue>>(
		testrayCaseResultsIssuesImpl.resource,
		{
			params: {
				fields: 'r_issueToCaseResultsIssues_c_issue.name',
				filter: SearchBuilder.eq(
					buildId
						? 'caseResultToCaseResultsIssues/r_buildToCaseResult_c_buildId'
						: 'caseResultToCaseResultsIssues/r_caseToCaseResult_c_caseId',
					id
				),
			},
			swrConfig: {
				shouldFetch: id,
			},
			transformData: (response) =>
				testrayCaseResultsIssuesImpl.transformDataFromList(response),
		}
	);

	const issues = useMemo(
		() => (data?.items ?? []).map(({issue}) => issue as TestrayIssue),
		[data?.items]
	);

	return issues;
};

export default useIssuesFound;
