/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {Outlet, useLocation, useParams} from 'react-router-dom';
import PageRenderer from '~/components/PageRenderer';

import {useFetch} from '../../../../../../hooks/useFetch';
import useHeader from '../../../../../../hooks/useHeader';
import i18n from '../../../../../../i18n';
import {
	TestrayCaseResult,
	liferayMessageBoardImpl,
	testrayCaseResultImpl,
} from '../../../../../../services/rest';
import useCaseResultActions from './useCaseResultActions';

const CaseResultOutlet = () => {
	const {actions} = useCaseResultActions();
	const {pathname} = useLocation();
	const {caseResultId} = useParams();

	const isEditCase = pathname.includes('edit');

	const {
		data: testrayCaseResult,
		error,
		loading,
		mutate: mutateCaseResult,
	} = useFetch<TestrayCaseResult>(
		testrayCaseResultImpl.getResource(caseResultId as string),
		{
			transformData: (response) =>
				testrayCaseResultImpl.transformData(response),
		}
	);

	const {data: mbMessage} = useFetch(
		testrayCaseResult?.mbMessageId
			? liferayMessageBoardImpl.getMessagesIdURL(
					testrayCaseResult.mbMessageId
				)
			: null
	);

	const testrayBuild = testrayCaseResult?.build;

	const testrayProject = testrayBuild?.project;
	const testrayRoutine = testrayBuild?.routine;

	const basePath = `/project/${testrayProject?.id}/routines/${testrayRoutine?.id}/build/${testrayBuild?.id}/case-result/${caseResultId}`;

	const {setHeaderActions, setHeading, setTabs} = useHeader({
		timeout: 100,
	});

	useEffect(() => {
		setHeaderActions({
			actions,
			item: testrayCaseResult,
			mutate: mutateCaseResult,
		});
	}, [actions, testrayCaseResult, mutateCaseResult, setHeaderActions]);

	useEffect(() => {
		if (testrayCaseResult?.case?.name) {
			setHeading([
				{
					category: i18n.translate('project').toUpperCase(),
					path: `/project/${testrayProject?.id}/routines`,
					title: String(testrayProject?.name),
				},
				{
					category: i18n.translate('routine').toUpperCase(),
					path: `/project/${testrayProject?.id}/routines/${testrayRoutine?.id}`,
					title: String(testrayRoutine?.name),
				},
				{
					category: i18n.translate('build').toUpperCase(),
					path: `/project/${testrayProject?.id}/routines/${testrayRoutine?.id}/build/${testrayBuild?.id}`,
					title: String(testrayBuild?.name),
				},
				{
					category: i18n.translate('case-result'),
					title: testrayCaseResult?.case?.name || '',
				},
			]);
		}
	}, [
		setHeading,
		testrayProject,
		testrayRoutine,
		testrayBuild,
		testrayCaseResult,
	]);

	useEffect(() => {
		setTabs(
			isEditCase
				? []
				: [
						{
							active: !pathname.includes('history'),
							path: basePath,
							title: i18n.translate('result'),
						},
						{
							active: pathname.includes('history'),
							path: `${basePath}/history?${new URLSearchParams({
								filter: JSON.stringify({
									testrayRoutineIds: [testrayRoutine?.id],
								}),
								filterSchema: 'buildResultsHistory',
							})}`,
							title: i18n.translate('history'),
						},
					]
		);
	}, [basePath, isEditCase, pathname, setTabs, testrayRoutine?.id]);

	return (
		<PageRenderer error={error} loading={loading}>
			<Outlet
				context={{
					actions: testrayCaseResult?.actions,
					caseResult: testrayCaseResult,
					mbMessage,
					mutateCaseResult,
					projectId: testrayProject?.id,
				}}
			/>
		</PageRenderer>
	);
};

export default CaseResultOutlet;
