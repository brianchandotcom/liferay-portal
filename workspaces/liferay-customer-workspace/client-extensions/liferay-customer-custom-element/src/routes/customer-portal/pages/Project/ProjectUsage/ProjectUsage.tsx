/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CardContainer from './components/CardContainer';
import ProgressBarContent from './components/ProgressBarContent';

import './ProjectUsage.css';
import ProjectUsageSection from './components/ProjectUsageSection';
import useProjectUsageData from './hooks/useProjectUsageData';

const ProjectUsage = () => {
	const {siteAndUsersData} = useProjectUsageData();

	return (
		<div className="cp-project-usage-page m-0 p-0">
			<h2 className="mb-5">Project Usage Metrics</h2>

			<ProjectUsageSection className="mb-5" title="Sites and Users">
				{siteAndUsersData.map((chartData, index) => (
					<div
						className="col-12 col-md-6 col-xl-4 mb-3"
						key={`${chartData.title}-${index}`}
					>
						<CardContainer infoButtonText={chartData.infoText}>
							<ProgressBarContent
								maxCount={chartData.maxCount}
								title={chartData.title}
								usedCount={chartData.usedCount}
							/>
						</CardContainer>
					</div>
				))}
			</ProjectUsageSection>
		</div>
	);
};

export default ProjectUsage;
