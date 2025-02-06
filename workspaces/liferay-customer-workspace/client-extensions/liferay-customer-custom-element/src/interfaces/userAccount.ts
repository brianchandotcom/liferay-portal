/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import IAccountBrief from './accountBrief';
import IOrganizationBrief from './organizationBrief';
import IRoleBrief from './roleBrief';

export default interface IUserAccount {
	accountBriefs?: IAccountBrief[];
	accountKey?: string;
	code?: string;
	email?: string;
	familyName?: string;
	firstName?: string;
	givenName?: string;
	id?: number;
	isAccountAdmin: boolean;
	isOmniAdmin: boolean;
	isProvisioning: boolean;
	isStaff: boolean;
	lastName?: string;
	organizationBriefs?: IOrganizationBrief[];
	partnershipCurrent?: string;
	partnershipCurrentEndDate?: string;
	partnershipExpired?: string;
	partnershipExpiredEndDate?: string;
	partnershipFuture?: string;
	partnershipFutureStartDate?: string;
	region: string;
	roleBriefs?: IRoleBrief[];
	screenName?: string;
	slaCurrent?: string;
	slaCurrentEndDate?: string;
	slaExpired?: string;
	slaExpiredEndDate?: string;
	slaFuture?: string;
	slaFutureStartDate?: string;
	status: string;
	userId?: number;
	userName?: string;
	uuid?: string;
}
