/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useMutation} from '@apollo/client';

export const CREATE_ADMIN_LIFERAY_EXPERIENCE_CLOUD = gql`
	mutation createAdminLiferayExperienceCloud(
		$AdminLiferayExperienceCloud: InputC_AdminLiferayExperienceCloud!
	) {
		createAdminLiferayExperienceCloud(input: $AdminLiferayExperienceCloud)
			@rest(
				method: "POST"
				type: "C_AdminLiferayExperienceCloud"
				path: "/c/adminliferayexperienceclouds"
			) {
			emailAddress
			fullName
			githubUsername
			liferayExperienceCloudEnvironmentId
		}
	}
`;

export function useCreateAdminLiferayExperienceCloud(
	variables,
	options = {displaySuccess: false}
) {
	return useMutation(
		CREATE_ADMIN_LIFERAY_EXPERIENCE_CLOUD,
		{
			context: {
				displaySuccess: options.displaySuccess,
				type: 'liferay-rest',
			},
		},
		variables
	);
}
