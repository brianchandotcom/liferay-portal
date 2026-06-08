/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query Recommendation($jobId: String!) {
		jobById(id: $jobId) {
			id
			name
			nextRunDate
			parameters {
				name
				value
			}
			runDataPeriod
			runDate
			runFrequency
			status
			type
		}
	}
`;

export const RECOMMENDATION_BY_NAME_QUERY = gql`
	query Recommendation($name: String!) {
		jobByName(name: $name) {
			id
			name
		}
	}
`;
