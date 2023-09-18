/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayAccountBrief from './liferayAccountBrief';
import Role from './role';

interface Telephone {
	id: number;
	phoneNumber: string;
}

interface UserAccountContactInformation {
	telephones: Telephone[];
}

export default interface UserAccount {
	accountBriefs: LiferayAccountBrief[];
	emailAddress: string;
	familyName: string;
	givenName: string;
	id: number;
	roleBriefs: Role[];
	userAccountContactInformation: UserAccountContactInformation;
}
