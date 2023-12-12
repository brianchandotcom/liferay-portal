/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {Outlet, useOutletContext, useParams} from 'react-router-dom';
import PageRenderer from '~/components/PageRenderer';

import {useHeader} from '../../../hooks';
import {useFetch} from '../../../hooks/useFetch';
import i18n from '../../../i18n';
import {
	TestrayProject,
	TestrayRequirement,
	testrayRequirementsImpl,
} from '../../../services/rest';
import useRequirementActions from './useRequirementActions';

const RequirementsOutlet = () => {
	const {actions} = useRequirementActions({isHeaderActions: true});
	const {requirementId} = useParams();
	const {
		testrayProject,
	}: {testrayProject: TestrayProject} = useOutletContext();

	const {data: testrayRequirement, error, loading, mutate} = useFetch<
		TestrayRequirement
	>(testrayRequirementsImpl.getResource(requirementId as string), {
		transformData: (response: TestrayRequirement) =>
			testrayRequirementsImpl.transformData(response),
	});

	const {setHeaderActions, setHeading} = useHeader({
		timeout: 100,
	});

	useEffect(() => {
		setHeaderActions({actions, item: testrayRequirement, mutate});
	}, [actions, mutate, setHeaderActions, testrayRequirement]);

	useEffect(() => {
		if (testrayRequirement && testrayProject) {
			setHeading([
				{
					category: i18n.translate('project').toUpperCase(),
					path: `/project/${testrayProject.id}/requirements`,
					title: testrayProject.name,
				},
				{
					category: i18n.translate('requirement').toUpperCase(),
					path: `/project/${testrayProject.id}/requirements/${testrayRequirement.id}`,
					title: testrayRequirement?.summary,
				},
			]);
		}
	}, [testrayProject, setHeading, testrayRequirement]);

	return (
		<PageRenderer error={error} loading={loading}>
			<Outlet
				context={{
					actions: testrayRequirement?.actions,
					mutateTestrayRequirement: mutate,
					testrayProject,
					testrayRequirement,
				}}
			/>
		</PageRenderer>
	);
};

export default RequirementsOutlet;
