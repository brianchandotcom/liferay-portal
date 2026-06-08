/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Alert, {AlertTypes} from '~/shared/components/Alert';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {useTimeZone} from '~/shared/hooks/useTimeZone';
import {applyTimeZone} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';

const TIME_ZONE_COUNTRY_REGEX = /\([^)]+.*/;

interface ITimeZoneAlertProps extends React.HTMLAttributes<HTMLElement> {
	modifiedTime: number;
	onClose: () => void;
	stripe: boolean;
}

const TimeZoneAlert: React.FC<ITimeZoneAlertProps> = ({
	modifiedTime,
	onClose,
	stripe,
}) => {
	const {displayTimeZone, timeZoneId} = useTimeZone();
	const currentUser = useCurrentUser();

	return (
		<Alert
			iconSymbol="exclamation-full"
			onClose={onClose}
			stripe={stripe}
			title={Liferay.Language.get('info')}
			type={AlertTypes.Info}
		>
			{sub(
				Liferay.Language.get(
					'workspace-timezone-has-changed-to-x-as-of-x.-please-allow-1-2-days-for-the-data-to-adjust-to-this-new-setting.'
				),
				[
					displayTimeZone.replace(TIME_ZONE_COUNTRY_REGEX, ''),
					applyTimeZone(
						modifiedTime,
						timeZoneId,
						currentUser.languageId
					).fromNow(),
				]
			)}
		</Alert>
	);
};

export default TimeZoneAlert;
