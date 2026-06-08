/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const mockEmptyState = {
	empty: true,
	error: false,
	loading: false,
};

export const mockErrorState = {
	empty: false,
	error: true,
	loading: false,
};

export const mockLoadingState = {
	empty: false,
	error: false,
	loading: true,
};

export const mockSuccessState = {
	empty: false,
	error: false,
	loading: false,
};
