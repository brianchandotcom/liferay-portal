/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLocation, useNavigate, useParams} from 'react-router-dom';
import { APP_FLOW_ITEMS } from '../pages/NewAppFlow/constants';


const usePublishAppNavigation = () => {
	const location = useLocation();
	const navigate = useNavigate();
	const {id} = useParams();

	const [, ..._publishAppSteps] = APP_FLOW_ITEMS;

	const publishAppSteps = id
		? _publishAppSteps
		: APP_FLOW_ITEMS;

	const paths = location.pathname.split('/');
	const lastPath = paths.at(-1);

	let activeIndex = publishAppSteps.findIndex(
		({path}) => path === lastPath
	);

	const isLastStep = activeIndex + 1 === publishAppSteps.length;

	if (activeIndex === -1) {
		activeIndex = 0;
	}

	const activeRoute =
    publishAppSteps[activeIndex] || publishAppSteps[0];

	const onClickPrevious = () => {
		navigate(publishAppSteps[activeIndex - 1].path);
	};

	const onClickContinue = () => {
		navigate(publishAppSteps[activeIndex + 1].path);
	};

	const onExit = () => navigate('/apps');

	return {
		activeIndex,
		activeRoute,
		id,
		isLastStep,
		onClickContinue,
		onClickPrevious,
		onExit,
		publishAppSteps,
	};
};

export default usePublishAppNavigation;
