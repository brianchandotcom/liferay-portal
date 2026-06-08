/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {noop, values} from 'lodash';
import React from 'react';
import Alert, {AlertTypes} from '~/shared/components/Alert';

import Item from '../components/Item';

export default class AlertKit extends React.Component {
	render() {
		return (
			<div>
				<div>
					{values(AlertTypes).map((type) => (
						<Item key={type}>
							<Alert type={type}>{type}</Alert>
						</Item>
					))}
				</div>

				<Item>
					<Alert title="Basic Alert" type={AlertTypes.Success}>
						This is a basic alert.
					</Alert>
				</Item>

				<Item>
					<Alert
						onClose={noop}
						title="Dismissable Alert"
						type={AlertTypes.Success}
					>
						This is a dismissable alert.
					</Alert>
				</Item>

				<Item>
					<Alert
						stripe
						title="Alert Stripe"
						type={AlertTypes.Success}
					>
						Check out this Alert Stripe.
					</Alert>
				</Item>
			</div>
		);
	}
}
