/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import CardTabs from '~/shared/components/CardTabs';

export default class CardTabsKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<CardTabs
					activeTabId="bar"
					tabs={[
						{
							secondaryInfo: 'Foo secondary info',
							tabId: 'foo',
							title: 'Foo Tab',
						},
						{
							secondaryInfo: 'Bar secondary info',
							tabId: 'bar',
							title: 'Bar Tab',
						},
					]}
				/>
			</div>
		);
	}
}
