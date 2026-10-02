import AttributeBreakdownChip from './AttributeBreakdownChip';
import AttributeBreakdownDropdown from './attribute-breakdown-dropdown';
import ConditionsSection from './ConditionsSection';
import React, {useRef} from 'react';
import useKeyboardReorder from './useKeyboardReorder';
import {
	AddBreakdown,
	AddBreakdownParams,
	DeleteBreakdown,
	useAttributes,
} from '../context/attributes';
import {Align} from '@clayui/drop-down';
import {ClayButtonWithIcon} from '@clayui/button';

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
		moveBreakdown,
	} = useAttributes();

	const sectionRef = useRef<HTMLElement>(null);

	const getKeyboardProps = useKeyboardReorder({
		count: breakdownOrder.length,
		getName: (index) => {
			const attribute =
				attributes[breakdowns[breakdownOrder[index]].attributeId];

			return attribute?.displayName || attribute?.name || '';
		},
		onMove: moveBreakdown,
	});

	if (!eventId) {
		return null;
	}

	const disabledIds = breakdownOrder.map(
		(breakdownId) => breakdowns[breakdownId].attributeId
	);

	const uneditableIds = Object.keys(attributes);

	const focusSection = () => setTimeout(() => sectionRef.current?.focus());

	const onAttributeSelect: AddBreakdown = (params: AddBreakdownParams) => {
		addBreakdown(params);

		if (breakdownOrder.length + 1 === MAX_ATTRIBUTES) {
			focusSection();
		}
	};

	const onRemove: DeleteBreakdown = (params) => {
		deleteBreakdown(params);

		focusSection();
	};

	return (
		<ConditionsSection
			action={
				breakdownOrder.length < MAX_ATTRIBUTES && (
					<AttributeBreakdownDropdown
						alignmentPosition={Align.RightTop}
						disabledIds={disabledIds}
						eventId={eventId}
						onAttributeSelect={onAttributeSelect}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'add-breakdown'
								)}
								data-html2canvas-ignore
								displayType="secondary"
								monospaced
								size="sm"
								symbol="plus"
								title={Liferay.Language.get('add-breakdown')}
							/>
						}
						uneditableIds={uneditableIds}
					/>
				)
			}
			className="attribute-breakdown-section-root"
			ref={sectionRef}
			title={Liferay.Language.get('breakdown-by')}
		>
			{!!breakdownOrder.length && (
				<div className="attribute-container attribute-list d-flex flex-column mt-3">
					{breakdownOrder.map((id, i) => (
						<AttributeBreakdownChip
							attribute={attributes[breakdowns[id].attributeId]}
							breakdown={breakdowns[id]}
							index={i}
							key={id}
							keyboard={getKeyboardProps(i)}
							onCloseClick={onRemove}
							onMove={moveBreakdown}
						/>
					))}
				</div>
			)}
		</ConditionsSection>
	);
};

export default AttributeBreakdownSection;
