import Alert, {AlertTypes} from 'shared/components/Alert';
import React from 'react';
import {applyTimeZone} from 'shared/util/date';
import {sub} from 'shared/util/lang';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useTimeZone} from 'shared/hooks/useTimeZone';

const TIME_ZONE_COUNTRY_REGEX = /\([^)]+.*/;

interface ITimeZoneAlertProps extends React.HTMLAttributes<HTMLElement> {
	modifiedTime: number;
	onClose: () => void;
	stripe: boolean;
}

const TimeZoneAlert: React.FC<ITimeZoneAlertProps> = ({
	modifiedTime,
	onClose,
	stripe
}) => {
	const {displayTimeZone, timeZoneId} = useTimeZone();
	const currentUser = useCurrentUser();

	return (
		<Alert
			iconSymbol='exclamation-full'
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
					).fromNow()
				]
			)}
		</Alert>
	);
};

export default TimeZoneAlert;
