/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import {ProfileTypes} from '~/segment/segment-editor/dynamic/utils/constants';

interface IMembershipChanges {
	className?: string;
	data: {
		profileType: ProfileTypes;
	};
}

const PROFILE_TYPE_LABEL_MAP: Record<ProfileTypes, string> = {
	ANONYMOUS: Liferay.Language.get('anonymous'),
	KNOWN: Liferay.Language.get('known'),
};

const ProfileType: React.FC<IMembershipChanges> = ({
	className,
	data: {profileType},
}) => (
	<td className={getCN('text-capitalize', className)}>
		{PROFILE_TYPE_LABEL_MAP[profileType]}
	</td>
);

export default ProfileType;
