/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

interface IJoinableWorkspacesWrapperProps
	extends React.HTMLAttributes<HTMLElement> {
	details: string;
	title: string;
}

const JoinableWorkspacesWrapper: React.FC<IJoinableWorkspacesWrapperProps> = ({
	children,
	details,
	title,
}) => (
	<div className="join-container">
		<div className="title-container">
			<h2 className="title">{title}</h2>
			<div className="details-container">{details}</div>
		</div>
		{children}
	</div>
);

export default JoinableWorkspacesWrapper;
