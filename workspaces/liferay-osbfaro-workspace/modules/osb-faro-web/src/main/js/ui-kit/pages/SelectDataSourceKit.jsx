/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import SelectDataSource from '~/settings/components/SelectDataSource';

const DATA_SOURCE_ARRAY = [
	{
		dataSources: [
			{
				iconName: 'csv_logo',
				iconSize: 'xl',
				name: 'test',
				url: '#',
			},
			{
				iconName: 'csv_logo',
				iconSize: 'xl',
				name: 'test1',
				url: '#',
			},
			{
				iconName: 'liferay_logo',
				iconSize: 'xxl',
				name: 'test 2',
				url: '#',
			},
			{
				iconName: 'csv_logo',
				iconSize: 'xl',
				name: 'testing long text that should wrap',
				url: '#',
			},
		],
		title: 'These are data sources',
	},
];

class SelectDataSourceKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<SelectDataSource sections={DATA_SOURCE_ARRAY} />
			</div>
		);
	}
}

export default SelectDataSourceKit;
