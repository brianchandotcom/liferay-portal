/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';
import Interests from '~/contacts/hoc/segment/Interests';

export default class InterestsPage extends React.Component {
	static propTypes = {
		router: PropTypes.object,
	};

	render() {
		const {router} = this.props;

		return (
			<div className="segment-interests-root">
				<div className="row">
					<div className="col-xl-12">
						<Interests router={router} />
					</div>
				</div>
			</div>
		);
	}
}
