/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {NAME, createOrderIOMap} from '~/shared/util/pagination';
import {organizationsListColumns} from '~/shared/util/table-columns';

import {EntityType} from '../context/referencedObjects';
import {
	getMapResultToProps,
	mapPropsToOptions,
} from '../mappers/dxp-entity-bag-mapper';
import OrganizationsQuery from '../queries/OrganizationsQuery';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import CustomSelectEntityInput from './components/CustomSelectEntityInput';

interface IOrganizationSelectProps extends ISegmentEditorCustomInputBase {
	touched: boolean;
	valid: boolean;
}

const OrganizationSelectInput: React.FC<IOrganizationSelectProps> = ({
	property,
	valid,
	...otherProps
}) => (
	<CustomSelectEntityInput
		className="organization-select-input-root"
		columns={organizationsListColumns}
		entityLabel={Liferay.Language.get('organizations')}
		entityType={EntityType.Organizations}
		graphqlProps={{
			graphqlQuery: OrganizationsQuery,
			mapPropsToOptions,
			mapResultToProps: getMapResultToProps('organizations'),
		}}
		initialOrderIOMap={createOrderIOMap(NAME)}
		orderByOptions={[
			{
				label: Liferay.Language.get('name'),
				value: NAME,
			},
		]}
		property={property}
		valid={valid}
		{...otherProps}
	/>
);

export default OrganizationSelectInput;
