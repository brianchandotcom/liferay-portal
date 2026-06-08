/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import React from 'react';
import DefinitionItem from '~/shared/components/DefinitionItem';
import Item from '~/ui-kit/components/Item';
import Row from '~/ui-kit/components/Row';

export default class DefinitionItemKit extends React.Component {
	state = {
		name: 'Test Test',
		url: 'www.liferay.com',
	};

	@autobind
	handleSubmit(value, name) {
		return Promise.resolve().then(() => this.setState({[name]: value}));
	}

	render() {
		const {name, url} = this.state;

		return (
			<div>
				<Row>
					<Item>
						<DefinitionItem
							label="URL (Non-Editable)"
							name="url"
							onSubmit={this.handleSubmit}
							value={url}
						/>
					</Item>
				</Row>

				<Row>
					<Item>
						<DefinitionItem
							editable
							label="Name (Editable)"
							name="name"
							onSubmit={this.handleSubmit}
							value={name}
						/>
					</Item>
				</Row>
			</div>
		);
	}
}
