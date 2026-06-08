/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ProgressTimeline from '~/shared/components/ProgressTimeline';

import Row from '../components/Row';

class ProgressTimelineKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<ProgressTimeline
						activeIndex={1}
						items={[
							{
								title: 'This is a really long title for this step',
							},
							{title: 'Step 2'},
							{title: 'Step 3'},
							{title: 'Step 4'},
						]}
					/>
				</Row>
			</div>
		);
	}
}

export default ProgressTimelineKit;
