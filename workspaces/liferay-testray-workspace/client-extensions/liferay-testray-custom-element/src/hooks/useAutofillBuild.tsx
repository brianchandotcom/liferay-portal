/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext} from 'react';

import {BuildId, TestrayContext, TestrayTypes} from '../context/TestrayContext';

const useAutofillBuild = () => {
	const [{autofillBuild}, dispatch] = useContext(TestrayContext);

	const setBuildA = useCallback(
		(buildA: BuildId) =>
			dispatch({payload: buildA, type: TestrayTypes.SET_BUILD_A}),
		[dispatch]
	);

	const setBuildB = useCallback(
		(buildB: BuildId) =>
			dispatch({payload: buildB, type: TestrayTypes.SET_BUILD_B}),
		[dispatch]
	);

	return {
		autofillBuild: {
			...autofillBuild,
		},
		setBuildA,
		setBuildB,
	};
};

export default useAutofillBuild;
