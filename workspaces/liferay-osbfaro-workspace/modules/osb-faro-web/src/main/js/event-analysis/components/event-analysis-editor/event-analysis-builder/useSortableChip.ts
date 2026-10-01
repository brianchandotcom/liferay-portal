import {DropTargetMonitor, useDrag, useDrop} from 'react-dnd';
import {useEffect, useRef, useState} from 'react';

const DRAG_TYPE = 'breakdown-condition-chip';

export enum DragStates {
	Placeholder = 'placeholder',
	Preview = 'preview',
}

export enum HoverTypes {
	Bottom = 'bottom',
	Top = 'top',
}

interface DragItem {
	index: number;
	type: string;
}

const useSortableChip = ({
	index,
	onMove,
}: {
	index: number;
	onMove: (params: {from: number; to: number}) => void;
}) => {
	const chipRef = useRef<HTMLDivElement>(null);
	const containerRef = useRef<HTMLDivElement>(null);

	const [hoverPosition, setHoverPosition] = useState<HoverTypes | null>(null);

	const [{canDrop, isOver}, drop] = useDrop({
		accept: DRAG_TYPE,
		canDrop: ({index: dragIndex}: DragItem) => dragIndex !== index,
		collect: (monitor: DropTargetMonitor) => ({
			canDrop: monitor.canDrop(),
			isOver: monitor.isOver(),
		}),
		drop: ({index: dragIndex}: DragItem) => {
			if (!hoverPosition) {
				return;
			}

			let dropIndex = index;

			if (hoverPosition === HoverTypes.Top && dragIndex < index) {
				dropIndex = index - 1;
			}
			else if (
				hoverPosition === HoverTypes.Bottom &&
				dragIndex > index
			) {
				dropIndex = index + 1;
			}

			onMove({from: dragIndex, to: dropIndex});
		},
		hover: ({index: dragIndex}: DragItem, monitor: DropTargetMonitor) => {
			if (!containerRef.current) {
				return;
			}

			const {height, top} = containerRef.current.getBoundingClientRect();

			const {y} = monitor.getClientOffset() ?? {y: 0};

			const hoverTop = y < top + height / 2;

			if ((hoverTop ? index - 1 : index + 1) === dragIndex) {
				setHoverPosition(null);
			}
			else {
				setHoverPosition(hoverTop ? HoverTypes.Top : HoverTypes.Bottom);
			}
		},
	});

	const [{isDragging}, drag, preview] = useDrag({
		collect: (monitor: any) => ({
			isDragging: monitor.isDragging(),
		}),
		item: {index, type: DRAG_TYPE},
	});

	const [placeholder, setPlaceholder] = useState(false);

	useEffect(() => {
		if (!isDragging) {
			setPlaceholder(false);

			return;
		}

		const frame = requestAnimationFrame(() => setPlaceholder(true));

		return () => cancelAnimationFrame(frame);
	}, [isDragging]);

	useEffect(() => {
		drag(chipRef);
		drop(containerRef);
		preview(chipRef, {captureDraggingState: true});
	}, []);

	let dragState: DragStates | undefined;

	if (isDragging) {
		dragState = placeholder ? DragStates.Placeholder : DragStates.Preview;
	}

	return {
		chipRef,
		containerRef,
		dragState,
		hoverPosition: isOver && canDrop ? hoverPosition : null,
	};
};

export default useSortableChip;
