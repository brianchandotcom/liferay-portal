import Canvas from 'shared/components/canvas/Canvas';
import React from 'react';
import {
	AddProperty,
	withReferencedObjectsConsumer,
} from '../context/referencedObjects';
import {compose} from 'redux';
import {
	ConnectDropTarget,
	DropTarget as dropTarget,
	DropTargetMonitor,
} from 'react-dnd';
import {DragTypes} from '../utils/drag-types';
import {DropState} from 'shared/components/canvas/CanvasEmptyState';
import {OnCriterionAdd} from '../utils/types';

/**
 * Prevents items from being dropped from other contributors.
 * This method must be called `canDrop`.
 * @returns {boolean} True if the target should accept the item.
 */
const canDrop = (): boolean => true;

/**
 * Implements the behavior of what will occur when an item is dropped.
 * Adds the criterion dropped.
 * This method must be called `drop`.
 */
const drop = (
	{
		addProperty,
		onCriterionAdd,
	}: {
		addProperty: AddProperty;
		onCriterionAdd: OnCriterionAdd;
	},
	monitor: DropTargetMonitor
): void => {
	const {criterion, property} = monitor.getItem();

	if (property) {
		addProperty(property);
	}

	onCriterionAdd(0, criterion);
};

interface IEmptyDropZoneProps {
	addProperty: AddProperty;
	canDrop: boolean;
	connectDropTarget: ConnectDropTarget;
	hover?: boolean;
	onCriterionAdd: OnCriterionAdd;
}

export const EmptyDropZone: React.FC<IEmptyDropZoneProps> = ({
	canDrop,
	connectDropTarget,
	hover,
}) => {
	let dropState: DropState = 'idle';

	if (canDrop) {
		dropState = hover ? 'over' : 'dragging';
	}

	return (
		<div className="empty-drop-zone-root">
			{connectDropTarget(
				<div className="empty-drop-zone-target">
					<Canvas.EmptyState
						description={Liferay.Language.get(
							'to-create-a-new-segment-drag-items-from-the-conditions-library-and-drop-them-here'
						)}
						dropState={dropState}
						title={Liferay.Language.get('no-conditions-yet')}
					/>
				</div>
			)}
		</div>
	);
};

export default compose<React.ComponentType<any>>(
	withReferencedObjectsConsumer,
	dropTarget(
		DragTypes.Property,
		{
			canDrop,
			drop,
		},
		(connect, monitor) => ({
			canDrop: monitor.canDrop(),
			connectDropTarget: connect.dropTarget(),
			hover: monitor.isOver(),
		})
	)
)(EmptyDropZone);
