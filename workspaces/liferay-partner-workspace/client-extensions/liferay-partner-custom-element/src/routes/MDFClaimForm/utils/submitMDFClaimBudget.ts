/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimBudget from '../../../common/interfaces/mdfClaimBudget';
import createMDFClaimActivityBudget from '../../../common/services/liferay/object/claim-budgets/createMDFClaimActivityBudget';
import updateMDFClaimActivityBudget from '../../../common/services/liferay/object/claim-budgets/updateMDFClaimActivityBudget';

const submitMDFClaimBudget = async (
	mdfClaimBudget: MDFClaimBudget,
	companyId: number,
	dtoMDFClaimActivityId: number
) => {
	const dtoMDFClaimBudget = mdfClaimBudget.id
		? await updateMDFClaimActivityBudget(
				mdfClaimBudget,
				dtoMDFClaimActivityId,
				companyId
		  )
		: await createMDFClaimActivityBudget(
				mdfClaimBudget,
				dtoMDFClaimActivityId,
				companyId
		  );

	return dtoMDFClaimBudget;
};

export default submitMDFClaimBudget;
