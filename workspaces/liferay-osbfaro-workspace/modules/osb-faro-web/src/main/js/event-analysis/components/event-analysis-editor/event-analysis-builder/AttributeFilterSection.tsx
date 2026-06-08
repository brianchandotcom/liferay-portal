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
	DeleteFilter,
	MoveFilter,
	withAttributesConsumer,
} from '../context/attributes';
import AttributeFilterChip from './AttributeFilterChip';
import AttributeFilterDropdown from './attribute-filter-dropdown';

interface IAttributeFilterSectionProps {
	attributes: Attributes;
	breakdownOrder: string[];
	breakdowns: Breakdowns;
	deleteFilter: DeleteFilter;
	eventId: string;
	filterOrder: string[];
	filters: Filters;
	moveFilter: MoveFilter;
}

export const AttributeFilterSection = function AttributeFilterSection({
	attributes,
	deleteFilter,
	eventId,
	filterOrder,
	filters,
	moveFilter,
}: IAttributeFilterSectionProps) {
	const uneditableIds = Object.keys(attributes);

	return (
		<div className="align-items-center attribute-filter-section-root d-flex">
			<div className="section-header">
				{Liferay.Language.get('filter')}
			</div>

			{!!eventId && (
				<div className="align-items-center attribute-container d-flex justify-content-between">
					<DndProvider backend={HTML5Backend}>
						<div className="align-items-center attribute-list d-flex">
							{filterOrder.map((id, i) => (
								<AttributeFilterChip
									attribute={
										attributes[filters[id].attributeId]
									}
									eventId={eventId}
									filter={filters[id]}
									index={i}
									key={id}
									onCloseClick={deleteFilter}
									onMove={moveFilter}
									uneditableIds={uneditableIds}
								/>
							))}
						</div>
					</DndProvider>

					<AttributeFilterDropdown
						alignmentPosition={Align.LeftTop}
						eventId={eventId}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get('add')}
								borderless
								className="add-attribute button-root"
								displayType="secondary"
								size="sm"
							>
								<ClayIcon className="icon-root" symbol="plus" />
							</ClayButton>
						}
						uneditableIds={uneditableIds}
					/>
				</div>
			)}
		</div>
	);
};

export default withAttributesConsumer(AttributeFilterSection);
