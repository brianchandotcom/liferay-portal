/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';
import PopoverIconButton from '~/features/project/components/PopoverIconButton';

import CardContainer from '../CardContainer';

interface IProps {
	children?: ReactNode;
	className?: string;
	contentSkeleton?: number;
	isLoading?: boolean;
	title?: string;
	tooltipClassName?: string;
	tooltipText?: string;
}

const ProjectUsageSection: React.FC<IProps> = ({
	children,
	className,
	contentSkeleton = 3,
	isLoading,
	title,
	tooltipClassName,
	tooltipText,
}) => {
	return (
		<div className={`${className}`}>
			<h3 className="mb-3">
				{title}

				{tooltipText && (
					<span className="ml-1">
						<PopoverIconButton
							className={tooltipClassName}
							popoverText={tooltipText}
							symbol="question-circle"
						/>
					</span>
				)}
			</h3>

			<div className="d-grid">
				{isLoading
					? [...Array(contentSkeleton)].map((_, index) => (
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
