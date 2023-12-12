/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate, useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import useSuiteActions from './useSuiteActions';

const Suites = () => {
	const navigate = useNavigate();
	const {projectId} = useParams();

	const {actions} = useSuiteActions();

	return (
		<Container>
			<ListView
				managementToolbarProps={{
					addButton: () => navigate('create'),
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
							key: 'description',
							value: i18n.translate('description'),
						},
						{
							key: 'caseParameters',
							render: (caseParameters) =>
								i18n.translate(
									caseParameters ? 'smart' : 'static'
								),
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
