/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export const EXPERIMENT_MUTATION = gql`
	mutation ExperimentMutation(
		$experimentId: String!
		$experimentSettings: ExperimentSettings
		$publishedDXPVariantId: String
		$status: String!
	) {
		experiment(
			experimentId: $experimentId
			experimentSettings: $experimentSettings
			publishedDXPVariantId: $publishedDXPVariantId
			status: $status
		) {
			id
		}
	}
`;

export const EXPERIMENT_DELETE_MUTATION = gql`
	mutation ExperimentDeleteMutation($experimentId: String!) {
		deleteExperiment(experimentId: $experimentId)
	}
`;
