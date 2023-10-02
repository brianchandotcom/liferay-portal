/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivityDTO from '../../../common/interfaces/dto/mdfRequestActivityDTO';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import MDFRequestBudget from '../../../common/interfaces/mdfRequestBudget';
import createMDFRequestActivityBudget from '../../../common/services/liferay/object/budgets/createMDFRequestActivityBudgets';
import updateMDFRequestActivityBudget from '../../../common/services/liferay/object/budgets/updateMDFRequestActivityBudgets';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

const submitMDFRequestBudget = async (
	budget: MDFRequestBudget,
	activityDTO: MDFRequestActivityDTO,
	mdfRequest: MDFRequest
) => {
	const dtoMDFClaimBudget = budget.id
		? await updateMDFRequestActivityBudget(
				ResourceName.BUDGET,
				budget,
				activityDTO,
				mdfRequest
		  )
		: await createMDFRequestActivityBudget(
				ResourceName.BUDGET,
				budget,
				activityDTO,
				mdfRequest
		  );

	return dtoMDFClaimBudget;
};

export default submitMDFRequestBudget;
