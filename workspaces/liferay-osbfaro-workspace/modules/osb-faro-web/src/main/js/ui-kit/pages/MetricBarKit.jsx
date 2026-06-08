/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import MetricBar, {Displays, Sizes} from '~/shared/components/MetricBar';

import Item from '../components/Item';
import Row from '../components/Row';

export default class MetricBarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				{Object.values(Sizes).map((size) => (
					<Row key={size}>
						{Object.values(Displays).map((display) => (
							<Item key={display}>
								<span
									style={{
										display: 'inline-block',
										width: '100px',
									}}
								>
									<MetricBar
										display={display}
										percent={0.3}
										size={size}
									/>
								</span>
							</Item>
						))}
					</Row>
				))}
			</div>
		);
	}
}
