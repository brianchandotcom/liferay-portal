/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ToggleSwitchModal from '~/shared/components/modals/ToggleSwitchModal';

class ToggleSwitchModalKit extends React.Component {
	handleSubmit(val) {
		alert(JSON.stringify(val));
	}

	render() {
		return (
			<div>
				<ToggleSwitchModal
					items={['foo', 'bar', 'baz']}
					message="Select which items you want to toggle"
					onSubmit={this.handleSubmit}
					title="Toggle some options!"
				/>

				<ToggleSwitchModal
					items={['test', 'more', 'stuff']}
					message="Even add a Toggle All switch"
					onSubmit={this.handleSubmit}
					title="W/ Toggle All"
					toggleAllMessage="Toggle All"
				/>
			</div>
		);
	}
}

export default ToggleSwitchModalKit;
