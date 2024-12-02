/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';

import CardContainer from '../CardContainer';

interface IProps {
	children?: ReactNode;
	className?: string;
	isLoading?: boolean;
	title?: string;
}

const CONTENT_SKELETON_QUANTITY = 3;

const ProjectUsageSection: React.FC<IProps> = ({
	children,
	className,
	isLoading,
	title,
}) => {
	return (
		<div className={`${className}`}>
			<h3 className="mb-3">{title}</h3>

			<div className="d-grid">
				{isLoading
					? [...Array(CONTENT_SKELETON_QUANTITY)].map((_, index) => (
							<CardContainer
								displayUsage={false}
								key={`${title}-${index}-loading`}
							/>
						))
					: children}
			</div>
		</div>
	);
};

export default ProjectUsageSection;
