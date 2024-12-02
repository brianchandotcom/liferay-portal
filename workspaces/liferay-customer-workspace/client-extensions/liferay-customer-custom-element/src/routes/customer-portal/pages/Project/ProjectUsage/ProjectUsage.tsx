/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CardContainer from './components/CardContainer';
import ChartContent from './components/ChartContent';
import ProgressBarContent from './components/ProgressBarContent';

import './ProjectUsage.css';

import i18n from '~/common/I18n';

import ContactBanner from './components/ContactBanner';
import ProjectUsageSection from './components/ProjectUsageSection';
import useProjectUsageData from './hooks/useProjectUsageData';

const ProjectUsage = () => {
	const {displayUsage, isLoading, usageData} = useProjectUsageData();

	return (
		<div className="container-xl cp-project-usage-page m-0 p-0">
			<h2 className="mb-4">{i18n.translate('project-usage-metrics')}</h2>

			{!displayUsage && !isLoading && (
				<ContactBanner
					className="mb-5"
					description={i18n.translate(
						'project-usage-metrics-are-available-for-liferay-saas-customers-on-liferays-latest-billing-model'
					)}
					title={i18n.translate(
						'this-project-is-on-a-legacy-billing-model'
					)}
				/>
			)}

			<div className="position-relative">
				{!displayUsage && (
					<div className="fade-panel position-absolute" />
				)}

				<ProjectUsageSection
					className="mb-5"
					isLoading={isLoading}
					title={i18n.translate('sites-and-users')}
				>
					{usageData?.siteAndUsers.map((chartData, index) => (
						<CardContainer
							displayUsage={displayUsage}
							infoButtonText={chartData.infoText}
							key={`${chartData.title}-${index}`}
						>
							<ProgressBarContent
								displayUsage={displayUsage}
								maxCount={chartData?.maxCount}
								title={chartData?.title}
								usedCount={chartData?.usedCount}
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
							displayUsage={displayUsage}
							infoButtonText={chartData.infoText}
							key={`${chartData.title}-${index}`}
						>
							<ChartContent
								dataSizeUnits={chartData.dataSizeUnits}
								displayUsage={displayUsage}
								maxCount={chartData.maxCount}
								maxCountText={chartData.maxCountText}
								title={chartData.title}
								usedCount={chartData.usedCount}
							/>
						</CardContainer>
					))}
				</ProjectUsageSection>
			</div>
		</div>
	);
};

export default ProjectUsage;
