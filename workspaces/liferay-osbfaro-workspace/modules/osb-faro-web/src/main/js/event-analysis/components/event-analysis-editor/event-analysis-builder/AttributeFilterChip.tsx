import AttributeFilterDropdown from './attribute-filter-dropdown';
import React from 'react';
import SortableConditionChip from './SortableConditionChip';
import {Attribute, Filter} from 'event-analysis/utils/types';
import {
	DATA_TYPE_ICONS_MAP,
	getFilterDisplay,
} from 'event-analysis/utils/utils';
import {DeleteFilter} from '../context/attributes';
import {getSafeDecodedURIComponent} from 'shared/util/util';
import {SortableChipTypes} from './useSortableChip';

const AttributeFilterChip: React.FC<{
	attribute: Attribute;
	eventId: string;
	filter: Filter;
	index: number;
	onCloseClick: DeleteFilter;
	onMove: (params: {from: number; to: number}) => void;
	uneditableIds: string[];
}> = ({
	attribute,
	eventId,
	filter,
	index,
	onCloseClick,
	onMove,
	uneditableIds,
}) => {
	const [overline, label] = getFilterDisplay(attribute, filter);

	const {dataType, description, displayName} = filter;

	return (
		<AttributeFilterDropdown
			attribute={{...attribute, dataType, description, displayName}}
			eventId={eventId}
			filter={filter}
			trigger={
				<SortableConditionChip
					dragType={SortableChipTypes.Filter}
					icon={DATA_TYPE_ICONS_MAP[dataType]}
					index={index}
					label={getSafeDecodedURIComponent(label)}
					name={
						displayName || attribute.displayName || attribute.name
					}
					onMove={onMove}
					onRemove={() => onCloseClick({id: filter.id ?? ''})}
					overline={overline}
				/>
			}
			uneditableIds={uneditableIds}
		/>
	);
};

export default AttributeFilterChip;
