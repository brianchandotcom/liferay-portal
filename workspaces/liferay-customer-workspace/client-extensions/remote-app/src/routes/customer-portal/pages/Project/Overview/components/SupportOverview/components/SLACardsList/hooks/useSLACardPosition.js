/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

export default function useSLACardPosition(slaCardsCount) {
	const [currentPosition, setCurrentPosition] = useState(0);
	const [lastPosition, setLastPosition] = useState();

	useEffect(() => {
		if (slaCardsCount) {
			setLastPosition(slaCardsCount - 1);
		}
	}, [slaCardsCount]);

	const changePosition = () => {
		const nextPosition = currentPosition + 1;

		if (nextPosition < slaCardsCount) {
			setCurrentPosition(nextPosition);
			setLastPosition(currentPosition);

			return;
		}

		setLastPosition(slaCardsCount - 1);
		setCurrentPosition(0);
	};

	return {changePosition, currentPosition, lastPosition};
}
