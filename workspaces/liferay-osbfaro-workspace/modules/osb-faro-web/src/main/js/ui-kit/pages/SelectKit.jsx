/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Select from '~/shared/components/Select';

import Item from '../components/Item';
import Row from '../components/Row';

class SelectKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Item>
						<Select>
							<Select.Item>one</Select.Item>
							<Select.Item>two</Select.Item>
							<Select.Item>three</Select.Item>
							<Select.Item>four</Select.Item>
						</Select>
					</Item>
				</Row>

				<Row>
					<Item>
						<Select showBlankOption>
							<Select.Item>show</Select.Item>
							<Select.Item>blank</Select.Item>
							<Select.Item>option</Select.Item>
						</Select>
					</Item>
				</Row>

				<Row>
					<Item>
						<Select multiple>
							<Select.Item>one</Select.Item>
							<Select.Item>two</Select.Item>
							<Select.Item>three</Select.Item>
							<Select.Item>four</Select.Item>
						</Select>
					</Item>
				</Row>
			</div>
		);
	}
}

export default SelectKit;
