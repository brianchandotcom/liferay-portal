/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { Link } from "react-router-dom";

function Breadcrumbs({breadcrumbs}) {
	return (
		<ol class="breadcrumb">
			{breadcrumbs && breadcrumbs.map(
				(breadcrumb) => {
					let breadcrumbText = (
						<span className="breadcrumb-text-truncate">
							{breadcrumb.name}
						</span>
					);

					if (breadcrumb.active) {
						return breadcrumbText;
					}

					return (
						<Link
							className="breadcrumb-link"
							title={breadcrumb.name}
							to={breadcrumb.link}
						>
							{breadcrumbText}
						</Link>
					);
				}
			)}
		</ol>
	);
}

export default Breadcrumbs;
