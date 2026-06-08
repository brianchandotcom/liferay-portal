/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map, OrderedMap} from 'immutable';
import React, {useEffect, useState} from 'react';
import {Attribute} from '~/event-analysis/utils/types';
import Card from '~/shared/components/Card';
import CodeSnippet from '~/shared/components/CodeSnippet';
import Label from '~/shared/components/form/Label';
import Table, {Column} from '~/shared/components/table';
import {attributeListColumns} from '~/shared/util/table-columns';

interface IEventDetailsCardProps {
	eventAttributes: Attribute[];
	eventName: string;
	groupId: string;
}

const EventDetailsCard: React.FC<IEventDetailsCardProps> = ({
	eventAttributes,
	eventName,
	groupId,
}) => {
	const [codeLines, setCodeLines] = useState([
		`Analytics.track('${eventName}', {`,
		'});',
	]);

	const [selectedAttributes, setSelectedAttributes] = useState(
		OrderedMap<string, Map<string, any>>(
			eventAttributes.map(
				(attribute) =>
					[attribute.id, Map(attribute)] as [
						string,
						Map<string, string>,
					]
			)
		)
	);

	useEffect(() => {
		const attributesRepresentations: string[] = [];

		selectedAttributes.forEach((attribute) => {
			const name = attribute?.get('name');
			const sampleValue = attribute?.get('sampleValue');

			attributesRepresentations.push(`'${name}': '${sampleValue}',`);
		});

		setCodeLines([
			codeLines[0],
			...attributesRepresentations,
			codeLines[codeLines.length - 1],
		]);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedAttributes]);

	const addSelectedAttribute = (attribute: Attribute): void =>
		setSelectedAttributes(
			selectedAttributes.set(attribute.id, Map(attribute))
		);

	const removeSelectedAttribute = (key: string): void => {
		setSelectedAttributes((previous) => previous.remove(key));
	};

	return (
		<Card key="cardContainer">
			<Card.Header>
				<Card.Title>
					{Liferay.Language.get('send-this-event')}
				</Card.Title>
			</Card.Header>

			<Card.Body>
				<span className="mb-4 mt-2 w-50">
					{Liferay.Language.get(
						'use-this-script-to-start-sending-events-to-analytics-cloud.-you-can-customize-which-attributes-to-send-with-a-specific-event.-selecting-the-attributes-below-will-generate-a-new-sample-script'
					)}
				</span>

				<Label>{Liferay.Language.get('sample-javascript-colon')}</Label>

				<CodeSnippet codeLines={codeLines} />
			</Card.Body>

			<Table
				className="mb-0"
				columns={
					[
						attributeListColumns.getName({
							channelId: 'channelId',
							groupId,
						}),
						attributeListColumns.displayName,
						attributeListColumns.description,
						attributeListColumns.sampleValue,
						attributeListColumns.dataType,
					].map((column) => ({
						...column,
						sortable: false,
					})) as Column[]
				}
				items={eventAttributes}
				onSelectItemsChange={(selectedAttribute) =>
					selectedAttributes.has(selectedAttribute.id)
						? removeSelectedAttribute(selectedAttribute.id)
						: addSelectedAttribute(selectedAttribute as Attribute)
				}
				rowIdentifier="id"
				selectedItemsIOMap={selectedAttributes}
				showCheckbox
			/>
		</Card>
	);
};

export default EventDetailsCard;
