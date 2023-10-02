/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayPicklist from '../../../../../common/interfaces/liferayPicklist';
import MDFRequestActivity from '../../../../../common/interfaces/mdfRequestActivity';
import {Status} from '../../../../../common/utils/constants/status';

export default function getNewActivity(
	claimPercent: number,
	currency: LiferayPicklist
): MDFRequestActivity {
	return {
		activityDescription: {
			activityPromotion: '',
			ad: '',
			assetsLiferayDescription: '',
			assetsLiferayRequired: '',
			audienceTarget: '',
			broadcastChannel: '',
			cta: '',
			description: '',
			detailsLeadFollowUp: '',
			expectedImpressions: '',
			gatedLandingPage: '',
			goalOfContent: '',
			guaranteedImpressions: '',
			hiringOutsideWriterOrAgency: '',
			howLiferayBrandUsed: '',
			keywordsForPPCCampaigns: '',
			landingPageCopy: '',
			leadFollowUpStrategies: [],
			leadGenerated: '',
			liferayBranding: '',
			liferayParticipationRequirements: '',
			location: '',
			manySeries: '',
			marketingActivity: '',
			nurtureDripCampaign: '',
			overallMessageContentCTA: '',
			primaryThemeOrMessage: '',
			publication: '',
			quantity: '',
			resourcesNecessaryFromLiferay: '',
			sourceAndSizeOfCallList: '',
			sourceAndSizeOfInviteeList: '',
			specificSites: '',
			targetOfLeads: '',
			targetOfSends: '',
			typeMerchandise: '',
			usingCIABAssets: '',
			venueName: '',
			webinarHostPlatform: '',
			webinarTopic: '',
			weeksAiring: '',
		},
		activityStatus: Status.SUBMITTED,
		budgets: [],
		claimPercent,
		currency,
		endDate: undefined,
		mdfRequestAmount: 0,
		name: '',
		startDate: undefined,
		submitted: false,
		tactic: {},
		totalCostOfExpense: 0,
		typeActivity: {},
	};
}
