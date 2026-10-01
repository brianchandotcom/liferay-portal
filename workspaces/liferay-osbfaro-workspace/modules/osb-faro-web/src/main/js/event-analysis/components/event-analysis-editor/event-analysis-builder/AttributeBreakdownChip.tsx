import AttributeBreakdownDropdown from './attribute-breakdown-dropdown';
import React from 'react';
import SortableConditionChip from './SortableConditionChip';
import {Attribute, Breakdown} from 'event-analysis/utils/types';
import {
	DATA_TYPE_ICONS_MAP,
	getBreakdownDisplay,
} from 'event-analysis/utils/utils';
import {DeleteBreakdown, EditBreakdown} from '../context/attributes';
import {SortableChipTypes} from './useSortableChip';

type MoveBreakdown = (params: {from: number; to: number}) => void;

const AttributeBreakdownChip: React.FC<{
	attribute: Attribute;
	breakdown: Breakdown;
	disabledIds: string[];
	eventId: string;
	index: number;
	onCloseClick: DeleteBreakdown;
	onEditSubmit: EditBreakdown;
	onMove: MoveBreakdown;
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
	const [overline, label] = getBreakdownDisplay(
		attribute,
		breakdown.attributeType
	);

	const {dataType, description, displayName} = breakdown;

	return (
		<AttributeBreakdownDropdown
			attribute={{...attribute, dataType, description, displayName}}
			breakdown={breakdown}
			disabledIds={disabledIds}
			eventId={eventId}
			onAttributeSelect={onEditSubmit}
			trigger={
				<SortableConditionChip
					dragType={SortableChipTypes.Breakdown}
					icon={DATA_TYPE_ICONS_MAP[dataType]}
					index={index}
					label={label}
					name={
						displayName || attribute.displayName || attribute.name
					}
					onMove={onMove}
					onRemove={() => onCloseClick({id: breakdown.id ?? ''})}
					overline={overline}
				/>
			}
			uneditableIds={uneditableIds}
		/>
	);
};

export default AttributeBreakdownChip;
