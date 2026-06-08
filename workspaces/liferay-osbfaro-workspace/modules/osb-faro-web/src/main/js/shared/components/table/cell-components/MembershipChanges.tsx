/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import getCN from 'classnames';
import React from 'react';
import {formatUTCDate} from '~/shared/util/date';

enum MembershipChange {
	ADDED = 'ADDED',
	REMOVED = 'REMOVED',
	STAYED = 'STAYED',
}

interface IMembershipChanges {
	className?: string;
	data: {
		createDateTime: string;
		type?: MembershipChange;
	};
}

const MEMBERSHIP_CHANGE_MAP: Record<
	MembershipChange,
	{displayType?: 'success' | 'danger'; label: string}
> = {
	ADDED: {
		displayType: 'success',
		label: Liferay.Language.get('added'),
	},
	REMOVED: {
		displayType: 'danger',
		label: Liferay.Language.get('removed'),
	},
	STAYED: {
		label: Liferay.Language.get('stayed'),
	},
};

const MAP_LABEL_TYPE: MembershipChange[] = [
	MembershipChange.ADDED,
	MembershipChange.REMOVED,
];

const MembershipChanges: React.FC<IMembershipChanges> = ({
	className,
	data: {createDateTime, type},
}) => (
	<td className={getCN('name-cell-root', className)}>
		<div>{formatUTCDate(createDateTime)}</div>
		{type && MAP_LABEL_TYPE.includes(type) && (
			<Label displayType={MEMBERSHIP_CHANGE_MAP[type].displayType}>
				{MEMBERSHIP_CHANGE_MAP[type].label}
			</Label>
		)}
	</td>
);

export default MembershipChanges;
