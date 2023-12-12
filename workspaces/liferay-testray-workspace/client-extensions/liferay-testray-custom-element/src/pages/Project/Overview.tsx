/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import Container from '../../components/Layout/Container';
import QATable from '../../components/Table/QATable';
import i18n from '../../i18n';
import {TestrayProject} from '../../services/rest';
import dayjs from '../../util/date';

const Overview = () => {
	const {testrayProject} = useOutletContext<{
		testrayProject: TestrayProject;
	}>();

	return (
		<Container title={i18n.translate('overview')}>
			<QATable
				items={[
					{
						title: i18n.translate('created-by'),
						value: testrayProject.creator.name,
					},
					{
						title: i18n.translate('date-created'),
						value: dayjs(testrayProject.dateCreated).format('lll'),
					},
					{
						title: i18n.translate('description'),
						value: testrayProject.description,
					},
				]}
			/>
		</Container>
	);
};

export default Overview;
