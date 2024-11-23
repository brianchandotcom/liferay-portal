/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CardContainer from './components/CardContainer';
import ProgressBarContent from './components/ProgressBarContent';

import './ProjectUsage.css';

import i18n from '~/common/I18n';

import ChartContent from './components/ChartContent';
import ProjectUsageSection from './components/ProjectUsageSection';
import useProjectUsageData from './hooks/useProjectUsageData';

const ProjectUsage = () => {
	const {isLoading, usageData} = useProjectUsageData();

	return (
		<div className="container-xl cp-project-usage-page m-0 p-0">
			<h2 className="mb-5">{i18n.translate('project-usage-metrics')}</h2>

			<ProjectUsageSection
				className="mb-5"
				isLoading={isLoading}
				title={i18n.translate('sites-and-users')}
			>
				{usageData?.siteAndUsers.map((chartData, index) => (
					<CardContainer
						infoButtonText={chartData.infoText}
						key={`${chartData.title}-${index}`}
					>
						<ProgressBarContent
							maxCount={chartData.maxCount}
							title={chartData.title}
							usedCount={chartData.usedCount}
						/>
					</CardContainer>
				))}
			</ProjectUsageSection>

			<ProjectUsageSection
				className="mb-5"
				isLoading={isLoading}
				title={i18n.translate('resource-usage')}
			>
				{usageData?.resourceUsage.map((chartData, index) => (
					<CardContainer
						infoButtonText={chartData.infoText}
						key={`${chartData.title}-${index}`}
					>
						<ChartContent
							dataSizeUnits={chartData.dataSizeUnits}
							maxCount={chartData.maxCount}
							maxCountText={chartData.maxCountText}
							title={chartData.title}
							usedCount={chartData.usedCount}
						/>
					</CardContainer>
				))}
			</ProjectUsageSection>
		</div>
	);
};

export default ProjectUsage;
