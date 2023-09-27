/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TacticKeys} from '../../../../../../../../../common/enums/mdfRequestTactics';
import MDFRequestActivity from '../../../../../../../../../common/interfaces/mdfRequestActivity';

export default function getEventFields(mdfRequestActivity: MDFRequestActivity) {
	const eventFields = [
		{
			title: 'Activity Description',
			value: mdfRequestActivity.activityDescription?.description,
		},
	];

	if (mdfRequestActivity.tactic.key === TacticKeys.WEBINAR) {
		eventFields.push(
			{
				title: 'Webinar Topic',
				value: mdfRequestActivity.activityDescription?.webinarTopic,
			},
			{
				title: 'Webinar Host/Platform',
				value:
					mdfRequestActivity.activityDescription?.webinarHostPlatform,
			}
		);
	}
	else {
		eventFields.push(
			{
				title: 'Location',
				value: mdfRequestActivity.activityDescription?.location,
			},
			{
				title: 'Venue Name',
				value: mdfRequestActivity.activityDescription?.venueName,
			}
		);
	}

	eventFields.push(
		{
			title: 'Liferay Branding',
			value: mdfRequestActivity.activityDescription?.liferayBranding,
		},
		{
			title: 'Liferay Participation / Requirements',
			value:
				mdfRequestActivity.activityDescription
					?.liferayParticipationRequirements,
		},
		{
			title: 'Source and Size of Invitee List',
			value:
				mdfRequestActivity.activityDescription
					?.sourceAndSizeOfInviteeList,
		},
		{
			title: 'Activity Promotion',
			value: mdfRequestActivity.activityDescription?.activityPromotion,
		}
	);

	return eventFields;
}
