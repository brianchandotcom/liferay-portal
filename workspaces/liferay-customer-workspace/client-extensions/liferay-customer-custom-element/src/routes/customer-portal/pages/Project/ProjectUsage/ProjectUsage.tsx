/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CardContainer from './components/CardContainer';
import ChartContent from './components/ChartContent';
import ProgressBarContent from './components/ProgressBarContent';

import './ProjectUsage.css';

import i18n from '~/common/I18n';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import useCurrentKoroneikiAccount from '~/common/hooks/useCurrentKoroneikiAccount';

import useMyUserAccountByAccountExternalReferenceCode from '../TeamMembers/components/TeamMembersTable/hooks/useMyUserAccountByAccountExternalReferenceCode';
import AddOnContent from './components/AddOnContent';
import ContactBanner from './components/ContactBanner';
import ProjectUsageSection from './components/ProjectUsageSection';
import useProjectUsageData from './hooks/useProjectUsageData';

const ProjectUsage = () => {
	const {addOns, displayUsage, isLoading: projectUsageLoading, usageData} = useProjectUsageData();
	const {featureFlags} = useAppPropertiesContext();

	const {data: koroneikiData, loading: koroneikiAccountLoading} =
		useCurrentKoroneikiAccount();

	const koroneikiAccount =
		koroneikiData?.koroneikiAccountByExternalReferenceCode;

	const {data: myUserAccountData, loading: myUserAccountLoading} =
		useMyUserAccountByAccountExternalReferenceCode(
			koroneikiAccountLoading,
			koroneikiAccount?.accountKey
		);

	const myUserAccount = myUserAccountData?.myUserAccount;

	const isLoading = koroneikiAccountLoading || myUserAccountLoading || projectUsageLoading;

	if (isLoading) {
		return <span
			aria-hidden="true"
			className="loading-animation loading-animation-seconday loading-animation-sm mt-10"
		/>
	}

	if (
		(!featureFlags.includes('LRSD-6322') && !featureFlags.includes('LRSD-7805')) ||
		(featureFlags.includes('LRSD-6322') && !featureFlags.includes('LRSD-7805') && !myUserAccount?.isLiferayStaff) ||
		(featureFlags.includes('LRSD-7805') && (!myUserAccount?.isLiferayStaff || !myUserAccount?.isPartner))
	) {
		return <h3>Page not found</h3>;
	}

	return (
		<div className="container-xl cp-project-usage-page m-0 p-0">
			<h2 className="mb-4">{i18n.translate('project-usage-metrics')}</h2>

			<>
				{!displayUsage && (
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

					<ProjectUsageSection
						className="mb-5"
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
						title={i18n.translate('resource-usage')}
					>
						{usageData?.resourceUsage.map(
							(chartData, index) => (
								<CardContainer
									displayUsage={displayUsage}
									infoButtonText={chartData.infoText}
									key={`${chartData.title}-${index}`}
								>
									<ChartContent
										dataSizeUnits={
											chartData.dataSizeUnits
										}
										displayUsage={displayUsage}
										maxCount={chartData.maxCount}
										maxCountText={
											chartData.maxCountText
										}
										title={chartData.title}
										usedCount={chartData.usedCount}
									/>
								</CardContainer>
							)
						)}
					</ProjectUsageSection>

					{displayUsage && (
						<>
							{!!addOns.length && (
								<ProjectUsageSection
									className="mb-5"
									title={i18n.translate('add-ons')}
								>
									{addOns?.map((addOn, index) => (
										<CardContainer
											className="align-items-center d-flex p-4"
											displayUsage={displayUsage}
											infoButtonText={addOn.infoText}
											key={`${addOn.title}-${index}`}
										>
											<AddOnContent
												title={addOn.title}
											/>
										</CardContainer>
									))}
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
		</div>
	);
};

export default ProjectUsage;
