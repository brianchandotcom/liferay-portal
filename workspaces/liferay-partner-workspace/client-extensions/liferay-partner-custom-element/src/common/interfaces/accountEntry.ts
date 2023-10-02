/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayObject from './liferayObject';

export default interface AccountEntry extends Partial<LiferayObject> {
	currency: string;
	externalReferenceCode: string;
	id: number;
	marketingPerformance: number;
	marketingPlan: boolean;
	name: string;
	newProjectExistingBusiness: number;
	partnerCountry: string;
	r_prtLvlToAcc_c_partnerLevelERC: string;
	solutionDeliveryCertification: boolean;
}
