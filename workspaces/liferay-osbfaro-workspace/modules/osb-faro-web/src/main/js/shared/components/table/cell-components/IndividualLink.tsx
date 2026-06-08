/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get} from 'lodash';
import React from 'react';
import {Routes, toRoute} from '~/shared/util/router';
import {isBlank} from '~/shared/util/util';

import NameCell from './Name';

interface IIndividualLinksProps {
	channelId?: string;
	className?: string;
	data: {
		emailAddress?: string;
		id: string;
		individualDeleted: boolean;
		individualEmail: string;
		individualId: string;
		individualName: string;
		name: string;
		ownerId?: string;
	};
	disabled?: boolean;
	groupId: string;
}

const IndividualLinkCell: React.FC<IIndividualLinksProps> = ({
	channelId,
	className,
	data,
	disabled,
	groupId,
}) => {
	const id = data.individualId || data.ownerId || data.id;

	const name = data.name || data.individualName || '-';

	const email =
		get(data, ['properties', 'email']) ||
		data.individualEmail ||
		data.emailAddress;

	const anonymous = isBlank(email);

	return (
		<NameCell
			className={className}
			data={{...data, id, name}}
			disabled={data.individualDeleted || anonymous || disabled}
			routeFn={({data: {id}}: {data: {id: string}}) =>
				toRoute(Routes.CONTACTS_INDIVIDUAL, {
					channelId,
					groupId,
					id,
				})
			}
		/>
	);
};

export default IndividualLinkCell;
