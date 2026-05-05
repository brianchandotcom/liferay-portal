/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const baseAttributes = ['account-id', 'user-id'] as const;

export type Properties = {
	accountId: string | null;
	userId: string | null;
};

export function getAttributes(element: HTMLElement): Properties {
	return {
		accountId: element.getAttribute('account-id'),
		userId: element.getAttribute('user-id'),
	};
}
