/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {List} from 'immutable';
import React from 'react';
import {compose} from 'redux';
import EventDefinitionsQuery from '~/event-analysis/queries/EventDefinitionsQuery';
import {EventTypes} from '~/event-analysis/utils/types';
import * as API from '~/shared/api';
import client from '~/shared/apollo/client';
import {withRequest} from '~/shared/hoc';
import {
	FieldContexts,
	FieldOwnerTypes,
	OrderByDirections,
	SegmentTypes,
} from '~/shared/util/constants';
import {NAME} from '~/shared/util/pagination';
import {PropertyGroup, PropertySubgroup} from '~/shared/util/records';

import {
	INDIVIDUAL_PROPERTIES,
	ORGANIZATION_PROPERTIES,
	SESSION_PROPERTIES,
	WEB_BEHAVIORS,
} from '../utils/properties';
import {
	convertEventToProperty,
	convertFieldMappingToAccountProperty,
	convertFieldMappingToIndividualProperty,
	convertFieldMappingToOrganizationProperty,
	createInterestProperty,
} from '../utils/utils';

const MAX_DELTA = 500;

const fetchPropertyGroups = ({
	channelId,
	groupId,
	type,
}: {
	channelId: string;
	groupId: string;
	type?: string;
}): Promise<any> =>
	Promise.all([
		API.fieldMappings.search({
			context: FieldContexts.Demographics,
			delta: MAX_DELTA,
			groupId,
			ownerType: FieldOwnerTypes.Individual,
		}),
		API.fieldMappings.search({
			context: FieldContexts.Custom,
			delta: MAX_DELTA,
			groupId,
			ownerType: FieldOwnerTypes.Individual,
		}),
		API.fieldMappings.search({
			channelId,
			context: FieldContexts.Account,
			delta: MAX_DELTA,
			groupId,
			ownerType: FieldOwnerTypes.Account,
		}),
		Promise.resolve(ORGANIZATION_PROPERTIES),
		API.fieldMappings.search({
			context: FieldContexts.Custom,
			delta: MAX_DELTA,
			groupId,
			ownerType: FieldOwnerTypes.Organization,
		}),
		client.query({
			fetchPolicy: 'network-only',
			query: EventDefinitionsQuery,
			variables: {
				eventType: EventTypes.Custom,
				hidden: false,
				page: 0,
				size: MAX_DELTA,
				sort: {
					column: NAME,
					type: OrderByDirections.Ascending,
				},
			},
		}),
		Promise.resolve(WEB_BEHAVIORS),
		type === SegmentTypes.Batch
			? API.interests.searchKeywords({
					channelId,
					delta: MAX_DELTA,
					groupId,
				})
			: Promise.resolve({items: []}),
		Promise.resolve(SESSION_PROPERTIES),
		Promise.resolve({items: [], totalCount: 0}),
	]);

const mapResultToProps = (
	[
		individualDemographicsMappings,
		individualCustomMappings,
		accountMappings,
		organizationProperties,
		organizationCustomMappings,
		eventProperties,
		webBehaviors,
		interestKeywords,
		sessionProperties,
	]: any[],
	{type}: {type: SegmentTypes}
) => {
	const individualDemographicProperties =
		individualDemographicsMappings.items.map(
			convertFieldMappingToIndividualProperty
		);

	let individualSubgroupsIList = List([
		new PropertySubgroup({
			properties: List(
				individualDemographicProperties.concat(INDIVIDUAL_PROPERTIES)
			),
		}),
	]);

	individualSubgroupsIList = individualSubgroupsIList.push(
		new PropertySubgroup({
			label: Liferay.Language.get('dxp-custom-fields'),
			properties: List(
				individualCustomMappings.items.map(
					convertFieldMappingToIndividualProperty
				)
			),
		})
	);

	const propertyGroupsIList = List(
		[
			new PropertyGroup({
				label: Liferay.Language.get('events'),
				propertyKey: 'web',
				propertySubgroups: List(
					[
						new PropertySubgroup({
							label: Liferay.Language.get('default-events'),

							properties: webBehaviors,
						}),

						new PropertySubgroup({
							label: Liferay.Language.get('custom-events'),
							properties: List(
								eventProperties?.data?.eventDefinitions?.eventDefinitions?.map(
									convertEventToProperty
								)
							),
						}),
					].filter(Boolean)
				),
			}),
			type === SegmentTypes.Batch &&
				new PropertyGroup({
					label: Liferay.Language.get('individual'),
					propertyKey: FieldOwnerTypes.Individual,
					propertySubgroups: individualSubgroupsIList,
				}),
			type === SegmentTypes.Batch &&
				new PropertyGroup({
					label: Liferay.Language.get('account'),
					propertyKey: FieldOwnerTypes.Account,
					propertySubgroups: List([
						new PropertySubgroup({
							properties: List(
								accountMappings.items.map(
									convertFieldMappingToAccountProperty
								)
							),
						}),
					]),
				}),
			type === SegmentTypes.Batch &&
				new PropertyGroup({
					label: Liferay.Language.get('interests'),
					propertyKey: 'interest',
					propertySubgroups: List([
						new PropertySubgroup({
							properties: List(
								interestKeywords.items.map(
									createInterestProperty
								)
							),
						}),
					]),
				}),
			type === SegmentTypes.Batch &&
				new PropertyGroup({
					label: Liferay.Language.get('session'),
					propertyKey: 'session',
					propertySubgroups: List([
						new PropertySubgroup({
							properties: List(sessionProperties),
						}),
					]),
				}),
			type === SegmentTypes.Batch &&
				new PropertyGroup({
					label: Liferay.Language.get('vocabularies-and-categories'),
					propertyKey: 'vocabulary',
					propertySubgroups: List([
						new PropertySubgroup({properties: List()}),
					]),
				}),
			type === SegmentTypes.Batch &&
				new PropertyGroup({
					label: Liferay.Language.get('tags'),
					propertyKey: 'tag',
					propertySubgroups: List([
						new PropertySubgroup({properties: List()}),
					]),
				}),
		].filter(Boolean) as PropertyGroup[]
	);

	if (type === SegmentTypes.Batch) {
		const organizationPropertyGroup = new PropertyGroup({
			label: Liferay.Language.get('organization'),
			propertyKey: FieldOwnerTypes.Organization,
			propertySubgroups: List([
				new PropertySubgroup({properties: organizationProperties}),
				new PropertySubgroup({
					label: Liferay.Language.get('dxp-custom-fields'),
					properties: List(
						organizationCustomMappings.items.map(
							convertFieldMappingToOrganizationProperty
						)
					),
				}),
			]),
		});

		return {
			propertyGroupsIList: propertyGroupsIList.push(
				organizationPropertyGroup
			),
		};
	}

	return {propertyGroupsIList};
};

export const withPropertyGroups = function withPropertyGroups(
	WrappedComponent: React.ComponentType<any>
) {
	return class extends React.Component<{
		propertyGroupsIList: List<PropertyGroup>;
	}> {
		render() {
			const {propertyGroupsIList, ...otherProps} = this.props;

			return (
				<WrappedComponent
					{...otherProps}
					propertyGroupsIList={propertyGroupsIList}
				/>
			);
		}
	};
};

export default compose(
	withRequest(fetchPropertyGroups, mapResultToProps),
	withPropertyGroups
);
