/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivity from '../../../../../../../../../common/interfaces/mdfRequestActivity';
import getBooleanValue from '../../../../../../../../../common/utils/getBooleanValue';

export default function getContentMarketFields(
	mdfRequestActivity: MDFRequestActivity
) {
	return [
		{
			title: 'Will this content be gated and have a landing page?',
			value: getBooleanValue(
				mdfRequestActivity.activityDescription
					?.gatedLandingPage as string
			),
		},
		{
			title: 'Primary theme or message of your content',
			value:
				mdfRequestActivity.activityDescription?.primaryThemeOrMessage,
		},

		{
			title: 'Goal of Content',
			value: mdfRequestActivity.activityDescription?.goalOfContent,
		},
		{
			title:
				'Are you hiring an outside writer or agency to prepare the content?',
			value: getBooleanValue(
				mdfRequestActivity.activityDescription
					?.hiringOutsideWriterOrAgency as string
			),
		},
	];
}
