/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createStore} from '@xstate/store';

const context = {
	replacements: {} as Record<string, string>,
};

export const breadcrumbStore = createStore({
	context,
	on: {
		setReplacements: (
			context,
			event: {replacements: Record<number | string, string>}
		) => {
			return {
				...context,
				replacements: {
					...context.replacements,
					...event.replacements,
				},
			};
		},
	},
});
