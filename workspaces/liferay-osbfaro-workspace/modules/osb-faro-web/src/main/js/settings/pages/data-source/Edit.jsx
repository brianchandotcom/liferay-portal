/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';
import {compose, withAdminPermission, withDataSource} from '~/shared/hoc';
import {DataSourceTypes} from '~/shared/util/constants';
import omitDefinedProps from '~/shared/util/omitDefinedProps';
import {DataSource} from '~/shared/util/records';

import {ConfigureCSV} from './ConfigureCSV';

const PAGE_MAP = {
	[DataSourceTypes.Csv]: ConfigureCSV,
};

export class Edit extends React.Component {
	static propTypes = {
		dataSource: PropTypes.instanceOf(DataSource).isRequired,
	};

	render() {
		const {dataSource, ...otherProps} = this.props;

		const Page = PAGE_MAP[dataSource.providerType];

		if (Page) {
			return (
				<Page
					{...omitDefinedProps(otherProps, Edit.propTypes)}
					dataSource={dataSource}
				/>
			);
		}
	}
}

export default compose(withAdminPermission, withDataSource)(Edit);
