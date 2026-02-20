/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CardContainer from './components/CardContainer';
import ChartContent from './components/ChartContent';
import ProgressBarContent from './components/ProgressBarContent';

import './ProjectUsage.css';

import {useMemo} from 'react';
import {useAppContext} from '~/features/project/context';
import i18n from '~/utils/I18n';
import {EXPERIENCE_SUBSCRIPTIONS, PLAN_SUBSCRIPTIONS} from '~/utils/constants';

import AddOnContent from './components/AddOnContent';
import ContactBanner from './components/ContactBanner';
import ProjectUsageSection from './components/ProjectUsageSection';
import useProjectUsageData from './hooks/useProjectUsageData';

const ProjectUsage = () => {
	const [{hasExperienceSubscription, hasLegacySubscription}] =
		useAppContext();

	const acceptedSubscriptions = useMemo(() => {
		if (hasExperienceSubscription) {
			return EXPERIENCE_SUBSCRIPTIONS;
		}

		return PLAN_SUBSCRIPTIONS;
	}, [hasExperienceSubscription]);

	const {
		addOns,
		displayUsage: isUsageDisplayed,
		isLoading,
		usageData,
	} = useProjectUsageData(acceptedSubscriptions, hasExperienceSubscription);

	const isLegacySubscription = hasLegacySubscription;

	const displayUsage = isUsageDisplayed && !isLegacySubscription;

	return (
		<div className="container-xl cp-project-usage-page m-0 p-0">
			<h2 className="mb-4">{i18n.translate('project-usage-metrics')}</h2>

			{isLoading && (
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-seconday loading-animation-sm mt-10"
				/>
			)}

			{!isLoading && (
				<>
					{(isLegacySubscription || !displayUsage) && (
						<ContactBanner
							className="mb-5"
							description={i18n.translate(
								'project-metrics-are-available-for-liferay-saas-customers-on-liferays-latest-usage-based-model'
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

						{!hasExperienceSubscription && (
							<ProjectUsageSection
								className="mb-5"
								title={i18n.translate('sites-and-users')}
							>
								{usageData?.siteAndUsers.map(
									(chartData: any, index: number) => (
										<CardContainer
											displayUsage={displayUsage}
											infoButtonText={chartData.infoText}
											key={`${chartData.title}-${index}`}
										>
											<ProgressBarContent
												displayUsage={displayUsage}
												maxCount={chartData?.maxCount}
												maxCountUnits={
													chartData?.maxCountUnits
												}
												percentage={
													chartData?.percentage
												}
												title={chartData?.title}
												usedCount={chartData?.usedCount}
												usedCountUnits={
													chartData?.usedCountUnits
												}
											/>
										</CardContainer>
									)
								)}
							</ProjectUsageSection>
						)}

						<ProjectUsageSection
							className={`mb-5 ${hasExperienceSubscription ? 'cp-project-usage-grid' : ''}`}
							title={i18n.translate('resource-usage')}
							tooltipClassName="text-neutral-7"
							tooltipText={`${i18n.translate('monthly-usage-overview')}: ${i18n.translate('view-a-breakdown-of-your-usage-and-overage-details-at-a-glance-values-reset-at-the-start-of-every-month')}`}
						>
							{usageData?.resourceUsage.map(
								(chartData: any, index: number) => (
									<CardContainer
										displayUsage={displayUsage}
										infoButtonText={chartData.infoText}
										key={`${chartData.title}-${index}`}
									>
										<ChartContent
											displayUsage={displayUsage}
											maxCount={chartData.maxCount}
											maxCountText={
												chartData.maxCountText
											}
											maxCountUnits={
												chartData.maxCountUnits
											}
											percentage={chartData.percentage}
											title={chartData.title}
											usedCount={chartData.usedCount}
											usedCountUnits={
												chartData.usedCountUnits
											}
										/>
									</CardContainer>
								)
							)}
						</ProjectUsageSection>

						{displayUsage && (
							<>
								{!hasExperienceSubscription &&
									!!addOns.length && (
										<ProjectUsageSection
											className="mb-5"
											title={i18n.translate('add-ons')}
										>
											{addOns?.map(
												(addOn: any, index: number) => (
													<CardContainer
														className="align-items-center d-flex p-4"
														displayUsage={
															displayUsage
														}
														infoButtonText={
															addOn.infoText
														}
														key={`${addOn.title}-${index}`}
													>
														<AddOnContent
															title={addOn.title}
														/>
													</CardContainer>
												)
											)}
										</ProjectUsageSection>
									)}

								<ContactBanner
									description={i18n.translate(
										'do-not-let-resources-limit-your-project'
									)}
									title={i18n.translate(
										'need-more-project-resources'
									)}
								/>
							</>
						)}
					</div>
				</>
			)}
		</div>
	);
};

export default ProjectUsage;
