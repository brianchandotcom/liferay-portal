import AttributeFilterChip from './AttributeFilterChip';
import AttributeFilterDropdown from './attribute-filter-dropdown';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import DndProvider from 'shared/components/DndProvider';
import React from 'react';
import {Align} from '@clayui/drop-down';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {useAttributes} from '../context/attributes';

interface IAttributeFilterSectionProps {
	eventId?: string;
}

const AttributeFilterSection: React.FC<IAttributeFilterSectionProps> = ({
	eventId,
}) => {
	const {attributes, deleteFilter, filterOrder, filters, moveFilter} =
		useAttributes();

	const uneditableIds = Object.keys(attributes);

	return (
		<div className="attribute-filter-section-root d-flex flex-column">
			<div className="section-header">
				{Liferay.Language.get('filter')}
			</div>

			{!!eventId && (
				<div className="attribute-container d-flex flex-column align-items-start">
					<DndProvider backend={HTML5Backend}>
						<div className="attribute-list d-flex flex-column w-100">
							{filterOrder.map((id, i) => (
								<AttributeFilterChip
									attribute={
										attributes[filters[id].attributeId]
									}
									eventId={eventId}
									filter={filters[id]}
									index={i}
									key={id}
									onCloseClick={deleteFilter}
									onMove={moveFilter}
									uneditableIds={uneditableIds}
								/>
							))}
						</div>
					</DndProvider>

					<AttributeFilterDropdown
						alignmentPosition={Align.LeftTop}
						eventId={eventId}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get('add')}
								borderless
								className="button-root add-attribute"
								displayType="secondary"
								size="sm"
							>
								<ClayIcon className="icon-root" symbol="plus" />
							</ClayButton>
						}
						uneditableIds={uneditableIds}
					/>
				</div>
			)}
		</div>
	);
};

export default AttributeFilterSection;
