/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import i18n from '../../i18n';
import {Action} from '../../types';

type UseTestflowAssign = {
	setUserIds: React.Dispatch<React.SetStateAction<number[]>>;
};

const useTestFlowAssign = ({setUserIds}: UseTestflowAssign) => {
	const actions: Action[] = [
		{
			action: ({id}: {id: number}) => {
				setUserIds((userIds: number[]) =>
					userIds.filter((userId) => userId !== id)
				);
			},
			name: i18n.translate('remove'),
		},
	];

	return {
		actions,
	};
};

export default useTestFlowAssign;
