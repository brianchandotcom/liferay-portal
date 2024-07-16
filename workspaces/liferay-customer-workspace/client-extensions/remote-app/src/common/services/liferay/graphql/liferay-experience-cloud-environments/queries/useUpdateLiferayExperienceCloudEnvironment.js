/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useMutation} from '@apollo/client';

export const UPDATE_LIFERAY_EXPERIENCE_CLOUD_ENVIRONMENT = gql`
	mutation updateLiferayExperienceCloudEnvironment(
		$liferayExperienceCloudEnvironmentId: Long!
		$LiferayExperienceCloudEnvironment: InputC_LiferayExperienceCloudEnvironment!
	) {
		updateLiferayExperienceCloudEnvironment(
			liferayExperienceCloudEnvironmentId: $liferayExperienceCloudEnvironmentId
			input: $LiferayExperienceCloudEnvironment
		)
			@rest(
				method: "PUT"
				type: "C_LiferayExperienceCloudEnvironment"
				path: "/c/liferayexperiencecloudenvironments/{args.liferayExperienceCloudEnvironmentId}"
			) {
			liferayExperienceCloudEnvironmentId
			projectId
		}
	}
`;

export function useUpdateLiferayExperienceCloudEnvironment(
	variables,
	options = {displaySuccess: false}
) {
	return useMutation(
		UPDATE_LIFERAY_EXPERIENCE_CLOUD_ENVIRONMENT,
		{
			context: {
				displaySuccess: options.displaySuccess,
				type: 'liferay-rest',
			},
		},
		variables
	);
}
