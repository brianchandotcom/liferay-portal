/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BasePage from '~/settings/components/base-page/BasePage';
import {Router} from '~/shared/types';

import RecommendationList from '../hocs/RecommendationList';

interface IRecommendationsProps {
	history: {
		push: (value: string) => void;
	};
	router: Router;
}

const Recommendations: React.FC<IRecommendationsProps> = ({
	history,
	router,
}) => {
	const {groupId} = router.params;

	return (
		<BasePage
			pageDescription={Liferay.Language.get(
				'create-and-train-machine-learning-models-to-use-in-your-recommendations'
			)}
			pageTitle={Liferay.Language.get('recommendations')}
		>
			<RecommendationList
				groupId={groupId}
				history={history}
				router={router}
			/>
		</BasePage>
	);
};

export default Recommendations;
