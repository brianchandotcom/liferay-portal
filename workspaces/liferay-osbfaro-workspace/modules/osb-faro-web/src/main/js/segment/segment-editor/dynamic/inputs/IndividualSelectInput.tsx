/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderedMap} from 'immutable';
import React from 'react';
import {IPagination} from '~/shared/types';
import {NAME, createOrderIOMap} from '~/shared/util/pagination';

import {EntityType} from '../context/referencedObjects';
import {
	getMapResultToProps,
	mapPropsToOptions,
} from '../mappers/dxp-entity-bag-mapper';
import getDXPEntitiesQuery from '../queries/DXPEntitiesQuery';
import DXPUsersQuery from '../queries/DXPUsersQuery';
import {ISegmentEditorInputBase} from '../utils/types';
import SelectEntityInput from './components/SelectEntityInput';

const QUERY_MAP = {
	userId: DXPUsersQuery,
};

export const ENTITY_MAP = {
	groupIds: EntityType.Groups,
	roleIds: EntityType.Roles,
	teamIds: EntityType.Teams,
	userGroupIds: EntityType.UserGroups,
	userId: EntityType.Users,
};

const LABEL_MAP = {
	groupIds: Liferay.Language.get('site-memberships'),
	roleIds: Liferay.Language.get('roles'),
	teamIds: Liferay.Language.get('teams'),
	userGroupIds: Liferay.Language.get('user-groups'),
};

const nameCol = {
	accessor: 'name',
	className: 'table-cell-expand',
	label: Liferay.Language.get('name'),
	title: true,
};

const PROPERTY_COLUMNS_MAP = {
	userId: [
		nameCol,
		{
			accessor: 'screenName',
			className: 'table-cell-expand',
			label: Liferay.Language.get('screen-name'),
		},
	],
};

interface IIndividualSelectProps extends ISegmentEditorInputBase, IPagination {
	channelId: string;
	groupId: string;
	touched: boolean;
	valid: boolean;
	value: string;
}

const IndividualSelectInput: React.FC<IIndividualSelectProps> = ({
	channelId,
	onChange,
	property,
	valid,
	value,
	...otherProps
}) => {
	const propertyName = property.name as keyof typeof ENTITY_MAP;
	const entityType: EntityType = ENTITY_MAP[propertyName];

	const graphqlEntityType =
		entityType === EntityType.UserGroups
			? 'userGroups'
			: (entityType as string);

	const handleItemsChange = (items: OrderedMap<string, any>) => {
		const entity = items.first();

		if (items.size === 1) {
			onChange({
				valid: true,
				value: entity.id,
			});
		}
		else {
			onChange(
				items
					.valueSeq()
					.toArray()
					.map(({id}) => ({
						valid: true,
						value: id,
					}))
			);
		}
	};

	return (
		<SelectEntityInput
			channelId={channelId}
			className="individual-select-input-root"
			columns={
				PROPERTY_COLUMNS_MAP[
					propertyName as keyof typeof PROPERTY_COLUMNS_MAP
				] || [nameCol]
			}
			entityLabel={LABEL_MAP[propertyName as keyof typeof LABEL_MAP]}
			entityType={entityType}
			graphqlProps={{
				graphqlQuery:
					QUERY_MAP[propertyName as keyof typeof QUERY_MAP] ||
					getDXPEntitiesQuery(graphqlEntityType),
				mapPropsToOptions,
				mapResultToProps: getMapResultToProps(graphqlEntityType),
			}}
			initialOrderIOMap={createOrderIOMap(NAME)}
			onItemsChange={handleItemsChange}
			onValidChange={onChange}
			orderByOptions={[
				{
					label: Liferay.Language.get('name'),
					value: NAME,
				},
			]}
			property={property}
			valid={valid}
			value={value}
			{...otherProps}
		/>
	);
};

export default IndividualSelectInput;
