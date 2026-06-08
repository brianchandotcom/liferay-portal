/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import IntervalSelector from '~/shared/components/IntervalSelector';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';

const IntervalSelectorKit = () => {
	const [interval, setInterval] = useState(INTERVAL_KEY_MAP.day);

	return (
		<div>
			<IntervalSelector
				activeInterval={interval}
				onChange={setInterval}
			/>
		</div>
	);
};

export default IntervalSelectorKit;
