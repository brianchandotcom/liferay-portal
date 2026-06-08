/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BasePage from '~/settings/components/base-page/BasePage';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {useTimeZone} from '~/shared/hooks/useTimeZone';
import {Router} from '~/shared/types';
import {getDataPrivacy} from '~/shared/util/breadcrumbs';

import SuppressedUserList from '../hocs/SuppressedUserList';

export const SuppressedUsers = function SuppressedUsers({
	router,
}: {
	router: Router;
}) {
	const currentUser = useCurrentUser();
	const {timeZoneId} = useTimeZone();

	const groupId = router.params.groupId ?? '';

	return (
		<BasePage
			breadcrumbItems={[
				getDataPrivacy({groupId}),
				{
					active: true,
					label: Liferay.Language.get('suppressed-user-list'),
				},
			]}
			className="suppressed-users-page-root"
			documentTitle={Liferay.Language.get('suppressed-user-list')}
		>
			<SuppressedUserList
				currentUser={currentUser}
				router={router}
				timeZoneId={timeZoneId}
			/>
		</BasePage>
	);
};

export default SuppressedUsers;
