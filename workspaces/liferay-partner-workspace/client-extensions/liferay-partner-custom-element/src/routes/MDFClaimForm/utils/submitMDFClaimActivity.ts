/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimActivity from '../../../common/interfaces/mdfClaimActivity';
import createMDFClaimActivity from '../../../common/services/liferay/object/claim-activity/createMDFClaimActivity';
import updateMDFClaimActivity from '../../../common/services/liferay/object/claim-activity/updateMDFClaimActivity';

const submitMDFClaimActivity = async (
	mdfClaimActivity: MDFClaimActivity,
	companyId: number,
	dtoMDFClaimId: number
) => {
	const dtoMDFClaimActivity = mdfClaimActivity.id
		? await updateMDFClaimActivity(
				mdfClaimActivity,
				dtoMDFClaimId,
				companyId
		  )
		: await createMDFClaimActivity(
				mdfClaimActivity,
				dtoMDFClaimId,
				companyId
		  );

	return dtoMDFClaimActivity;
};

export default submitMDFClaimActivity;
