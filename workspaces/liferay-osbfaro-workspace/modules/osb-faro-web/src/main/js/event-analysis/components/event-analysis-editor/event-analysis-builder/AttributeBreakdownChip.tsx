import AttributeBreakdownDropdown from './attribute-breakdown-dropdown';
import ClayIcon from '@clayui/icon';
import ConditionChip from './ConditionChip';
import getCN from 'classnames';
import React from 'react';
import useSortableChip from './useSortableChip';
import {Attribute, Breakdown} from 'event-analysis/utils/types';
import {
	DATA_TYPE_ICONS_MAP,
	getBreakdownDisplay,
} from 'event-analysis/utils/utils';
import {DeleteBreakdown, EditBreakdown} from '../context/attributes';
import {mergeRef} from 'shared/util/util';

type MoveBreakdown = (params: {from: number; to: number}) => void;

interface ISortableChipProps
	extends React.ComponentProps<typeof ConditionChip> {
	index: number;
	onMove: MoveBreakdown;
}

const SortableChip = React.forwardRef<HTMLDivElement, ISortableChipProps>(
	({index, onMove, ...otherProps}, ref) => {
		const {chipRef, containerRef, dragState, hoverPosition} =
			useSortableChip({index, onMove});

		return (
			<div
				className={getCN('attribute-chip-container', {
					[`hover-${hoverPosition}`]: hoverPosition,
				})}
				ref={containerRef}
			>
				<ConditionChip
					{...otherProps}
					dragState={dragState}
					handle={
						<span
							className="drag-handle pl-2"
							data-html2canvas-ignore
						>
							<ClayIcon symbol="drag" />
						</span>
					}
					ref={mergeRef(ref, chipRef)}
				/>
			</div>
		);
	}
);

const AttributeBreakdownChip: React.FC<{
	attribute: Attribute;
	breakdown: Breakdown;
	disabledIds: string[];
	eventId: string;
	index: number;
	onCloseClick: DeleteBreakdown;
	onEditSubmit: EditBreakdown;
	onMove: MoveBreakdown;
	uneditableIds: string[];
}> = ({
	attribute,
	breakdown,
	disabledIds,
	eventId,
	index,
	onCloseClick,
	onEditSubmit,
	onMove,
	uneditableIds,
}) => {
	const [overline, label] = getBreakdownDisplay(
		attribute,
		breakdown.attributeType
	);

	const {dataType, description, displayName} = breakdown;

	return (
		<AttributeBreakdownDropdown
			attribute={{...attribute, dataType, description, displayName}}
			breakdown={breakdown}
			disabledIds={disabledIds}
			eventId={eventId}
			onAttributeSelect={onEditSubmit}
			trigger={
				<SortableChip
					icon={DATA_TYPE_ICONS_MAP[dataType]}
					index={index}
					label={label}
					name={
						displayName || attribute.displayName || attribute.name
					}
					onMove={onMove}
					onRemove={() => onCloseClick({id: breakdown.id ?? ''})}
					overline={overline}
				/>
			}
			uneditableIds={uneditableIds}
		/>
	);
};

export default AttributeBreakdownChip;
