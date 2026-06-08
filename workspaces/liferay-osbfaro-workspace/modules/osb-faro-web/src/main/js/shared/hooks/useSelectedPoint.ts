/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNull} from 'lodash';
import {useState} from 'react';

export function useSelectedPoint(): {
	hasSelectedPoint: boolean;
	onPointSelect: (point: number | undefined) => void;
	selectedPoint: number | undefined;
} {
	const [selectedPoint, setSelectedPoint] = useState<number>();

	return {
		hasSelectedPoint:
			!isNull(selectedPoint) &&
			selectedPoint !== undefined &&
			isFinite(selectedPoint),
		onPointSelect: setSelectedPoint,
		selectedPoint,
	};
}
