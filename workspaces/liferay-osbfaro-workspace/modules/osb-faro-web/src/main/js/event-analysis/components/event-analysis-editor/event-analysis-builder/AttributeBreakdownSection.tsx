import AttributeBreakdownChip from './AttributeBreakdownChip';
import AttributeBreakdownDropdown from './attribute-breakdown-dropdown';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import DndProvider from 'shared/components/DndProvider';
import React from 'react';
import {
	AddBreakdown,
	AddBreakdownParams,
	EditBreakdown,
	useAttributes,
} from '../context/attributes';
import {Align} from '@clayui/drop-down';
import {HTML5Backend} from 'react-dnd-html5-backend';

const MAX_ATTRIBUTES = 5;

interface IAttributeBreakdownSectionProps {
	eventId?: string;
}

const AttributeBreakdownSection: React.FC<IAttributeBreakdownSectionProps> = ({
	eventId,
}) => {
	const {
		addBreakdown,
		attributes,
		breakdownOrder,
		breakdowns,
		deleteBreakdown,
		editBreakdown,
		moveBreakdown,
	} = useAttributes();

	const disabledIds = breakdownOrder.map(
		(breakdownId) => breakdowns[breakdownId].attributeId
	);

	const uneditableIds = Object.keys(attributes);

	const onAttributeSelect: AddBreakdown | EditBreakdown = (
		params: AddBreakdownParams
	) => {
		addBreakdown(params);
	};

	return (
		<div className="attribute-breakdown-section-root d-flex flex-column">
			<div className="section-header">
				{Liferay.Language.get('breakdown')}
			</div>

			{!!eventId && (
				<div className="attribute-container d-flex flex-column align-items-start">
					<DndProvider backend={HTML5Backend}>
						<div className="attribute-list d-flex flex-column w-100">
							{breakdownOrder.map((id, i) => (
								<AttributeBreakdownChip
									attribute={
										attributes[breakdowns[id].attributeId]
									}
									breakdown={breakdowns[id]}
									disabledIds={disabledIds}
									eventId={eventId}
									index={i}
									key={id}
									onCloseClick={deleteBreakdown}
									onEditSubmit={editBreakdown}
									onMove={moveBreakdown}
									uneditableIds={uneditableIds}
								/>
							))}
						</div>
					</DndProvider>

					{breakdownOrder.length < MAX_ATTRIBUTES && (
						<AttributeBreakdownDropdown
							alignmentPosition={Align.LeftTop}
							disabledIds={disabledIds}
							eventId={eventId}
							onAttributeSelect={onAttributeSelect}
							trigger={
								<ClayButton
									aria-label={Liferay.Language.get('add')}
									borderless
									className="button-root add-attribute"
									displayType="secondary"
									size="sm"
								>
									<ClayIcon
										className="icon-root"
										symbol="plus"
									/>
								</ClayButton>
							}
							uneditableIds={uneditableIds}
						/>
					)}
				</div>
			)}
		</div>
	);
};

export default AttributeBreakdownSection;
