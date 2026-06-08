/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderByDirections} from '~/shared/util/constants';
import {COUNT, getSortFromOrderIOMap} from '~/shared/util/pagination';

export {getMapResultToProps} from '~/sites/hocs/mappers/composition-query';

interface IMapPropsArgs {
	channelId: string;
	delta: number;
	id: string;
	orderIOMap: unknown;
	page: number;
	query: string;
}

const mapPropsToOptions = ({
	channelId,
	delta,
	id,
	orderIOMap,
	page,
	query,
}: IMapPropsArgs) => ({
	variables: {
		active: true,
		channelId,
		id,
		keywords: query,
		size: delta,
		sort: getSortFromOrderIOMap(orderIOMap),
		start: (page - 1) * delta,
	},
});

const mapCardPropsToOptions = ({
	channelId,
	id,
}: {
	channelId: string;
	id: string;
}) => ({
	variables: {
		active: true,
		channelId,
		id,
		size: 5,
		sort: {
			column: COUNT,
			type: OrderByDirections.Descending,
		},
		start: 0,
	},
});

export {mapCardPropsToOptions, mapPropsToOptions};
