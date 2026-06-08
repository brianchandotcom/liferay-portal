/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RangeSelectors} from '~/shared/types';
import {Filters} from '~/shared/util/filter';
import {getVariables, safeResultToProps} from '~/shared/util/mappers';

type AssetPage = {
	assetId: string;
	assetTitle?: string;
};

type TouchpointListResult = {
	assetPages?: AssetPage[];
};

const mapResultToProps = safeResultToProps(
	({assetPages}: TouchpointListResult) => {
		const items =
			assetPages &&
			assetPages.map(({assetId, assetTitle}: AssetPage) => ({
				title: assetTitle ? assetTitle : assetId,
				touchpoint: assetId,
			}));

		return {
			items,
		};
	}
);

interface IMapPropsToOptionsArgs {
	assetType: string;
	filters: Filters;
	rangeSelectors: RangeSelectors;
	router: {params: Record<string, string | undefined>};
}

/**
 * Map Props to Options
 * @param {object} param0 props
 * @param {object} param1 context
 */
const mapPropsToOptions = ({
	assetType,
	filters,
	rangeSelectors,
	router: {params},
}: IMapPropsToOptionsArgs) => {
	const {variables} = getVariables({filters, params, rangeSelectors});

	return {
		variables: {
			...variables,
			assetType: assetType.toUpperCase(),
		},
	};
};

export {mapPropsToOptions, mapResultToProps};
