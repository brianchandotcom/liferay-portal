import React from 'react';
import SortableConditionChip from './SortableConditionChip';
import {Attribute, Filter} from 'event-analysis/utils/types';
import {
	DATA_TYPE_ICONS_MAP,
	getFilterDisplay,
} from 'event-analysis/utils/utils';
import {DeleteFilter} from '../context/attributes';
import {getSafeDecodedURIComponent} from 'shared/util/util';
import {IKeyboardReorderProps} from './useKeyboardReorder';
import {SortableChipTypes} from './useSortableChip';

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
			icon={DATA_TYPE_ICONS_MAP[filter.dataType]}
			index={index}
			keyboard={keyboard}
			label={getSafeDecodedURIComponent(label)}
			name={filter.displayName || attribute.displayName || attribute.name}
			onMove={onMove}
			onRemove={() => onCloseClick({id: filter.id ?? ''})}
			overline={overline}
		/>
	);
};

export default AttributeFilterChip;
