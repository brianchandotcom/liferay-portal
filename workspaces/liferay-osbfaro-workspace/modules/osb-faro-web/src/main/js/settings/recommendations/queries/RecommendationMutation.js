/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export const RECOMMENDATION_MUTATION = gql`
	mutation RecommendationMutation(
		$name: String!
		$parameters: [JobParameterInput]
		$runDataPeriod: JobRunDataPeriod
		$runFrequency: JobRunFrequency
		$runNow: Boolean
		$type: JobType!
	) {
		createJob(
			name: $name
			parameters: $parameters
			runDataPeriod: $runDataPeriod
			runFrequency: $runFrequency
			runNow: $runNow
			type: $type
		) {
			id
			name
			parameters {
				name
				value
			}
			status
			runDataPeriod
			runDate
			runFrequency
			type
		}
	}
`;

export const RECOMMENDATION_DELETE_MUTATION = gql`
	mutation RecommendationDeleteMutation($jobIds: [String]!) {
		deleteJobs(jobIds: $jobIds)
	}
`;

export const RECOMMENDATION_RUN_MUTATION = gql`
	mutation RecommendationRunMutation(
		$jobId: String!
		$runDataPeriod: JobRunDataPeriod!
	) {
		runJob(jobId: $jobId, runDataPeriod: $runDataPeriod) {
			id
		}
	}
`;

export const RECOMMENDATION_UPDATE_MUTATION = gql`
	mutation RecommendationUpdateMutation(
		$jobId: String!
		$name: String!
		$parameters: [JobParameterInput]
		$runDataPeriod: JobRunDataPeriod
		$runFrequency: JobRunFrequency
		$runNow: Boolean
	) {
		updateJob(
			jobId: $jobId
			name: $name
			parameters: $parameters
			runDataPeriod: $runDataPeriod
			runFrequency: $runFrequency
			runNow: $runNow
		) {
			id
			name
			parameters {
				name
				value
			}
			status
			runDataPeriod
			runDate
			runFrequency
			type
		}
	}
`;
