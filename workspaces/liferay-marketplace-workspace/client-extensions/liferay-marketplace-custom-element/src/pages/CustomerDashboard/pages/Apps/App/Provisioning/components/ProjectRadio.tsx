/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {UseFormSetValue} from 'react-hook-form';
import {z} from 'zod';

import RadioCard from '../../../../../../../components/RadioCardList/components/RadioCard';
import i18n from '../../../../../../../i18n';
import zodSchema from '../../../../../../../schema/zod';
import {convertMegabyteToGigabyte} from '../../../../../../GetApp/hooks/useGetResourceInfo';
import {UserProject} from '../Types';

type ProjectRadioType = {
	projects: UserProject[];
	selectedProject: UserProject;
	setValue: UseFormSetValue<z.infer<typeof zodSchema.installProductSchema>>;
};

const ProjectRadio: React.FC<ProjectRadioType> = ({
	projects,
	selectedProject,
	setValue,
}) => {
	const handleSelectRadio = (selectedRadio: RadioOption<any>) => {
		setValue('project', selectedRadio.value);
	};

	return projects.map((userProject: UserProject, index: number) => {
		return (
			<RadioCard
				activeRadio={
					selectedProject?.rootProjectId === userProject.rootProjectId
				}
				fullTitle
				key={index}
				leftRadio
				selectRadio={() =>
					handleSelectRadio({
						index,
						value: userProject,
					})
				}
				title={
					<div className="d-flex justify-content-between w-100">
						<div className="d-flex flex-column w-100">
							<div className="h5 m-0 project-selection-page-title-text">
								{userProject.rootProjectId.toUpperCase()}
							</div>

							<p className="m-0 project-selection-page-description-text">
								{`${userProject.rootProjectPlanUsage.instance.free} Environments, ${userProject.rootProjectPlanUsage.cpu.free}CPUs, ${convertMegabyteToGigabyte(
									{
										inverseOperation: true,
										value: userProject.rootProjectPlanUsage
											.memory.free,
									}
								)}GB RAM`}
							</p>

							{!userProject?.availabilityToProduct && (
								<p className="m-0 text-danger">
									<small>
										{i18n.translate(
											'the-selected-project-does-not-meet-the-necessary-resource-requirements-for-this-app-Please-contact-sales-to-request-additional-resources'
										)}
									</small>
								</p>
							)}
						</div>

						{userProject?.availabilityToProduct && (
							<div className="align-items-center d-flex">
								<ClayButton
									aria-label="info-button"
									className="project-selection-page-info-button"
								>
									<ClayIcon
										className="project-selection-page-info-button-icon"
										symbol="question-circle"
									/>
								</ClayButton>
							</div>
						)}
					</div>
				}
			/>
		);
	});
};

export default ProjectRadio;
