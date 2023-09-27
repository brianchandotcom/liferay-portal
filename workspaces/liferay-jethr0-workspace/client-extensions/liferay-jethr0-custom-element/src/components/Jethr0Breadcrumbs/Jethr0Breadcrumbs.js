/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Link} from 'react-router-dom';

function Jethr0Breadcrumbs({breadcrumbs}) {
	return (
		<ol className="breadcrumb">
			{breadcrumbs &&
				breadcrumbs.map((breadcrumb) => {
					if (breadcrumb.active) {
						return (
							<li
								className="breadcrumb-item"
								key={breadcrumb.name}
							>
								<span className="breadcrumb-text-truncate">
									{breadcrumb.name}
								</span>
							</li>
						);
					}

					return (
						<li className="breadcrumb-item" key={breadcrumb.name}>
							<Link
								className="breadcrumb-link"
								title={breadcrumb.name}
								to={breadcrumb.link}
							>
								<span className="breadcrumb-text-truncate">
									{breadcrumb.name}
								</span>
							</Link>
						</li>
					);
				})}
		</ol>
	);
}

export default Jethr0Breadcrumbs;
