/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TacticKeys} from '../../../../../../../../../common/enums/mdfRequestTactics';
import MDFRequestActivity from '../../../../../../../../../common/interfaces/mdfRequestActivity';

export default function getMiscellaneousMarketing(
	mdfRequestActivity: MDFRequestActivity
) {
	const miscellaneousMarketingFields = [
		{
			title: 'Marketing activity',
			value: mdfRequestActivity.activityDescription?.marketingActivity,
		},
	];
	if (mdfRequestActivity.tactic.key === TacticKeys.BROADCAST_ADVERTISING) {
		miscellaneousMarketingFields.push(
			{
				title: 'Broadcast channel',
				value: mdfRequestActivity.activityDescription?.broadcastChannel,
			},
			{
				title: 'CTA',
				value: mdfRequestActivity.activityDescription?.cta,
			},
			{
				title: '# of weeks/airing',
				value: mdfRequestActivity.activityDescription?.weeksAiring,
			},
			{
				title: 'Guaranteed Impressions',
				value:
					mdfRequestActivity.activityDescription
						?.guaranteedImpressions,
			}
		);
	}
	else if (
		mdfRequestActivity.tactic.key ===
		TacticKeys.CAMPAIGN_WITH_INDUSTRY_PUBLICATION
	) {
		miscellaneousMarketingFields.push(
			{
				title: 'Publication',
				value: mdfRequestActivity.activityDescription?.publication,
			},
			{
				title: 'CTA',
				value: mdfRequestActivity.activityDescription?.cta,
			},
			{
				title: 'Expected Impressions',
				value:
					mdfRequestActivity.activityDescription?.expectedImpressions,
			}
		);
	}
	else if (
		mdfRequestActivity.tactic.key === TacticKeys.CO_BRANDED_MERCHANDISE
	) {
		miscellaneousMarketingFields.push(
			{
				title: 'What type of merchandise?',
				value: mdfRequestActivity.activityDescription?.typeMerchandise,
			},
			{
				title: 'Quantity',
				value: mdfRequestActivity.activityDescription?.quantity,
			}
		);
	}
	else if (mdfRequestActivity.tactic.key === TacticKeys.DIRECT_MAIL) {
		miscellaneousMarketingFields.push(
			{
				title: 'Target # of sends',
				value: mdfRequestActivity.activityDescription?.targetOfSends,
			},
			{
				title: 'CTA',
				value: mdfRequestActivity.activityDescription?.cta,
			}
		);
	}
	else if (
		mdfRequestActivity.tactic.key ===
		TacticKeys.OUTBOUND_TELEMARKETING_SALES
	) {
		miscellaneousMarketingFields.push(
			{
				title: 'Audience Target',
				value: mdfRequestActivity.activityDescription?.audienceTarget,
			},
			{
				title: 'Source and Size of call List',
				value:
					mdfRequestActivity.activityDescription
						?.sourceAndSizeOfCallList,
			},
			{
				title: 'Resources necessary from Liferay',
				value:
					mdfRequestActivity.activityDescription
						?.resourcesNecessaryFromLiferay,
			}
		);
	}
	else if (mdfRequestActivity.tactic.key === TacticKeys.PRINT_ADVERTISING) {
		miscellaneousMarketingFields.push(
			{
				title: 'Publication',
				value: mdfRequestActivity.activityDescription?.publication,
			},
			{
				title: 'CTA',
				value: mdfRequestActivity.activityDescription?.cta,
			},
			{
				title: 'Expected Impressions',
				value:
					mdfRequestActivity.activityDescription?.expectedImpressions,
			}
		);
	}

	return miscellaneousMarketingFields;
}
