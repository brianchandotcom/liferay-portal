/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useParams} from 'react-router-dom';
import BasePage from '~/settings/components/base-page/BasePage';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {useTimeZone} from '~/shared/hooks/useTimeZone';
import {getDataPrivacy} from '~/shared/util/breadcrumbs';

import RequestList from '../hocs/RequestList';

export const RequestLog = function RequestLog() {
	const {groupId = ''} = useParams<{groupId: string}>();
	const currentUser = useCurrentUser();
	const {timeZoneId} = useTimeZone();

	return (
		<BasePage
			breadcrumbItems={[
				getDataPrivacy({groupId}),
				{
					active: true,
					label: Liferay.Language.get('request-log'),
				},
			]}
			className="request-log-page-root"
			documentTitle={Liferay.Language.get('request-log')}
		>
			<RequestList currentUser={currentUser} timeZoneId={timeZoneId} />
		</BasePage>
	);
};

export default RequestLog;
