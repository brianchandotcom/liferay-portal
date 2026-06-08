/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import React from 'react';
import Card from '~/shared/components/Card';
import Form from '~/shared/components/form';

const SegmentEnabledSequentialCard = () => (
	<Card>
		<Card.Header>
			<Card.Title>{Liferay.Language.get('order')}</Card.Title>
		</Card.Header>

		<Card.Body>
			<p>
				<Form.ToggleSwitch
					className="sequential"
					label={Liferay.Language.get('enable-sequential')}
					name="sequential"
				/>
			</p>

			<Text color="secondary" size={3}>
				{Liferay.Language.get(
					'when-this-is-enabled,the-second-event-must-come-after-the-first-event,-with-any-number-of-events-in-between'
				)}
			</Text>
		</Card.Body>
	</Card>
);

export {SegmentEnabledSequentialCard};
