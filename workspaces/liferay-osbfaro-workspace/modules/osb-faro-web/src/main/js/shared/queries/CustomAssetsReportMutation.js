/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	mutation CustomAssetsReportMutation(
		$dashboardId: String!
		$definition: String!
		$modifiedByUserName: String!
		$modifiedByUserId: String!
	) {
		dashboard(
			dashboardId: $dashboardId
			definition: $definition
			modifiedByUserName: $modifiedByUserName
			modifiedByUserId: $modifiedByUserId
		) {
			assetId
			assetTitle
			definition
			id
		}
	}
`;
