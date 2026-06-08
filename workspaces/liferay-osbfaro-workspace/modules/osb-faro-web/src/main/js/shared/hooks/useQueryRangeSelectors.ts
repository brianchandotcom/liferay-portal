/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQueryParams} from '~/shared/hooks/useQueryParams';
import {RangeSelectors} from '~/shared/types';
import {RangeKeyTimeRanges} from '~/shared/util/constants';

export const DEFAULT_RANGE_SELECTORS: RangeSelectors = {
	rangeEnd: null as unknown as string,
	rangeKey: RangeKeyTimeRanges.Last30Days,
	rangeStart: null as unknown as string,
};

/**
 * Used to get undefined if there is no rangeKey on query
 * @returns {RangeSelectors | undefined}
 */

export const useUnsafeQueryRangeSelectors =
	function useUnsafeQueryRangeSelectors(): RangeSelectors | undefined {
		const {rangeEnd, rangeKey, rangeStart} = useQueryParams();

		if (!rangeKey) {
			return;
		}

		return {rangeEnd, rangeKey, rangeStart};
	};

export const useQueryRangeSelectors =
	function useQueryRangeSelectors(): RangeSelectors {
		const rangeSelectors = useUnsafeQueryRangeSelectors();

		return rangeSelectors || DEFAULT_RANGE_SELECTORS;
	};
