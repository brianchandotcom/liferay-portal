/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {List} from 'immutable';
import {Property} from '~/shared/util/records';

import {PropertyTypes} from '../constants';

const createOrganizationProperty = ({
	label,
	name,
	type,
}: {
	label: string;
	name: string;
	type: PropertyTypes;
}): Property =>
	new Property({
		entityName: Liferay.Language.get('organization'),
		label,
		name,
		propertyKey: 'organization',
		type,
	});

const ORGANIZATION_PROPERTIES = List(
	[
		{
			label: Liferay.Language.get('date-modified'),
			name: 'modifiedDate',
			type: PropertyTypes.OrganizationDate,
		},
		{
			label: Liferay.Language.get('name'),
			name: 'name',
			type: PropertyTypes.OrganizationText,
		},
		{
			label: Liferay.Language.get('hierarchy-path'),
			name: 'hierarchyPath',
			type: PropertyTypes.OrganizationText,
		},
		{
			label: Liferay.Language.get('organization'),
			name: 'id',
			type: PropertyTypes.OrganizationSelectText,
		},
		{
			label: Liferay.Language.get('parent-organization'),
			name: 'parentId',
			type: PropertyTypes.OrganizationSelectText,
		},
		{
			label: Liferay.Language.get('type'),
			name: 'type',
			type: PropertyTypes.OrganizationText,
		},
	].map(createOrganizationProperty)
);

export default ORGANIZATION_PROPERTIES;
