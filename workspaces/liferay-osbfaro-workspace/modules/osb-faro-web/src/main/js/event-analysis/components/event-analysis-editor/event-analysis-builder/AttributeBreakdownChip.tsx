import React from 'react';
import SortableConditionChip from './SortableConditionChip';
import {Attribute, Breakdown} from 'event-analysis/utils/types';
import {
	DATA_TYPE_ICONS_MAP,
	getBreakdownDisplay,
} from 'event-analysis/utils/utils';
import {DeleteBreakdown} from '../context/attributes';
import {IKeyboardReorderProps} from './useKeyboardReorder';
import {SortableChipTypes} from './useSortableChip';

const AttributeBreakdownChip: React.FC<{
	attribute: Attribute;
	breakdown: Breakdown;
	index: number;
	keyboard?: IKeyboardReorderProps;
	onCloseClick: DeleteBreakdown;
	onMove: (params: {from: number; to: number}) => void;
}> = ({attribute, breakdown, index, keyboard, onCloseClick, onMove}) => {
	const [overline, label] = getBreakdownDisplay(
		attribute,
		breakdown.attributeType
	);

	return (
		<SortableConditionChip
			dragType={SortableChipTypes.Breakdown}
			icon={DATA_TYPE_ICONS_MAP[breakdown.dataType]}
			index={index}
			keyboard={keyboard}
			label={label}
			name={
				breakdown.displayName || attribute.displayName || attribute.name
			}
			onMove={onMove}
			onRemove={() => onCloseClick({id: breakdown.id ?? ''})}
			overline={overline}
		/>
	);
};

export default AttributeBreakdownChip;
