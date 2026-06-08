/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ListGroup from '~/shared/components/list-group';

import Row from '../components/Row';

class ListGroupKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<ListGroup>
						<ListGroup.Item flex>
							<ListGroup.ItemField>Item 1</ListGroup.ItemField>

							<ListGroup.ItemField>ItemField</ListGroup.ItemField>

							<ListGroup.ItemField>ItemField</ListGroup.ItemField>

							<ListGroup.ItemField>ItemField</ListGroup.ItemField>
						</ListGroup.Item>

						<ListGroup.Item>
							<ListGroup.ItemField>Item 2</ListGroup.ItemField>
						</ListGroup.Item>
					</ListGroup>
				</Row>
			</div>
		);
	}
}

export default ListGroupKit;
