/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import InterestsBase from '~/sites/hocs/Interests';

const Interests = InterestsBase as React.ComponentType<any>;

interface IInterestsPageProps extends React.HTMLAttributes<HTMLDivElement> {
	router: {
		params: object;
	};
}

export default class InterestsPage extends React.Component<IInterestsPageProps> {
	render() {
		const {router} = this.props;

		return (
			<div className="sites-dashboard-interests-root">
				<div className="row">
					<div className="col-xl-12">
						<Interests router={router} />
					</div>
				</div>
			</div>
		);
	}
}
