/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Label from '~/shared/components/Label';

import Item from '../components/Item';
import Row from '../components/Row';

class LabelKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					{Object.values(Label.Displays).map((display, index) => (
						<Item key={index}>
							<Label display={display}>Label</Label>
						</Item>
					))}
				</Row>
				<Row>
					{Object.values(Label.Displays).map((display, index) => (
						<Item key={index}>
							<Label display={display}>Label</Label>
						</Item>
					))}
				</Row>
				<Row>
					{Object.values(Label.Sizes).map((size, index) => (
						<Item key={index}>
							<Label size={size}>Label</Label>
						</Item>
					))}
				</Row>
			</div>
		);
	}
}

export default LabelKit;
