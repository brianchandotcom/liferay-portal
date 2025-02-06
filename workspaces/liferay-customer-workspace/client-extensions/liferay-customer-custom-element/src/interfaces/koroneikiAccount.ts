/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default interface IKoroneikiAccount {
	accountKey: string;
	code: string;
	dxpVersion?: string;
	id: number;
	maxRequestors?: number;
	name: string;
	partnershipCurrent?: string;
	partnershipCurrentEndDate?: string;
	partnershipExpired?: string;
	partnershipExpiredEndDate?: string;
	partnershipFuture?: string;
	partnershipFutureStartDate?: string;
	region: string;
	slaCurrent?: string;
	slaCurrentEndDate?: string;
	slaExpired?: string;
	slaExpiredEndDate?: string;
	slaFuture?: string;
	slaFutureStartDate?: string;
	status: string;
}
