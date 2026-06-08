/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IPagination} from '~/shared/types';
import {getGraphQLVariablesFromPagination} from '~/shared/util/pagination';

export const mapPropsToOptions = function mapPropsToOptions({
	channelId,
	delta,
	orderIOMap,
	page,
	query,
}: IPagination & {channelId: string}) {
	return {
		variables: {
			...getGraphQLVariablesFromPagination({
				delta,
				orderIOMap,
				page,
				query,
			}),
			channelId,
		},
	};
};

export const getMapResultToProps = function getMapResultToProps(
	graphqlEntityType: string
) {
	return ({
		[graphqlEntityType]: {dxpEntities, total},
	}: {
		[key: string]: {
			dxpEntities: {id: string; name: string}[];
			total: number;
		};
	}) => ({
		empty: !total,
		items: dxpEntities,
		total,
	});
};
