/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	mutation DataControlRequest(
		$emailAddresses: [String]
		$fileName: String
		$ownerId: String!
		$types: [DataControlTaskType]!
		$userId: String!
		$userName: String!
	) {
		dataControlTasks(
			emailAddresses: $emailAddresses
			fileName: $fileName
			ownerId: $ownerId
			types: $types
			userId: $userId
			userName: $userName
		)
	}
`;
