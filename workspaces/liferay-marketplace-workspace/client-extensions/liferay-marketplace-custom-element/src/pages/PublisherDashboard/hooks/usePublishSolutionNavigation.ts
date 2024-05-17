/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLocation, useNavigate, useParams} from 'react-router-dom';

import {SOLUTION_FLOW_ITEMS} from '../pages/Solutions/constants';

const usePublishSolutionNavigation = () => {
	const location = useLocation();
	const navigate = useNavigate();
	const {id} = useParams();

	const [, ..._publishSolutionSteps] = SOLUTION_FLOW_ITEMS;

	const publishSolutionSteps = id
		? _publishSolutionSteps
		: SOLUTION_FLOW_ITEMS;

	const paths = location.pathname.split('/');
	const lastPath = paths.at(-1);

	let activeIndex = publishSolutionSteps.findIndex(
		({path}) => path === lastPath
	);

	const isLastStep = activeIndex + 1 === publishSolutionSteps.length;

	if (activeIndex === -1) {
		activeIndex = 0;
	}

	const activeRoute =
		publishSolutionSteps[activeIndex] || publishSolutionSteps[0];

	const onClickPrevious = () => {
		navigate(publishSolutionSteps[activeIndex - 1].path);
	};

	const onClickContinue = () => {
		navigate(publishSolutionSteps[activeIndex + 1].path);
	};

	const onExit = () => navigate('/solutions');

	return {
		activeIndex,
		activeRoute,
		id,
		isLastStep,
		onClickContinue,
		onClickPrevious,
		onExit,
		publishSolutionSteps,
	};
};

export default usePublishSolutionNavigation;
