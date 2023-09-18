/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {string} from 'yup';

import {TacticKeys} from '../../../../../../common/enums/mdfRequestTactics';

const getEventFieldsValidation = (tactic: TacticKeys) => {
	const basicEventFields = {
		activityPromotion: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		description: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		liferayParticipationRequirements: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		sourceAndSizeOfInviteeList: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
	};

	let targetFields = {};

	if (tactic === TacticKeys.WEBINAR) {
		targetFields = {
			...basicEventFields,
			liferayBranding: string()
				.trim()
				.max(255, 'You have exceeded the character limit'),
			webinarHostPlatform: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
			webinarTopic: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};
	}
	else {
		targetFields = {
			...basicEventFields,
			liferayBranding: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
			location: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
			venueName: string()
				.trim()
				.max(255, 'You have exceeded the character limit')
				.required('Required'),
		};
	}

	return targetFields;
};

export default getEventFieldsValidation;
