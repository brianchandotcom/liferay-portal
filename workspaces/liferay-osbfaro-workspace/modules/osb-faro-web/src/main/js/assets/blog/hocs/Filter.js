/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import getFiltersMapper from '~/cerebro-shared/hocs/mappers/filter';
import {withFilterComponent} from '~/shared/hoc/Filter';
import globalFilterAssetQuery from '~/shared/queries/globalFilterAssetQuery';

/**
 * HOC
 * @description Blogs Filters
 */
const withBlogFilter = () =>
	graphql(
		globalFilterAssetQuery('blog', 'viewsMetric'),
		getFiltersMapper((result) => result.blog.viewsMetric)
	);

export default withFilterComponent(withBlogFilter);
