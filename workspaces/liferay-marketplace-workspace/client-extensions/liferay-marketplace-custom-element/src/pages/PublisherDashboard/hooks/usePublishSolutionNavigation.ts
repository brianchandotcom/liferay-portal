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

	const paths = location.pathname.split('/');
	const lastPath = paths.at(id ? -2 : -1);

	let activeIndex = SOLUTION_FLOW_ITEMS.findIndex(
		({path}) => path === lastPath
	);

	if (activeIndex === -1) {
		activeIndex = 0;
	}

	const activeRoute =
		SOLUTION_FLOW_ITEMS[activeIndex] || SOLUTION_FLOW_ITEMS[0];

	const onClickPrevious = () => {
		navigate(SOLUTION_FLOW_ITEMS[activeIndex - 1].path);
	};

	const onClickContinue = () => {
		navigate(SOLUTION_FLOW_ITEMS[activeIndex + 1].path);
	};

	const onExit = () => navigate('../solutions');

	return {
		activeIndex,
		activeRoute,
		onClickContinue,
		onClickPrevious,
		onExit,
	};
};

export default usePublishSolutionNavigation;
