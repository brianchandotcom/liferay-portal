/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Navigate, useNavigate} from 'react-router-dom';

import RadioCard from '../../../components/RadioCardList/components/RadioCard';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import ContactSupport from '../../CustomerDashboard/pages/Apps/App/CloudProvisioning/components/ContactSupport';
import {ProductCardRevamp} from '../../GetApp/components/ProductCard/ProductCard';
import {convertMegabyteToGigabyte} from '../../GetApp/hooks/useGetResourceInfo';
import {useOAuth2OutletContext} from '../OAuth2AuthorizeOutlet';

type ProjectSelectionProps = {
	noCloudProjectsAvailable: boolean;
};

const ProjectSelection: React.FC<ProjectSelectionProps> = ({
	noCloudProjectsAvailable,
}) => {
	const {
		project: selectedProject,
		projects,
		setValue,
	} = useOAuth2OutletContext();

	if (noCloudProjectsAvailable) {
		return (
			<div className="align-items-center d-flex flex-column justify-content-between mt-5 p-5">
				<h2>{i18n.translate('no-cloud-projects-available')}</h2>

				<p className="mt-4 secondary-text">
					{`${i18n.translate('you-do-not-have-access-to-cloud-project')} 
					${i18n.translate('this-may-restrict-the-functionality-available-to-you')}`}
				</p>
			</div>
		);
	}

	return (
		<>
			<h1 className="align-items-center d-flex flex-column pb-2">
				{i18n.translate('project-selection')}
			</h1>

			<small className="align-items-center d-flex my-4 secondary-text">
				Projects available for
				<strong className="mx-1">
					{Liferay.ThemeDisplay.getUserEmailAddress()}
				</strong>
				(you)
			</small>

			{projects.map((project, index) => {
				const hasExtensionEnvironment = project.environments.some(
					({isExtensionEnvironment}) => isExtensionEnvironment
				);

				return (
					<RadioCard
						activeRadio={
							selectedProject?.rootProjectId ===
							project.rootProjectId
						}
						disabled={!hasExtensionEnvironment}
						fullTitle
						key={index}
						leftRadio
						selectRadio={() => setValue('project', project)}
						title={
							<div className="d-flex flex-column w-100">
								<div className="h5 m-0 project-selection-page-title-text">
									{project.rootProjectId.toUpperCase()}
								</div>

								<small className="m-0 project-selection-page-description-text">
									{`${project.environments.length} Environments, ${project.rootProjectPlanUsage.cpu.free} CPUs, ${convertMegabyteToGigabyte(
										{
											inverseOperation: true,
											value: project.rootProjectPlanUsage
												.memory.free,
										}
									)} GB RAM`}
								</small>

								{!hasExtensionEnvironment && (
									<small className="text-danger">
										This project has no extension
										environments
									</small>
								)}
							</div>
						}
					/>
				);
			})}

			<ContactSupport />
		</>
	);
};

const ProjectSelectionLayout = () => {
	const {
		isLoading,
		myUserAccount,
		project: selectedProject,
		projects,
		selectedAccount,
		singleAccount,
		singleProject,
	} = useOAuth2OutletContext();

	const navigate = useNavigate();

	if (!selectedAccount) {
		return <Navigate to="/" />;
	}

	if (singleProject) {
		return <Navigate to="/environment-selection" />;
	}

	const noCloudProjectsAvailable =
		projects?.length === 0 ||
		projects.every(
			(project) =>
				!project.environments.some((env) => env.isExtensionEnvironment)
		);

	return (
		<>
			<ProductCardRevamp
				icon={selectedAccount?.logoURL as string}
				subtitle={myUserAccount.name}
				title={selectedAccount?.name}
			/>

			<div className="border my-7 p-3 pb-3 pt-6 rounded">
				{isLoading ? (
					<div className="m-6 p-8">
						<ClayLoadingIndicator
							displayType="primary"
							shape="squares"
							size="md"
						/>
					</div>
				) : (
					<>
						<ProjectSelection
							noCloudProjectsAvailable={noCloudProjectsAvailable}
						/>

						<div className="d-flex justify-content-end mt-4">
							{!singleAccount && (
								<ClayButton
									className="btn-outline-secondary mr-3"
									onClick={() => navigate('/')}
								>
									{i18n.translate('back')}
								</ClayButton>
							)}

							<ClayButton
								disabled={
									noCloudProjectsAvailable
										? false
										: !selectedProject
								}
								onClick={() =>
									navigate(
										noCloudProjectsAvailable
											? '/congratulations'
											: '/environment-selection'
									)
								}
							>
								{i18n.translate(
									noCloudProjectsAvailable
										? 'connect-anyway'
										: 'continue'
								)}
							</ClayButton>
						</div>
					</>
				)}
			</div>
		</>
	);
};

export default ProjectSelectionLayout;
