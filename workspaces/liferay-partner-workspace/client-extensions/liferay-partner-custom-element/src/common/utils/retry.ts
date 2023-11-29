/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function retry<T>(
	operation: () => Promise<Response>,
	maxRetryCount: number = 5,
	delay: number = 400
): Promise<T> {
	const result = await operation();

	if (result.ok) {
		return result.json() as T;
	}

	if (maxRetryCount > 0) {
		await new Promise((resolve) => setTimeout(resolve, delay));

		return retry(operation, maxRetryCount - 1, delay * maxRetryCount);
	}
	else {
		throw new Error();
	}
}
