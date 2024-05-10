/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import useSuiteActions from './useSuiteActions';

const Suites = () => {
	const {projectId} = useParams();

	const {actions} = useSuiteActions();

	return (
		<Container>
			<ListView
				managementToolbarProps={{
					applyFilters: true,
					filterSchema: 'suites',
					title: i18n.translate('suites'),
				}}
				resource="/suites"
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'name',
							sorteable: true,
							value: i18n.translate('suite-name'),
						},
						{
							clickable: true,
							key: 'description',
							value: i18n.translate('description'),
						},
						{
							clickable: true,
							key: 'type',
							render: (type) => i18n.translate(type),
							value: i18n.translate('type'),
						},
					],
					navigateTo: (suite) =>
						`/project/${projectId}/suites/${suite.id}`,
				}}
				variables={{
					filter: SearchBuilder.eq('projectId', projectId as string),
				}}
			/>
		</Container>
	);
};

export default Suites;
