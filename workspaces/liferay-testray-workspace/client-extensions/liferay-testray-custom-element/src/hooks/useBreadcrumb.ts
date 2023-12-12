/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useMemo, useRef, useState} from 'react';

import SearchBuilder from '../core/SearchBuilder';
import i18n from '../i18n';
import {
	APIResponse,
	TestrayCaseResult,
	TestrayRun,
	testrayCaseResultImpl,
} from '../services/rest';
import useDebounce from './useDebounce';
import {useFetch} from './useFetch';

export type Entity = {
	entity: string;
	getPage?: (ids: number[]) => string;
	getResource: (ids: number[], search: string) => string | null;
	name: string;
	transformer?: (response: APIResponse<any>) => any;
};

type BreadCrumb = {
	entity: Entity;
	label: string;
	value: number;
};

const normalizeItems = (response: APIResponse<any>) => ({
	...response,
	items: response.items.map(({id, name}) => ({
		label: name,
		value: id,
	})),
});

const getEntityUrlAndNormalizer = (
	{getResource = () => null, transformer}: Entity = {} as Entity,
	ids: number[],
	search: string
) => ({
	transformer: transformer ?? normalizeItems,
	url: getResource(ids, search),
});

const useBreadcrumb = (entities: Entity[], {active}: {active: boolean}) => {
	const inputRef = useRef<HTMLInputElement>(null);

	const [breadCrumb, setBreadCrumb] = useState<BreadCrumb[]>([]);
	const [search, setSearch] = useState('');
	const debouncedSearch = useDebounce(search, 500);

	const currentEntity = entities[breadCrumb.length];

	const ids = breadCrumb.map(({value}) => value);

	const {transformer, url} = getEntityUrlAndNormalizer(
		currentEntity,
		ids,
		debouncedSearch
	);

	const {data} = useFetch<APIResponse<any>>(url, {
		swrConfig: {shouldFetch: active},
		transformData: transformer,
	});

	const items = useMemo(() => data?.items || [], [data?.items]);

	const onBackscape = useCallback(() => {
		if (breadCrumb.length) {
			const lastBreadcrumb = breadCrumb[breadCrumb.length - 1];

			setBreadCrumb((prevBreadcrumb) =>
				prevBreadcrumb.filter(
					(_, index) => breadCrumb.length !== index + 1
				)
			);

			setTimeout(() => {
				setSearch(lastBreadcrumb.label);

				setTimeout(() => {
					inputRef.current?.select();
				}, 10);
			}, 10);
		}
	}, [breadCrumb]);

	const onClickRow = (rowIndex: number) => {
		setBreadCrumb((prevBreadCrumb) => [...prevBreadCrumb, items[rowIndex]]);
		setSearch('');
	};

	return {
		breadCrumb,
		currentEntity,
		entities,
		inputRef,
		items,
		onBackscape,
		onClickRow,
		search,
		setBreadCrumb,
		setSearch,
	};
};

const defaultEntities: Entity[] = [
	{
		entity: 'projects',
		getPage: ([projectId]) => `/project/${projectId}/routines`,
		getResource: (_, search) =>
			`/projects?filter=${SearchBuilder.contains(
				'name',
				search
			)}&fields=id,name&pageSize=100`,
		name: i18n.translate('project'),
	},
	{
		entity: 'routines',
		getPage: ([projectId, routineId]) =>
			`/project/${projectId}/routines/${routineId}`,
		getResource: ([projectId], search) =>
			`/routines?filter=${SearchBuilder.eq(
				'projectId',
				projectId
			)} and ${SearchBuilder.contains(
				'name',
				search
			)}&fields=id,name&pageSize=50`,
		name: i18n.translate('routine'),
	},
	{
		entity: 'builds',
		getPage: ([projectId, routineId, buildId]) =>
			`/project/${projectId}/routines/${routineId}/build/${buildId}`,
		getResource: ([, routineId], search) =>
			`/builds?filter=${new SearchBuilder()
				.eq('routineId', routineId)
				.and()
				.contains('name', search)
				.build()}&fields=id,name&pageSize=100`,
		name: i18n.translate('build'),
	},
	{
		entity: 'runs',
		getPage: ([projectId, routineId, buildId, runId]) =>
			`/project/${projectId}/routines/${routineId}/build/${buildId}?runId=${runId}`,
		getResource: ([, , buildId], search) =>
			`/runs?filter=${SearchBuilder.eq(
				'r_buildToRuns_c_buildId',
				buildId
			)} ${search ? `and number eq ${search}` : ''} &fields=id,number`,
		name: i18n.translate('run'),
		transformer: (response: APIResponse<TestrayRun>) => ({
			...response,
			items: response.items.map(({id, number}) => ({
				label: number,
				value: id,
			})),
		}),
	},
	{
		entity: 'caseresults',
		getPage: ([projectId, routineId, buildId, , caseResultsId]) =>
			`/project/${projectId}/routines/${routineId}/build/${buildId}/case-result/${caseResultsId}`,
		getResource: ([, , buildId, runId], search) =>
			`/caseresults?filter=${new SearchBuilder()
				.eq('buildId', buildId)
				.and()
				.eq('r_runToCaseResult_c_runId', runId)
				.and()
				.contains('caseToCaseResult/name', search)
				.build()}&nestedFields=case,r_runToCaseResult_c_runId&pageSize=20&fields=r_caseToCaseResult_c_case,id,run`,
		name: i18n.translate('case-result'),
		transformer: (response: APIResponse<TestrayCaseResult>) => {
			const transformedResponse = testrayCaseResultImpl.transformDataFromList(
				response
			);

			return {
				...transformedResponse,
				items: transformedResponse.items.map((caseResult) => ({
					...caseResult,
					label: caseResult.case?.name,
					run: caseResult.r_runToCaseResult_c_run?.id,
					value: caseResult.id,
				})),
			};
		},
	},
];

export {defaultEntities};

export default useBreadcrumb;
