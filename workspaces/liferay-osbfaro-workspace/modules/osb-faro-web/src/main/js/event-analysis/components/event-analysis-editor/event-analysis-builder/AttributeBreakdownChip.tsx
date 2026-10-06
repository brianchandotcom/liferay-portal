import React from 'react';
import SortableConditionChip from 'shared/components/condition-chip/SortableConditionChip';
import {Attribute, Breakdown} from 'event-analysis/utils/types';
import {getBreakdownDisplay} from 'event-analysis/utils/utils';
import {DeleteBreakdown} from '../context/attributes';
import {IKeyboardReorderProps} from 'shared/components/condition-chip/useKeyboardReorder';
import {SortableChipTypes} from './SortableChipTypes';

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
			index={index}
			keyboard={keyboard}
			label={label}
			name={
				breakdown.displayName || attribute.displayName || attribute.name
			}
			onMove={onMove}
			onRemove={() => onCloseClick({id: breakdown.id ?? ''})}
			overline={overline}
			sticker={{dataType: breakdown.dataType}}
		/>
	);
};

export default AttributeBreakdownChip;
