/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import {PaginationBar} from '@clayui/pagination-bar';
import {List} from 'immutable';
import React, {useContext, useEffect, useMemo, useState} from 'react';
import Loading from '~/shared/components/Loading';
import {SegmentTypes} from '~/shared/util/constants';
import {Property, PropertyGroup, PropertySubgroup} from '~/shared/util/records';

import {ReferencedObjectsContext} from '../context/referencedObjects';
import {extractRemoteCriterionEntries} from '../criterion-types/extract';
import {getRemoteCriterionTypeByPropertyKey} from '../criterion-types/registry';
import {translateQueryToCriteria} from '../utils/odata';
import CriteriaSidebarCollapse from './CriteriaSidebarCollapse';
import CriteriaSidebarSearchBar from './CriteriaSidebarSearchBar';

const REMOTE_PAGE_SIZE = 12;

const PROPERTY_KEY_TO_GROUP: Record<string, string> = {
	account: 'attributes',
	individual: 'attributes',
	interest: 'page-topics',
	organization: 'attributes',
	session: 'attributes',
	tag: 'asset-categorization',
	vocabulary: 'asset-categorization',
	web: 'behavioral',
};

const GROUP_ORDER = [
	'behavioral',
	'attributes',
	'asset-categorization',
	'page-topics',
];

const GROUP_LABELS: Record<string, string> = {
	'asset-categorization': Liferay.Language.get('asset-categorization'),
	'attributes': Liferay.Language.get('attributes'),
	'behavioral': Liferay.Language.get('behavioral'),
	'page-topics': Liferay.Language.get('page-topics'),
};

interface IPickerGroup {
	items: Array<{label: string; value: string}>;
	label: string;
}

interface ICriteriaSidebarProps {
	channelId: string;
	criteriaString?: string;
	groupId: string;
	propertyGroupsIList: List<PropertyGroup>;
	type: string;
}

export default function CriteriaSidebar({
	channelId,
	criteriaString,
	groupId,
	propertyGroupsIList,
	type,
}: ICriteriaSidebarProps) {
	const [searchValue, setSearchValue] = useState('');
	const [selectedPropertyKey, setSelectedPropertyKey] = useState<
		string | null
	>(() => propertyGroupsIList.first()?.propertyKey ?? null);

	const [remoteQuery, setRemoteQuery] = useState<{
		keywords: string;
		page: number;
	}>({keywords: '', page: 1});
	const [remoteItems, setRemoteItems] = useState<List<Property>>(List());
	const [remoteLoading, setRemoteLoading] = useState(false);

	const [remoteTotalCount, setRemoteTotalCount] = useState(0);

	const {addProperty} = useContext(ReferencedObjectsContext);

	const selectedRemoteCriterionType =
		getRemoteCriterionTypeByPropertyKey(selectedPropertyKey);
	const isRemoteSection = !!selectedRemoteCriterionType;
	const remoteKeywords = isRemoteSection ? searchValue : '';

	useEffect(() => {
		if (type !== SegmentTypes.Batch || !criteriaString || !addProperty) {
			return;
		}

		extractRemoteCriterionEntries(
			translateQueryToCriteria(criteriaString)
		).forEach(({criterionType, id, name}) => {
			addProperty(criterionType.createProperty({id, name}));
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		setRemoteQuery((q) =>
			q.keywords === remoteKeywords
				? q
				: {keywords: remoteKeywords, page: 1}
		);
	}, [remoteKeywords]);

	useEffect(() => {
		setRemoteItems(List());
		setRemoteTotalCount(0);
		setRemoteQuery((q) => (q.page === 1 ? q : {...q, page: 1}));
	}, [selectedPropertyKey]);

	useEffect(() => {
		if (type !== SegmentTypes.Batch || !selectedRemoteCriterionType) {
			return;
		}

		setRemoteLoading(true);

		selectedRemoteCriterionType
			.api({
				channelId,
				groupId,
				keywords: remoteQuery.keywords,
				page: remoteQuery.page,
				pageSize: REMOTE_PAGE_SIZE,
			})
			.then((result) => {
				const properties: List<Property> = List(
					(result.items ?? []).map(
						selectedRemoteCriterionType.createProperty
					)
				);

				setRemoteItems(properties);
				setRemoteTotalCount(result.totalCount ?? 0);

				if (addProperty) {
					properties.forEach(
						(property) => property && addProperty(property)
					);
				}
			})
			.finally(() => setRemoteLoading(false));

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [channelId, groupId, type, selectedRemoteCriterionType, remoteQuery]);

	const effectivePropertyGroupsIList = useMemo(
		() =>
			propertyGroupsIList
				.map((group) => {
					if (
						!group ||
						!getRemoteCriterionTypeByPropertyKey(group.propertyKey)
					) {
						return group as PropertyGroup;
					}

					return group.set(
						'propertySubgroups',
						List([new PropertySubgroup({properties: remoteItems})])
					) as PropertyGroup;
				})
				.toList(),
		[propertyGroupsIList, remoteItems]
	);

	const groupedBySection = propertyGroupsIList
		.toArray()
		.reduce<Record<string, PropertyGroup[]>>((acc, pg) => {
			const groupKey =
				PROPERTY_KEY_TO_GROUP[pg.propertyKey] ?? 'attributes';

			if (!acc[groupKey]) {
				acc[groupKey] = [];
			}

			acc[groupKey].push(pg);

			return acc;
		}, {});

	const pickerItems: IPickerGroup[] = GROUP_ORDER.filter(
		(groupKey) => groupedBySection[groupKey]?.length > 0
	).map((groupKey) => ({
		items: groupedBySection[groupKey].map(({label, propertyKey}) => ({
			label,
			value: propertyKey,
		})),
		label: GROUP_LABELS[groupKey] ?? groupKey,
	}));

	return (
		<div className="criteria-sidebar-root">
			<div className="sidebar-title">
				{Liferay.Language.get('segment-criteria')}
			</div>

			{type !== SegmentTypes.RealTime && (
				<div className="sidebar-header">
					<Picker
						items={pickerItems}
						onSelectionChange={(key) => {
							setSelectedPropertyKey(key as string);
						}}
						selectedKey={selectedPropertyKey ?? undefined}
					>
						{(group: IPickerGroup) => (
							<ClayDropDown.Group
								header={group.label}
								items={group.items}
							>
								{(item: {label: string; value: string}) => (
									<Option key={item.value}>
										{item.label}
									</Option>
								)}
							</ClayDropDown.Group>
						)}
					</Picker>
				</div>
			)}

			<div className="sidebar-search">
				<CriteriaSidebarSearchBar
					onChange={setSearchValue}
					searchValue={searchValue}
				/>
			</div>

			<div className="sidebar-collapse">
				{isRemoteSection && remoteLoading ? (
					<Loading overlay />
				) : (
					<CriteriaSidebarCollapse
						propertyGroupsIList={effectivePropertyGroupsIList}
						propertyKey={selectedPropertyKey ?? ''}
						searchValue={isRemoteSection ? '' : searchValue}
					/>
				)}
			</div>

			{isRemoteSection && remoteTotalCount > 0 && (
				<PaginationBar className="justify-content-center sidebar-pagination">
					<ClayPaginationWithBasicItems
						active={remoteQuery.page}
						onActiveChange={(page) =>
							setRemoteQuery((q) => ({...q, page}))
						}
						totalPages={Math.ceil(
							remoteTotalCount / REMOTE_PAGE_SIZE
						)}
					/>
				</PaginationBar>
			)}
		</div>
	);
}
