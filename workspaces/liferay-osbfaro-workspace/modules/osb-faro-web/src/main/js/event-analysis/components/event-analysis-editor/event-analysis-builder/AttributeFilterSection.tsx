import AttributeFilterChip from './AttributeFilterChip';
import AttributeFilterDropdown from './attribute-filter-dropdown';
import ConditionsSection from './ConditionsSection';
import React, {useRef} from 'react';
import {Align} from '@clayui/drop-down';
import {ClayButtonWithIcon} from '@clayui/button';
import {DeleteFilter, useAttributes} from '../context/attributes';

interface IAttributeFilterSectionProps {
	eventId?: string;
}

const AttributeFilterSection: React.FC<IAttributeFilterSectionProps> = ({
	eventId,
}) => {
	const {attributes, deleteFilter, filterOrder, filters} = useAttributes();

	const sectionRef = useRef<HTMLElement>(null);

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
					{filterOrder.map((id) => (
						<AttributeFilterChip
							attribute={attributes[filters[id].attributeId]}
							eventId={eventId}
							filter={filters[id]}
							key={id}
							onCloseClick={onRemove}
							uneditableIds={uneditableIds}
						/>
					))}
				</div>
			)}
		</ConditionsSection>
	);
};

export default AttributeFilterSection;
