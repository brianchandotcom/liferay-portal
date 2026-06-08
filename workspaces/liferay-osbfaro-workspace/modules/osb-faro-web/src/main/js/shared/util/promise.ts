/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function sequence(fns: Array<(value: any) => Promise<any>>) {
	return (value?: any) =>
		fns.reduce(
			(result, fn) => result.then((error) => error || fn(value)),
			Promise.resolve<any>(undefined)
		);
}
