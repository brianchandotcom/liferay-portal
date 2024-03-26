/**
import { useFetch } from '../../hooks/useFetch';
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';
import {useFetch} from '~/hooks/useFetch';
import {TestrayComponent, TestrayTeam} from '~/services/rest';

export type CompareRunsResponse = {
	component?: TestrayComponent;
	team?: TestrayTeam;
	values: number[][];
};

const mockComponent = {
	dateCreated: '',
	dateModified: '',
	externalReferenceCode: '',
	id: 0,
	name: 'Liferay',
	originationKey: '',
	r_teamToComponents_c_teamId: 0,
	status: '',
	teamId: 0,
};

const mockTeam = {
	dateCreated: '',
	dateModified: '',
	externalReferenceCode: '',
	id: 0,
	name: 'Solutions',
};

const useCompareRuns = (type: 'components' | 'details' | 'teams') => {
	const {runA, runB} = useParams();

	const {data: runsData} = useFetch<any>(
		`/testray-run-comparisons/${runA}/${runB}`
	);

	return [
		{
			runsData,
			...(type === 'components' && {component: mockComponent}),
			...(type === 'teams' && {team: mockTeam}),
		},
	];
};

export default useCompareRuns;
