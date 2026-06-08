/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fromJS} from 'immutable';
import React from 'react';
import EntityDetailsList from '~/contacts/components/EntityDetailsList';
import * as data from '~/test/data';

export default class EntityDetailsListKit extends React.Component {
	render() {
		const mockNullField = data.mockNullFieldMapping(1, {
			context: 'organization',
		});

		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<EntityDetailsList
					demographicsIMap={fromJS(
						data.mockAccountDetails({nullField: [mockNullField]})
					)}
					groupId="23"
					title="Entity Details List"
				/>
			</div>
		);
	}
}
