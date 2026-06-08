/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Attribute, Breakdown} from '~/event-analysis/utils/types';
import {getBreakdownDisplay} from '~/event-analysis/utils/utils';

import {DeleteBreakdown, EditBreakdown} from '../context/attributes';
import AttributeChip, {DragTypes} from './AttributeChip';
import AttributeBreakdownDropdown from './attribute-breakdown-dropdown';

const AttributeBreakdownChip: React.FC<{
	attribute: Attribute;
	breakdown: Breakdown;
	disabledIds: string[];
	eventId: string;
	index: number;
	onCloseClick: DeleteBreakdown;
	onEditSubmit: EditBreakdown;
	onMove: (params: {from: number; to: number}) => void;
	uneditableIds: string[];
}> = ({
	attribute,
	breakdown,
	disabledIds,
	eventId,
	index,
	onCloseClick,
	onEditSubmit,
	onMove,
	uneditableIds,
}) => {
	const [label, value] = getBreakdownDisplay(
		attribute,
		breakdown.attributeType
	);

	const {dataType, description, displayName} = breakdown;

	const modifiedAttribute = {
		...attribute,
		dataType,
		description,
		displayName,
	};

	return (
		<AttributeBreakdownDropdown
			attribute={modifiedAttribute}
			breakdown={breakdown}
			disabledIds={disabledIds}
			eventId={eventId}
			onAttributeSelect={onEditSubmit}
			trigger={
				<AttributeChip
					dataType={dataType}
					description={description}
					displayName={displayName}
					dragType={DragTypes.AttributeBreakdownChip}
					id={breakdown.id ?? ''}
					index={index}
					label={label}
					onCloseClick={onCloseClick}
					onMove={onMove}
					value={value}
				/>
			}
			uneditableIds={uneditableIds}
		/>
	);
};

export default AttributeBreakdownChip;
