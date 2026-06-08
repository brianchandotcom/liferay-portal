/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Attributes, Breakdowns, Filters} from '~/event-analysis/utils/types';
import DndProvider from '~/shared/components/DndProvider';

import {
	AddBreakdown,
	AddBreakdownParams,
	DeleteBreakdown,
	EditBreakdown,
	MoveBreakdown,
	withAttributesConsumer,
} from '../context/attributes';
import AttributeBreakdownChip from './AttributeBreakdownChip';
import AttributeBreakdownDropdown from './attribute-breakdown-dropdown';

const MAX_ATTRIBUTES = 5;

interface IAttributeBreakdownSectionProps {
	addBreakdown: AddBreakdown;
	attributes: Attributes;
	breakdownOrder: string[];
	breakdowns: Breakdowns;
	deleteBreakdown: DeleteBreakdown;
	editBreakdown: EditBreakdown;
	eventId: string;
	filters: Filters;
	moveBreakdown: MoveBreakdown;
}

export const AttributeBreakdownSection = function AttributeBreakdownSection({
	addBreakdown,
	attributes,
	breakdownOrder,
	breakdowns,
	deleteBreakdown,
	editBreakdown,
	eventId,
	moveBreakdown,
}: IAttributeBreakdownSectionProps) {
	const disabledIds = breakdownOrder.map(
		(breakdownId) => breakdowns[breakdownId].attributeId
	);

	const uneditableIds = Object.keys(attributes);

	const onAttributeSelect: AddBreakdown | EditBreakdown = (
		params: AddBreakdownParams
	) => {
		addBreakdown(params);
	};

	return (
		<div className="align-items-center attribute-breakdown-section-root d-flex">
			<div className="section-header">
				{Liferay.Language.get('breakdown')}
			</div>

			{!!eventId && (
				<div className="align-items-center attribute-container d-flex justify-content-between">
					<DndProvider backend={HTML5Backend}>
						<div className="align-items-center attribute-list d-flex">
							{breakdownOrder.map((id, i) => (
								<AttributeBreakdownChip
									attribute={
										attributes[breakdowns[id].attributeId]
									}
									breakdown={breakdowns[id]}
									disabledIds={disabledIds}
									eventId={eventId}
									index={i}
									key={id}
									onCloseClick={deleteBreakdown}
									onEditSubmit={editBreakdown}
									onMove={moveBreakdown}
									uneditableIds={uneditableIds}
								/>
							))}
						</div>
					</DndProvider>

					{breakdownOrder.length < MAX_ATTRIBUTES && (
						<AttributeBreakdownDropdown
							alignmentPosition={Align.LeftTop}
							disabledIds={disabledIds}
							eventId={eventId}
							onAttributeSelect={onAttributeSelect}
							trigger={
								<ClayButton
									aria-label={Liferay.Language.get('add')}
									borderless
									className="add-attribute button-root"
									displayType="secondary"
									size="sm"
								>
									<ClayIcon
										className="icon-root"
										symbol="plus"
									/>
								</ClayButton>
							}
							uneditableIds={uneditableIds}
						/>
					)}
				</div>
			)}
		</div>
	);
};

export default withAttributesConsumer(AttributeBreakdownSection);
