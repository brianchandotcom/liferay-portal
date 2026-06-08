/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Property} from '~/shared/util/records';

import {PropertyTypes} from '../constants';

const createIndividualProperty = ({
	label,
	name,
	type,
}: {
	label: string;
	name: string;
	type: PropertyTypes;
}): Property =>
	new Property({
		entityName: Liferay.Language.get('individual'),
		label,
		name,
		propertyKey: 'individual',
		type,
	});

const INDIVIDUAL_PROPERTIES = [
	{
		label: Liferay.Language.get('site-membership'),
		name: 'groupIds',
		type: PropertyTypes.SelectText,
	},
	{
		label: Liferay.Language.get('role'),
		name: 'roleIds',
		type: PropertyTypes.SelectText,
	},
	{
		label: Liferay.Language.get('team'),
		name: 'teamIds',
		type: PropertyTypes.SelectText,
	},
	{
		label: Liferay.Language.get('user-group'),
		name: 'userGroupIds',
		type: PropertyTypes.SelectText,
	},
	{
		label: Liferay.Language.get('dxp-user'),
		name: 'userId',
		type: PropertyTypes.SelectText,
	},
].map(createIndividualProperty);

export default INDIVIDUAL_PROPERTIES;
