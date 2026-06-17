import {isNull} from 'lodash';
import {useState} from 'react';

export function useSelectedPoint(): {
	hasSelectedPoint: boolean;
	onPointSelect: (point: number | undefined) => void;
	selectedPoint: number | undefined;
} {
	const [selectedPoint, onPointSelect] = useState<number>();

	return {
		hasSelectedPoint:
			!isNull(selectedPoint) &&
			selectedPoint !== undefined &&
			isFinite(selectedPoint),
		onPointSelect,
		selectedPoint,
	};
}
