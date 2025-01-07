/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';

import RadioCard from '../../../components/RadioCardList/components/RadioCard';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import ContactSupport from '../../CustomerDashboard/pages/Apps/App/CloudProvisioning/components/ContactSupport';
import {ProductCardRevamp} from '../../GetApp/components/ProductCard/ProductCard';
import {convertMegabyteToGigabyte} from '../../GetApp/hooks/useGetResourceInfo';
import {ProjectSelectionStepType} from '../types';

const ProjectSelectionStep = ({
	myUserAccount,
	projects,
	selProject,
	selectedAccount,
	setValue,
}: ProjectSelectionStepType) => {
	const uniqueAccount = myUserAccount?.accountBriefs?.length === 1;
	const uniqueProject = projects?.length === 1;
	const noCloudProjectsAvailable =
		projects?.length === 0 ||
		projects.every(
			(project) =>
				!project.environments.some((env) => env.isExtensionEnvironment)
		);
	const navigate = useNavigate();

	if (uniqueProject) {
		setValue('project', projects[0]);
		navigate('/environment-selection');
	}

	useEffect(() => {
		if (!selectedAccount) {
			navigate('/');
		}
	}, [selectedAccount, navigate]);

	return (
		<div>
			<ProductCardRevamp
				icon={selectedAccount?.logoURL || ''}
				subtitle={myUserAccount.name}
				title={selectedAccount?.name || 'Account Name'}
			/>

			<div className="border my-7 p-3 py-6 rounded">
				{noCloudProjectsAvailable && (
					<div className="align-items-center d-flex flex-column justify-content-between mt-5 p-5">
						<h2>{i18n.translate('no-cloud-projects-available')}</h2>
						<p className="mt-4 secondary-text">
							{i18n.translate(
								'you-currently-do-not-have-access-to-any-cloud-projects-which-may-restrict-the-functionalities-available-to-you'
							)}
						</p>
					</div>
				)}

				{!noCloudProjectsAvailable && (
					<div>
						<div>
							<h1 className="align-items-center d-flex flex-column mt-2 pb-2 pt-2">
								Project Selection
							</h1>

							<p className="align-items-center d-flex my-4 secondary-text">
								Projects available for{' '}
								<strong>
									{Liferay.ThemeDisplay.getUserEmailAddress()}
								</strong>{' '}
								(you)
							</p>

							{projects.map((project, index) => {
								const hasExtensionEnvironment =
									project.environments.some(
										({isExtensionEnvironment}) =>
											isExtensionEnvironment
									);

								const disabled = !hasExtensionEnvironment;

								return (
									<RadioCard
										activeRadio={
											selProject?.rootProjectId ===
											project.rootProjectId
										}
										disabled={disabled}
										fullTitle
										key={index}
										leftRadio
										selectRadio={() =>
											setValue('project', project)
										}
										title={
											<div className="d-flex justify-content-between w-100">
												<div className="d-flex flex-column w-100">
													<div className="h5 m-0 project-selection-page-title-text">
														{project.rootProjectId.toUpperCase()}
													</div>

													<p className="m-0 project-selection-page-description-text">
														{`${project.environments.length} Environments, ${project.rootProjectPlanUsage.cpu.free} CPUs, ${convertMegabyteToGigabyte(
															{
																inverseOperation:
																	true,
																value: project
																	.rootProjectPlanUsage
																	.memory
																	.free,
															}
														)} GB RAM`}
													</p>

													{!hasExtensionEnvironment && (
														<small className="text-danger">
															This project has no
															extension
															environments
														</small>
													)}
												</div>
											</div>
										}
									/>
								);
							})}
						</div>

						<ContactSupport />
					</div>
				)}

				<div className="d-flex justify-content-end mt-4">
					{!uniqueAccount && (
						<ClayButton
							className="btn-outline-secondary mr-3"
							onClick={() => navigate('/account-selection')}
						>
							Back
						</ClayButton>
					)}

					{noCloudProjectsAvailable && (
						<ClayButton
							onClick={() => navigate('/congratulations')}
						>
							Connect Anyway
						</ClayButton>
					)}

					{!noCloudProjectsAvailable && (
						<ClayButton
							disabled={!selProject}
							onClick={() => navigate('/environment-selection')}
						>
							Continue
						</ClayButton>
					)}
				</div>
			</div>
		</div>
	);
};

export default ProjectSelectionStep;
