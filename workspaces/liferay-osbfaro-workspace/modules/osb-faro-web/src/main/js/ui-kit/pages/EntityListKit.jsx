/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {Set} from 'immutable';
import React from 'react';
import EntityList from '~/shared/components/EntityList';
import {EntityTypes} from '~/shared/util/constants';

import Row from '../components/Row';

class EntityListKit extends React.Component {
	state = {
		selectedItemsISet: new Set(),
	};

	@autobind
	handleSelectItemsChange(newVal) {
		this.setState({
			selectedItemsISet: newVal,
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<EntityList
						{...this.props}
						entityType={EntityTypes.IndividualsSegment}
						items={[
							{
								id: 1,
								name: 'Portland',
								properties: {
									global: {total: 321},
								},
								type: EntityTypes.IndividualsSegment,
							},
							{
								id: 2,
								name: 'San Diego',
								properties: {
									global: {total: 231},
								},
								type: EntityTypes.IndividualsSegment,
							},
							{
								id: 3,
								name: 'Seattle',
								properties: {
									global: {total: 123},
								},
								type: EntityTypes.IndividualsSegment,
							},
						]}
						onSelectItemsChange={this.handleSelectItemsChange}
						selectedItemsISet={this.state.selectedItemsISet}
					/>
				</Row>
			</div>
		);
	}
}

export default EntityListKit;
