/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLocation, useNavigate, useParams} from 'react-router-dom';

const usePublishNavigation = ({
	exitLink,
	flowItems,
}: {
	exitLink: string;
	flowItems: any[];
}) => {
	const location = useLocation();
	const navigate = useNavigate();
	const {id} = useParams();

	const [, ...steps] = flowItems;

	const publishAppSteps = id ? steps : flowItems;

	const paths = location.pathname.split('/');
	const lastPath = paths.at(-1);

	let activeIndex = publishAppSteps.findIndex(({path}) => path === lastPath);

	const isLastStep = activeIndex + 1 === publishAppSteps.length;

	if (activeIndex === -1) {
		activeIndex = 0;
	}

	const activeRoute = publishAppSteps[activeIndex] || publishAppSteps[0];

	const onClickPrevious = () => {
		navigate(publishAppSteps[activeIndex - 1].path);
	};

	const onClickContinue = () => {
		navigate(publishAppSteps[activeIndex + 1].path);
	};

	const onExit = () => navigate(exitLink);

	return {
		activeIndex,
		activeRoute,
		id,
		isLastStep,
		onClickContinue,
		onClickPrevious,
		onExit,
		steps: publishAppSteps,
	};
};

export default usePublishNavigation;
