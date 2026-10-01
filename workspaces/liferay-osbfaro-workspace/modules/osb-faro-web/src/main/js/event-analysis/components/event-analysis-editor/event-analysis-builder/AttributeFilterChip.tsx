import AttributeFilterDropdown from './attribute-filter-dropdown';
import ConditionChip from './ConditionChip';
import React from 'react';
import {Attribute, Filter} from 'event-analysis/utils/types';
import {
	DATA_TYPE_ICONS_MAP,
	getFilterDisplay,
} from 'event-analysis/utils/utils';
import {DeleteFilter} from '../context/attributes';
import {getSafeDecodedURIComponent} from 'shared/util/util';

const AttributeFilterChip: React.FC<{
	attribute: Attribute;
	eventId: string;
	filter: Filter;
	onCloseClick: DeleteFilter;
	uneditableIds: string[];
}> = ({attribute, eventId, filter, onCloseClick, uneditableIds}) => {
	const [overline, label] = getFilterDisplay(attribute, filter);

	const {dataType, description, displayName} = filter;

	return (
		<AttributeFilterDropdown
			attribute={{...attribute, dataType, description, displayName}}
			eventId={eventId}
			filter={filter}
			trigger={
				<ConditionChip
					icon={DATA_TYPE_ICONS_MAP[dataType]}
					label={getSafeDecodedURIComponent(label)}
					name={
						displayName || attribute.displayName || attribute.name
					}
					onRemove={() => onCloseClick({id: filter.id ?? ''})}
					overline={overline}
				/>
			}
			uneditableIds={uneditableIds}
		/>
	);
};

export default AttributeFilterChip;
