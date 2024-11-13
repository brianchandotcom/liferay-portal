/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';

interface IProps {
	children?: ReactNode;
	className?: string;
	title?: string;
}

const ProjectUsageSection: React.FC<IProps> = ({
	children,
	className,
	title,
}) => {
	return (
		<div className={`${className}`}>
			<h3 className="mb-3">{title}</h3>

			<div className="col-lg-12 col-xl-11 mx-0 px-0 row">{children}</div>
		</div>
	);
};

export default ProjectUsageSection;
