/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {noop} from 'lodash';
import React from 'react';
import Checkbox from '~/shared/components/Checkbox';

import Item from '../components/Item';
import Row from '../components/Row';

class CheckboxKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<Item>
						<Checkbox onChange={noop} />
					</Item>
					<Item>
						<Checkbox checked onChange={noop} />
					</Item>
					<Item>
						<Checkbox checked indeterminate onChange={noop} />
					</Item>
				</Row>

				<Row>
					<Item>
						<Checkbox label="Checkbox Label" onChange={noop} />
					</Item>
					<Item>
						<Checkbox checked onChange={noop} />
					</Item>
					<Item>
						<Checkbox checked indeterminate onChange={noop} />
					</Item>
				</Row>
			</div>
		);
	}
}

export default CheckboxKit;
