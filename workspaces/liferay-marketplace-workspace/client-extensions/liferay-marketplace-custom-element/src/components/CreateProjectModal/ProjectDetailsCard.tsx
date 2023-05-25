/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import calendarIcon from '../../assets/icons/calendar_month_icon.svg';
import githubIcon from '../../assets/icons/github_icon.svg';
import guideIcon from '../../assets/icons/guide_icon.svg';
import liferayIcon from '../../assets/icons/liferay_icon.svg';
import listIcon from '../../assets/icons/list_alt_icon.svg';
import serverIcon from '../../assets/icons/server_icon.svg';

const projectDetailsCardValues = [
	{
		description: '1 Site',
		icon: guideIcon,
		title: 'Sites',
	},
	{
		description: '10 GB',
		icon: serverIcon,
		title: 'Storage',
	},
	{
		description: 'Yes',
		icon: listIcon,
		title: 'Extensions Environment',
	},
	{
		description: 'Yes',
		icon: githubIcon,
		title: 'Github Access',
	},
	{
		description: '60 days',
		icon: calendarIcon,
		title: 'Duration',
	},
];

interface ProjectDetailsCardProps {
	showHeader?: boolean;
}

export function ProjectDetailsCard({
	showHeader = false,
}: ProjectDetailsCardProps) {
	return (
		<div className="create-project-modal-project-details-card">
			{showHeader && (
				<div className="create-project-modal-project-details-card-header">
					<img
						alt="Liferay Icon"
						className="create-project-modal-project-details-card-header-icon"
						src={liferayIcon}
					/>

					<div className="create-project-modal-project-details-card-header-text-container">
						<span className="create-project-modal-project-details-card-header-title">
							Solutions in progress
						</span>

						<span className="create-project-modal-project-details-card-header-description">
							Created by Gloria Davis (you)
						</span>
					</div>
				</div>
			)}

			<span className="create-project-modal-project-details-card-title">
				Project details
			</span>

			<div className="create-project-modal-project-details-card-info-block-container">
				{projectDetailsCardValues.map((cardValues, i) => (
					<div
						className="create-project-modal-project-details-card-info-block"
						key={cardValues.title + i}
					>
						<div className="create-project-modal-project-details-card-info-block-icon-container">
							<img src={cardValues.icon} />
						</div>

						<div className="create-project-modal-project-details-card-info-block-text-container">
							<span className="create-project-modal-project-details-card-info-block-text-title">
								{cardValues.title}
							</span>

							<span className="create-project-modal-project-details-card-info-block-text-description">
								{cardValues.description}
							</span>
						</div>
					</div>
				))}
			</div>
		</div>
	);
}
