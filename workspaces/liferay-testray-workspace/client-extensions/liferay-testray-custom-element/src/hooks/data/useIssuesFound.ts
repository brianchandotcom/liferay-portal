/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import SearchBuilder from '../../core/SearchBuilder';
import {
	APIResponse,
	TestrayCaseResult,
	testrayCaseResultImpl,
} from '../../services/rest';
import {useFetch} from '../useFetch';

type useIssuesFoundProps = {
	buildId?: number;
	caseId?: number;
};

const useIssuesFound = ({buildId, caseId}: useIssuesFoundProps) => {
	const id = (buildId ?? caseId) as number;

	const filter = useMemo(
		() =>
			new SearchBuilder()
				.eq(
					buildId
						? 'r_buildToCaseResult_c_buildId'
						: 'r_caseToCaseResult_c_caseId',
					id
				)
				.and()
				.ne('issues', null)
				.and()
				.ne('issues', '')
				.build(),
		[buildId, id]
	);

	const {data} = useFetch<APIResponse<TestrayCaseResult>>(
		testrayCaseResultImpl.resource,
		{
			params: {
				fields: 'issues',
				filter,
			},
			swrConfig: {
				shouldFetch: id,
			},
			transformData: (response) =>
				testrayCaseResultImpl.transformDataFromList(response),
		}
	);

	const issues = useMemo(
		() => (data?.items ?? []).map(({issues}) => issues),
		[data?.items]
	);

	return issues;
};

export default useIssuesFound;
