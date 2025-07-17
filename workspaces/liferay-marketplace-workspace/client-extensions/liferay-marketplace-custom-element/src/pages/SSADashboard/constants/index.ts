/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const EXTEND_OPTIONS = [
	{
		actionText: 'Submit',
		actionUrl: '',
		alertText: `The trial can be extended automatically once, with immediate effect. After that, any further extension will require admin approval.`,
		alertType: 'info',
		extendType: 'auto-extend',
	},
	{
		actionText: 'Submit Request',
		actionUrl: '',
		alertText: `You've already extended your trial once. To extend it again, you’ll need to submit a request to your admin.`,
		alertType: 'warning',
		extendType: 'admin-request',
	},
] as const;
