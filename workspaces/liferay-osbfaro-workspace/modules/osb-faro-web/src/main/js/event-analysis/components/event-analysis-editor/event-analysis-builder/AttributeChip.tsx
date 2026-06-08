/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React, {useEffect, useRef, useState} from 'react';
import {DropTargetMonitor, useDrag, useDrop} from 'react-dnd';
import {DataTypes} from '~/event-analysis/utils/types';
import {DATA_TYPE_ICONS_MAP} from '~/event-analysis/utils/utils';
import Chip from '~/shared/components/Chip';
import {mergeRef} from '~/shared/util/util';

import {DeleteBreakdown, DeleteFilter} from '../context/attributes';

interface DragItem {
	index: number;
	type: DragTypes;
}

export enum DragTypes {
	AttributeBreakdownChip = 'attribute-breakdown-chip',
	AttributeFilterChip = 'attribute-filter-chip',
}

export enum HoverTypes {
	Left = 'left',
	Right = 'right',
}

interface IAttributeChipProps {
	dataType: DataTypes;
	description?: string;
	displayName: string;
	dragType: DragTypes;
	draggable?: boolean;
	id: string;
	index: number;
	label: string;
	onCloseClick: DeleteBreakdown | DeleteFilter;
	onMove: (params: {from: number; to: number}) => void;
	value: boolean | number | string;
}

const AttributeChip: React.FC<IAttributeChipProps> = React.forwardRef<
	any,
	IAttributeChipProps & {onClick?: () => void}
>(
	(
		{
			dataType,
			dragType,
			draggable = true,
			id,
			index,
			label,
			onClick,
			onCloseClick,
			onMove,
			value,
		},
		ref
	) => {
		const _chipRef = useRef<HTMLDivElement>(null);
		const _wrapperRef = useRef<HTMLDivElement>(null);

		const [hoverPosition, setHoverPosition] = useState<HoverTypes | null>(
			null
		);

		const [{canDrop, isOver}, drop] = useDrop({
			accept: dragType,
			canDrop: ({index: dragIndex}: DragItem) => {
				const dropIndex = index;

				return dragIndex !== dropIndex;
			},
			collect: (monitor: DropTargetMonitor) => ({
				canDrop: monitor.canDrop(),
				isOver: monitor.isOver(),
			}),
			drop: ({index: dragIndex}: DragItem) => {
				let dropIndex = index;

				const insertLeft =
					hoverPosition === HoverTypes.Left && dragIndex < dropIndex;

				const insertRight =
					hoverPosition === HoverTypes.Right && dragIndex > dropIndex;

				if (insertLeft) {
					dropIndex = index - 1;
				}
				else if (insertRight) {
					dropIndex = index + 1;
				}

				onMove({from: dragIndex, to: dropIndex});
			},
			hover: (
				{index: dragIndex}: DragItem,
				monitor: DropTargetMonitor
			) => {
				const hoverIndex = index;

				// Determine whether hover is on left or right side of hovered AttributeChip

				if (_wrapperRef.current) {
					const {right, width} =
						_wrapperRef.current.getBoundingClientRect();

					const targetMiddleX = width / 2;

					const {x} = monitor.getClientOffset() ?? {x: 0};

					const hoverLeft = x < right - targetMiddleX;

					const destIndex = hoverLeft
						? hoverIndex - 1
						: hoverIndex + 1;

					if (destIndex === dragIndex) {
						setHoverPosition(null);
					}
					else if (hoverLeft) {
						setHoverPosition(HoverTypes.Left);
					}
					else {
						setHoverPosition(HoverTypes.Right);
					}
				}
			},
		});

		const [{isDragging}, drag, preview] = useDrag({
			collect: (monitor: any) => ({
				isDragging: monitor.isDragging(),
			}),
			item: {
				index,
				type: dragType,
			},
		});

		useEffect(() => {
			drop(_wrapperRef);
			preview(_chipRef);

			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, []);

		return (
			<div
				className={getCN('attribute-chip-container', {
					[`hover-${hoverPosition}`]:
						isOver && canDrop && hoverPosition,
				})}
				ref={_wrapperRef}
			>
				<Chip
					className={getCN('attribute-chip-root', {
						dragging: isDragging,
					})}
					onCloseClick={() => onCloseClick({id})}
					ref={mergeRef(ref, _chipRef)}
				>
					{draggable && (
						<div className="drag-handle" ref={drag}>
							<ClayIcon className="icon-root" symbol="drag" />
						</div>
					)}

					<ClayButton
						className="button-root d-flex edit-attribute-button"
						displayType="unstyled"
						onClick={onClick}
					>
						<div className="sticker">
							<ClayIcon
								className="icon-root"
								symbol={DATA_TYPE_ICONS_MAP[dataType]}
							/>
						</div>

						<div>
							<div className="attribute-label">{label}</div>

							<div className="attribute-value">{value}</div>
						</div>
					</ClayButton>
				</Chip>
			</div>
		);
	}
);

export default AttributeChip;
