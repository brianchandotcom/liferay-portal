/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {withEmpty} from '~/cerebro-shared/hocs/utils';
import Table from '~/shared/components/table';
import {compose, withPaginationBar, withToolbar} from '~/shared/hoc';

import {withError, withLoading} from './util';

const ListComponent = compose<any>(
	withToolbar({legacyDropdownRangeKey: false}),
	withPaginationBar(),
	withLoading({spacer: true}),
	withError({page: false}),
	withEmpty()
)(Table);

export default ListComponent;
