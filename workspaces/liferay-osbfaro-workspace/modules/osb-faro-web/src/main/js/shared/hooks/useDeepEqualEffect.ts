/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isEqual} from 'lodash';
import React from 'react';

function useUpdateValueOnChange<T>(value: T): T | undefined {
	const ref = React.useRef<T>();

	if (!isEqual(value, ref.current)) {
		ref.current = value;
	}

	return ref.current;
}

export function useDeepEqualEffect(
	callback: React.EffectCallback,
	args: React.DependencyList
) {

	// eslint-disable-next-line react-hooks/exhaustive-deps
	React.useEffect(callback, useUpdateValueOnChange(args));
}
