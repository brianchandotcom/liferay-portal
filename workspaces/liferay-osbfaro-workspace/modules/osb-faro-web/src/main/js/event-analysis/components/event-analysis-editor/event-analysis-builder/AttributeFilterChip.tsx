import React from 'react';
import SortableConditionChip from 'shared/components/condition-chip/SortableConditionChip';
import {Attribute, Filter} from 'event-analysis/utils/types';
import {getFilterDisplay} from 'event-analysis/utils/utils';
import {DeleteFilter} from '../context/attributes';
import {getSafeDecodedURIComponent} from 'shared/util/util';
import {IKeyboardReorderProps} from 'shared/components/condition-chip/useKeyboardReorder';
import {SortableChipTypes} from './SortableChipTypes';

const AttributeFilterChip: React.FC<{
	attribute: Attribute;
	filter: Filter;
	index: number;
	keyboard?: IKeyboardReorderProps;
	onCloseClick: DeleteFilter;
	onMove: (params: {from: number; to: number}) => void;
}> = ({attribute, filter, index, keyboard, onCloseClick, onMove}) => {
	const [overline, label] = getFilterDisplay(attribute, filter);

	return (
		<SortableConditionChip
			dragType={SortableChipTypes.Filter}
			index={index}
			keyboard={keyboard}
			label={getSafeDecodedURIComponent(label)}
			name={filter.displayName || attribute.displayName || attribute.name}
			onMove={onMove}
			onRemove={() => onCloseClick({id: filter.id ?? ''})}
			overline={overline}
			sticker={{dataType: filter.dataType}}
		/>
	);
};

export default AttributeFilterChip;
