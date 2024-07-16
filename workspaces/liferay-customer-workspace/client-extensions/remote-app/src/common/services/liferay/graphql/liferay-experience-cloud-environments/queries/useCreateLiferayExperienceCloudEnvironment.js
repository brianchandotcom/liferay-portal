/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useMutation} from '@apollo/client';

export const CREATE_LIFERAY_EXPERIENCE_CLOUD_ENVIRONMENT = gql`
	mutation createLiferayExperienceCloudEnvironment(
		$LiferayExperienceCloudEnvironment: InputC_LiferayExperienceCloudEnvironment!
	) {
		createLiferayExperienceCloudEnvironment(
			input: $LiferayExperienceCloudEnvironment
		)
			@rest(
				method: "POST"
				type: "C_LiferayExperienceCloudEnvironment"
				path: "/c/liferayexperiencecloudenvironments"
			) {
			accountKey
			id
			incidentManagementEmailAddress
			incidentManagementFullName
			primaryRegion
			projectId
		}
	}
`;

export function useCreateLiferayExperienceCloudEnvironments(
	variables,
	options = {displaySuccess: false}
) {
	return useMutation(
		CREATE_LIFERAY_EXPERIENCE_CLOUD_ENVIRONMENT,
		{
			context: {
				displaySuccess: options.displaySuccess,
				type: 'liferay-rest',
			},
		},
		variables
	);
}
