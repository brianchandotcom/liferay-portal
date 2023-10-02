/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {number, string} from 'yup';

import {TacticKeys} from '../../../../../../common/enums/mdfRequestTactics';

const getDigitalMarketingFieldsValidation = (tactic: TacticKeys) => {
	const basicDigitalMarketingFields = {
		assetsLiferayDescription: string().when('assetsLiferayRequired', {
			is: (assetsLiferayRequired: string) =>
				assetsLiferayRequired === 'true',
			then: (schema) =>
				schema
					.max(255, 'You have exceeded the character limit')
					.trim()
					.required('Required'),
		}),
		assetsLiferayRequired: string().required('Required'),
		howLiferayBrandUsed: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		overallMessageContentCTA: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
	};

	let targetFields = {};

	if (tactic === TacticKeys.EMAIL_CAMPAIGN) {
		targetFields = {
			...basicDigitalMarketingFields,
			landingPageCopy: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),

			manySeries: number()
				.typeError('The input must be a number')
				.when('nurtureDripCampaign', {
					is: (nurtureDripCampaign: string) =>
						nurtureDripCampaign === 'true',
					then: (schema) =>
						schema
							.min(1, 'Required')
							.max(
								9999999,
								'The value cannot be greater than 9,999,999.99'
							)
							.required('Required'),
				}),

			nurtureDripCampaign: string().required('Required'),
			usingCIABAssets: string().required('Required'),
		};
	}
	else {
		targetFields = {
			...basicDigitalMarketingFields,
			ad: string()
				.trim()
				.max(255, 'You have exceeded the character limit'),
			keywordsForPPCCampaigns: string()
				.trim()
				.max(255, 'You have exceeded the character limit'),
			specificSites: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};
	}

	return targetFields;
};

export default getDigitalMarketingFieldsValidation;
