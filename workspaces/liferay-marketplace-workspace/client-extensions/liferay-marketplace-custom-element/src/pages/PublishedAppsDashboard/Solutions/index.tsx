/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import solutionsIcon from '../../../assets/icons/analytics_icon.svg';
import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import {DashboardTable} from '../../../components/DashboardTable/DashboardTable';

const Solutions = () => (
	<DashboardPage
		messages={{
			description:
				'Manage solution trial and purchases from the Marketplace',
			title: 'My Solutions',
		}}
	>
		<DashboardTable
			emptyStateMessage={{
				description1: '',
				description2: '',
				title: 'No Solutions Yet',
			}}
			icon={solutionsIcon}
			items={[]}
			tableHeaders={[]}
		/>
	</DashboardPage>
);

export default Solutions;
