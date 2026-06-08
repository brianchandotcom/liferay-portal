/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useState} from 'react';
import {RangeSelectors} from '~/shared/types';
import {RangeKeyTimeRanges} from '~/shared/util/constants';

export interface WithRangeKeyProps {
	onRangeSelectorsChange?: (val: RangeSelectors) => void;
	rangeSelectors?: RangeSelectors;
}

const withRangeKey = <P extends WithRangeKeyProps>(
	WrappedComponent: React.ComponentType<P>
): React.FC<P> => {
	const defaultRangeSelectors = {
		rangeEnd: null,
		rangeKey: RangeKeyTimeRanges.Last30Days,
		rangeStart: null,
	};

	return ({rangeSelectors: initialRangeSelectors, ...otherProps}) => {
		const [rangeSelectors, setRangeSelectors] = useState({
			...defaultRangeSelectors,
			...initialRangeSelectors,
		});

		return (
			<WrappedComponent
				{...(otherProps as P)}
				onRangeSelectorsChange={useCallback(
					(newVal: RangeSelectors) => setRangeSelectors(newVal),
					[]
				)}
				rangeSelectors={rangeSelectors}
			/>
		);
	};
};

export default withRangeKey;
