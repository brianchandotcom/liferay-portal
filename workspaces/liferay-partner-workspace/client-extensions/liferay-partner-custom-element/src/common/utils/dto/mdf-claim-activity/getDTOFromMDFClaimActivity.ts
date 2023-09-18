/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimActivityDTO from '../../../interfaces/dto/mdfClaimActivityDTO';
import MDFClaimActivity from '../../../interfaces/mdfClaimActivity';

export default function getDTOFromMDFClaimActivity(
	mdfClaimActivity: MDFClaimActivity,
	mdfClaimId: number,
	companyId: number
): MDFClaimActivityDTO {
	return {
		currency: mdfClaimActivity.currency,
		eventProgram: mdfClaimActivity.eventProgram?.documentId,
		listOfQualifiedLeads: mdfClaimActivity.listOfQualifiedLeads?.documentId,
		metrics: mdfClaimActivity.metrics,
		name: mdfClaimActivity.name,
		r_accToMDFClmActs_accountEntryId: companyId,
		r_actToMDFClmActs_c_activityId:
			mdfClaimActivity.r_actToMDFClmActs_c_activityId,
		r_mdfClmToMDFClmActs_c_mdfClaimId: mdfClaimId,
		selected: mdfClaimActivity.selected,
		telemarketingMetrics: mdfClaimActivity.telemarketingMetrics,
		telemarketingScript: mdfClaimActivity.telemarketingScript?.documentId,
		totalCost: mdfClaimActivity.totalCost,
		typeActivity: mdfClaimActivity.typeActivity,
		videoLink: mdfClaimActivity.videoLink,
	};
}
