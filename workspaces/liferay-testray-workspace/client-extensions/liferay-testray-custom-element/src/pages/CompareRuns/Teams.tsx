/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';
import ListView from '~/components/ListView';

import i18n from '../../i18n';

const CompareRunsTeams = () => {
	const {runA: runAId, runB: runBId} = useParams();

	document.title = i18n.sub('compare-x', 'cases');

	return (
		<ListView
			managementToolbarProps={{
				applyFilters: true,
				display: {columns: false},
				filterSchema: 'compareRunsTeamsAndComponents',
			}}
			matrixProps={{title: 'Teams'}}
			resource={`/testray-run-comparisons/${runAId}/${runBId}`}
			tableProps={{visible: false}}
		/>
	);
};

export default CompareRunsTeams;
