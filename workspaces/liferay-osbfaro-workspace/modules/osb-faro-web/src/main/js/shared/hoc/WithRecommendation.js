/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import {compose} from 'redux';
import RecommendationQuery from '~/settings/recommendations/queries/RecommendationQuery';
import {withError, withLoading, withNull} from '~/shared/hoc/util';
import {safeResultToProps} from '~/shared/util/mappers';
import {Routes} from '~/shared/util/router';

const withRecommendation = compose(
	graphql(RecommendationQuery, {
		options: ({
			router: {
				params: {jobId},
			},
		}) => ({
			variables: {
				jobId,
			},
		}),
		props: safeResultToProps(({jobById}) => ({job: jobById})),
	}),
	withLoading(),
	withError({page: true}),
	withNull('job', {
		entityType: Liferay.Language.get('recommendation-model'),
		linkRoute: Routes.SETTINGS_RECOMMENDATIONS,
	})
);

export default withRecommendation;
