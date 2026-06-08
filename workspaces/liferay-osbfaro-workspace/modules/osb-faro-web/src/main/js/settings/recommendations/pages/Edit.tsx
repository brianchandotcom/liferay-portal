/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get} from 'lodash';
import React from 'react';
import {compose} from 'redux';
import BasePage from '~/settings/components/base-page/BasePage';
import {withAdminPermission} from '~/shared/hoc';
import withRecommendation from '~/shared/hoc/WithRecommendation';
import {Router} from '~/shared/types';
import {getRecommendations} from '~/shared/util/breadcrumbs';
import {Routes, toRoute} from '~/shared/util/router';

import RecommendationStepCard from '../components/recommendation-step-card';
import {Job} from '../utils/utils';

interface IEditProps {
	job: Job;
	router: Router;
}

const Edit: React.FC<IEditProps> = ({job, router}) => {
	const {groupId = '', jobId} = router.params;

	const name = get(job, 'name');

	return (
		<BasePage
			breadcrumbItems={[
				getRecommendations({groupId}),
				{
					active: true,
					label: name,
				},
			]}
			pageDescription={Liferay.Language.get(
				'item-similarity-model-uses-items-and-iteractions-for-training'
			)}
			pageTitle={name}
		>
			<div className="row">
				<div className="col-xl-8">
					<RecommendationStepCard
						cancelHref={toRoute(
							Routes.SETTINGS_RECOMMENDATION_MODEL_VIEW,
							{
								groupId,
								jobId,
							}
						)}
						job={job}
						router={router}
					/>
				</div>
			</div>
		</BasePage>
	);
};

export default compose<React.ComponentType<any>>(
	withAdminPermission,
	withRecommendation
)(Edit);
