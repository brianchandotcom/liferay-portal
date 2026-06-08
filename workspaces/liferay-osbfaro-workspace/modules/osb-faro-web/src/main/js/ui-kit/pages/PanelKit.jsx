/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Panel from '~/shared/components/Panel';

class PanelKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<div>
					<Panel title="Panel Title">Panel Content</Panel>
				</div>
				<div>
					<Panel expandable title="Expandable Panel Title">
						Expandable Panel Content
					</Panel>
				</div>
				<div>
					<Panel
						expandable
						initialExpanded
						title="Initially Expanded Expandable Panel Title"
					>
						Initially Expanded Expandable Panel Content
					</Panel>
				</div>
			</div>
		);
	}
}

export default PanelKit;
