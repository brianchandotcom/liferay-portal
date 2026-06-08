/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sum} from 'lodash';
import {RangeSelectors} from '~/shared/types';
import {WEEKDAYS} from '~/shared/util/date';
import {safeResultToProps} from '~/shared/util/mappers';
import {getSafeRangeSelectors} from '~/shared/util/util';

interface IHeatMapItem {
	column: number;
	value: number;
}

interface IVisitorsByTimeResult {
	siteVisitorHeatMap: IHeatMapItem[];
}

const mapResultToProps = safeResultToProps((result: IVisitorsByTimeResult) => {
	const data = result.siteVisitorHeatMap;
	const sumTotal = sum(data.map(({value}) => value));

	return !sumTotal
		? {data, total: 0}
		: {
				data: data.map((item) => ({
					...item,
					column: WEEKDAYS[item.column],
				})),
			};
});

const mapPropsToOptions = ({
	rangeSelectors,
	router: {
		params: {channelId},
	},
}: {
	rangeSelectors: RangeSelectors;
	router: {params: {channelId: string}};
}) => ({
	variables: {
		channelId,
		...getSafeRangeSelectors(rangeSelectors),
	},
});

export {mapPropsToOptions, mapResultToProps};
