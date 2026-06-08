/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {noop} from 'lodash';
import React from 'react';
import DataTransformation from '~/settings/components/DataTransformation';
import * as API from '~/shared/api';
import * as data from '~/test/data';

import Item from '../components/Item';
import Row from '../components/Row';

Object.assign(API.dataSource.fetchMappings, () =>
	Promise.resolve([
		data.mockFieldMapping(1, {
			name: 'foo',
			suggestions: [data.mockFieldMapping(2, {name: 'bar'})],
		}),
	])
);

const DefaultDataTransformation = (props = {}) => (
	<DataTransformation
		addAlert={noop}
		groupId="23"
		onSubmit={noop}
		{...props}
	/>
);

export default class DataTransformationKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Item>
						<DefaultDataTransformation />
					</Item>
				</Row>
			</div>
		);
	}
}
