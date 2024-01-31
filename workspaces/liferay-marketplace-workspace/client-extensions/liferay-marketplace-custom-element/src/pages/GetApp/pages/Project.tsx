/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useContext, useMemo} from 'react';

import './ProjectSelection.scss';
import RadioCardList from '../../../components/RadioCardList/RadioCardList';
import {MarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import {ConsoleUserProject} from '../../../services/oauth/MarketplaceSpringBootOAuth2';
import {useGetAppContext} from '../GetAppContextProvider';
import {convertMegabyteToGigabyte} from '../hooks/useGetResourceInfo';

const getCardContent = (project: ConsoleUserProject) => {
	const cpu = project.rootProjectPlanUsage.cpu.limit;
	const environment = project.environments.length;
	const memory = convertMegabyteToGigabyte({
		inverseOperation: true,
		value: project.rootProjectPlanUsage.memory.limit,
	});

	return `${environment} Environments , ${cpu} CPUs, ${memory} GB Ram`;
};

const ProjectSelection = () => {
	const [
		{
			account,
			appResourceInfo: {resourceRequest},
			project: selectedProject,
		},
		dispatch,
	] = useGetAppContext();

	const {properties} = useContext(MarketplaceContext);

	const userProjects = useMemo(() => resourceRequest?.userProjects ?? [], [
		resourceRequest?.userProjects,
	]);

	if (!resourceRequest) {
		return <ClayLoadingIndicator />;
	}

	return (
		<>
			<h1 className="my-4 text-center">Project Selection</h1>

			<p className="my-4 secondary-text">
				{`${account?.name} projects available for `}

				<strong>{Liferay.ThemeDisplay.getUserEmailAddress()}</strong>

				<span>{` (you)`}</span>
			</p>

			<RadioCardList
				contentList={
					userProjects.map((project, index) => ({
						fullTitle: true,
						selected:
							userProjects[index].rootProjectId ===
							selectedProject,
						title: (
							<div className="d-flex">
								<div>
									<h5 className="m-0 project-selection-page-title-text">
										{project.rootProjectId.toUpperCase()}
									</h5>

									<p className="m-0 project-selection-page-description-text">
										{getCardContent(project)}
									</p>
								</div>
								<div className="d-flex justify-content-end w-100">
									<ClayButton className="project-selection-page-info-button">
										<ClayIcon
											className="project-selection-page-info-button-icon"
											symbol="question-circle"
										/>
									</ClayButton>
								</div>
							</div>
						),
						value: project.rootProjectId,
					})) as any
				}
				leftRadio
				onSelect={(radioOption: RadioOption<ConsoleUserProject>) =>
					dispatch({
						payload: (radioOption.value as unknown) as string,
						type: 'SET_PROJECT',
					})
				}
				showImage={false}
			/>

			<p className="secondary-text">
				{`${i18n.translate('not-seeing-a-specific-project')} `}
				<a
					className="font-weight-bold project-selection-page-link"
					href={properties.contactSupportUrl}
				>
					{i18n.translate('contact-support')}
				</a>
			</p>
		</>
	);
};

export default ProjectSelection;
