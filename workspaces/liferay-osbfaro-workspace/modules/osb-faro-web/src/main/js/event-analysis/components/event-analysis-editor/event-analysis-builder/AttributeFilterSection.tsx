import AttributeFilterChip from './AttributeFilterChip';
import AttributeFilterDropdown from './attribute-filter-dropdown';
import ConditionsSection from './ConditionsSection';
import React, {useRef} from 'react';
import useKeyboardReorder from './useKeyboardReorder';
import {Align} from '@clayui/drop-down';
import {ClayButtonWithIcon} from '@clayui/button';
import {DeleteFilter, useAttributes} from '../context/attributes';

interface IAttributeFilterSectionProps {
	eventId?: string;
}

const AttributeFilterSection: React.FC<IAttributeFilterSectionProps> = ({
	eventId,
}) => {
	const {attributes, deleteFilter, filterOrder, filters, moveFilter} =
		useAttributes();

	const sectionRef = useRef<HTMLElement>(null);

	const getKeyboardProps = useKeyboardReorder({
		count: filterOrder.length,
		getName: (index) => {
			const attribute =
				attributes[filters[filterOrder[index]].attributeId];

			return attribute?.displayName || attribute?.name || '';
		},
		onMove: moveFilter,
	});

	if (!eventId) {
		return null;
	}

	const uneditableIds = Object.keys(attributes);

	const onRemove: DeleteFilter = (params) => {
		deleteFilter(params);

		setTimeout(() => sectionRef.current?.focus());
	};

	return (
		<ConditionsSection
			action={
				<AttributeFilterDropdown
					alignmentPosition={Align.RightTop}
					eventId={eventId}
					trigger={
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('add-filter')}
							data-html2canvas-ignore
							displayType="secondary"
							monospaced
							size="sm"
							symbol="plus"
							title={Liferay.Language.get('add-filter')}
						/>
					}
					uneditableIds={uneditableIds}
				/>
			}
			className="attribute-filter-section-root"
			ref={sectionRef}
			title={Liferay.Language.get('filter-by')}
		>
			{!!filterOrder.length && (
				<div className="attribute-container attribute-list d-flex flex-column mt-3">
					{filterOrder.map((id, i) => (
						<AttributeFilterChip
							attribute={attributes[filters[id].attributeId]}
							eventId={eventId}
							filter={filters[id]}
							index={i}
							key={id}
							keyboard={getKeyboardProps(i)}
							onCloseClick={onRemove}
							onMove={moveFilter}
							uneditableIds={uneditableIds}
						/>
					))}
				</div>
			)}
		</ConditionsSection>
	);
};

export default AttributeFilterSection;
