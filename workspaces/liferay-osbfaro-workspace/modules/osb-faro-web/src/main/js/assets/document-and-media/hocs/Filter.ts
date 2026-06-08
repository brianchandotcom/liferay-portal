/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OperationOption, graphql} from '@apollo/client/react/hoc';
import getFiltersMapper from '~/cerebro-shared/hocs/mappers/filter';
import {withFilterComponent} from '~/shared/hoc/Filter';
import globalFilterAssetQuery from '~/shared/queries/globalFilterAssetQuery';

type DocumentMetricResult = {
	document: {
		downloadsMetric: unknown;
	};
};

/**
 * HOC
 * @description Documents And Media Filter
 */
const withDocumentsAndMediaFilter = () =>
	graphql(
		globalFilterAssetQuery('document', 'downloadsMetric'),
		getFiltersMapper(
			(result: DocumentMetricResult) => result.document.downloadsMetric
		) as OperationOption<object, object>
	);

export default withFilterComponent(withDocumentsAndMediaFilter);
