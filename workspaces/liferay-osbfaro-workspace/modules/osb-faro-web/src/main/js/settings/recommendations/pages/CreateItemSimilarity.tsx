/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BasePage from '~/settings/components/base-page/BasePage';
import {withAdminPermission} from '~/shared/hoc';
import {Router} from '~/shared/types';
import {getRecommendations} from '~/shared/util/breadcrumbs';
import {JobTypes} from '~/shared/util/constants';
import {Routes, toRoute} from '~/shared/util/router';

import RecommendationStepCard from '../components/recommendation-step-card';

interface ICreateItemSimilarityProps {
	router: Router;
}

const CreateItemSimilarity: React.FC<ICreateItemSimilarityProps> = ({
	router,
}) => {
	const {groupId = ''} = router.params;

	return (
		<BasePage
			breadcrumbItems={[
				getRecommendations({groupId}),
				{
					active: true,
					label: Liferay.Language.get('new-model'),
				},
			]}
			pageDescription={Liferay.Language.get(
				'item-similarity-model-uses-items-and-iteractions-for-training'
			)}
			pageTitle={Liferay.Language.get('new-item-similarity-model')}
		>
			<div className="row">
				<div className="col-xl-8">
					<RecommendationStepCard
						cancelHref={toRoute(Routes.SETTINGS_RECOMMENDATIONS, {
							groupId,
						})}
						jobType={JobTypes.ItemSimilarity}
						router={router}
					/>
				</div>
			</div>
		</BasePage>
	);
};

export default withAdminPermission(CreateItemSimilarity);
