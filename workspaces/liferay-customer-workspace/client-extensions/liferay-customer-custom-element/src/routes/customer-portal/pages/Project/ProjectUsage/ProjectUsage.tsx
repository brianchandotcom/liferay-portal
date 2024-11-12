/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CardContainer from './components/CardContainer';
import ProgressBarContent from './components/ProgressBarContent';

import './ProjectUsage.css';
import useProjectUsageData from './hooks/useProjectUsageData';

const ProjectUsage = () => {
	const {siteAndUsersData} = useProjectUsageData();

	return (
		<div className="cp-project-usage-page m-0 p-0">
			<h2 className="mb-5">Project Usage Metrics</h2>

			<div className="align-items-center d-flex mb-3">
				<h3 className="mr-4 my-0">Sites and Users</h3>
			</div>

			<div className="mx-0 row">
				{siteAndUsersData.map((chartData, index) => {
					return (
						<CardContainer
							className="mb-3 mr-3 sites-users-card"
							infoButtonText={chartData.infoText}
							key={`${chartData.title}-${index}`}
						>
							<ProgressBarContent
								maxCount={chartData.maxCount}
								title={chartData.title}
								usedCount={chartData.usedCount}
							/>
						</CardContainer>
					);
				})}
			</div>
		</div>
	);
};

export default ProjectUsage;
