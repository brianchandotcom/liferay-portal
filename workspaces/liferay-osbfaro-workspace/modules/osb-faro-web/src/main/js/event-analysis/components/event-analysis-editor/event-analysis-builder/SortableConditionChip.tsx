import ClayIcon from '@clayui/icon';
import ConditionChip from './ConditionChip';
import getCN from 'classnames';
import React from 'react';
import useSortableChip, {SortableChipTypes} from './useSortableChip';
import {mergeRef} from 'shared/util/util';

interface ISortableConditionChipProps
	extends React.ComponentProps<typeof ConditionChip> {
	dragType: SortableChipTypes;
	index: number;
	onMove: (params: {from: number; to: number}) => void;
}

const SortableConditionChip = React.forwardRef<
	HTMLDivElement,
	ISortableConditionChipProps
>(({dragType, index, onMove, ...otherProps}, ref) => {
	const {chipRef, containerRef, dragState, hoverPosition} = useSortableChip({
		index,
		onMove,
		type: dragType,
	});

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
					<span className="drag-handle pl-2" data-html2canvas-ignore>
						<ClayIcon symbol="drag" />
					</span>
				}
				ref={mergeRef(ref, chipRef)}
			/>
		</div>
	);
});

export default SortableConditionChip;
