/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const ONE_SITE_FRIENDLY_URL = '/web/one';
export const ONE_MARKETPLACE_URL = `${ONE_SITE_FRIENDLY_URL}/marketplace`;
export const ONE_SUPPORT_URL = `${ONE_SITE_FRIENDLY_URL}/support`;
export const ONE_ADMIN_URL = `${ONE_SITE_FRIENDLY_URL}/admin`;

export const DEFAULT_ADMIN_EMAIL =
	process.env.ONE_ADMIN_EMAIL ?? 'test@liferay.com';
export const DEFAULT_ADMIN_PASSWORD = process.env.ONE_ADMIN_PASSWORD ?? 'test';

export const SPRING_BOOT_URL =
	process.env.SPRING_BOOT_URL ?? 'http://localhost:58081';
