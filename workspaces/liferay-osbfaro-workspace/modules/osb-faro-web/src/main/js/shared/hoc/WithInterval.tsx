/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useState} from 'react';
import {Interval} from '~/shared/types';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';

interface IWrappedComponentProps {
	interval: Interval;
	onChangeInterval: (val: Interval) => void;
}

const withInterval =
	(WrappedComponent: React.ComponentType<IWrappedComponentProps>) =>
	(props: Omit<IWrappedComponentProps, 'interval' | 'onChangeInterval'>) => {
		const [interval, setInterval] = useState(INTERVAL_KEY_MAP.day);
		const handleChangeInterval = useCallback(
			(newVal: Interval) => setInterval(newVal),
			[]
		);

		return (
			<WrappedComponent
				{...props}
				interval={interval}
				onChangeInterval={handleChangeInterval}
			/>
		);
	};

export default withInterval;
