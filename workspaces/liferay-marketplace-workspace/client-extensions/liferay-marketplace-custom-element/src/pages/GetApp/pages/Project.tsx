/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useContext, useMemo} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import RadioCardList from '../../../components/RadioCardList/RadioCardList';
import {MarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import {ConsoleUserProject} from '../../../services/oauth/types';
import {useGetAppContext} from '../GetAppContextProvider';
import {GetAppOutletContext} from '../GetAppOutlet';
import Container from '../containers/Container';
import {convertMegabyteToGigabyte} from '../hooks/useGetResourceInfo';

import './index.scss';

const getCardContent = (project: ConsoleUserProject) => {
	const cpu =
		project.rootProjectPlanUsage.cpu.limit -
		project.rootProjectPlanUsage.cpu.used;

	const environment = project.environments.length;

	const memory = convertMegabyteToGigabyte({
		inverseOperation: true,
		value:
			project.rootProjectPlanUsage.memory.limit -
			project.rootProjectPlanUsage.memory.used,
	});

	return `${environment} Environments , ${cpu} CPUs, ${memory} GB Ram`;
};

const ProjectSelection = () => {
	const navigate = useNavigate();
	const [
		{
			account,
			appResourceInfo: {
				hasConsoleProjectsAvailable,
				hasResources,
				isLoading,
				resourceRequest,
			},
			formState: {isValid},
			project,
			project: selectedProject,
			stepState,
		},
		dispatch,
	] = useGetAppContext();

	const {handleGetApp, isFreeApp, loading} =
		useOutletContext<GetAppOutletContext>();

	const {properties} = useContext(MarketplaceContext);

	const userProjects = useMemo(
		() => resourceRequest?.userProjects ?? [],
		[resourceRequest?.userProjects]
	);

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	if (!hasConsoleProjectsAvailable) {
		return (
			<Container
				className="d-flex flex-column"
				footerProps={{
					primaryButtonProps: {
						children: i18n.translate(
							'sign-in-with-a-different-account'
						),
						onClick: () =>
							Liferay.Util.navigate('/c/portal/logout'),
					},
					secondaryButtonProps: {visible: false},
				}}
				title="No Cloud Projects Available"
			>
				<p className="my-6 text-justify">
					You are attempting to Purchase a Cloud APP that is currently
					only available for Liferay SaaS and Liferay PaaS customers.
					You currently appear to not have access to any Cloud
					Projects. Please login as a user that has access to a
					project or contact your project administrator to add you to
					a project.
				</p>
			</Container>
		);
	}

	return (
		<Container
			className="d-flex flex-column"
			footerProps={{
				primaryButtonProps: {
					children: isFreeApp
						? i18n.translate('get-app')
						: i18n.translate('continue'),
					disabled: !isValid || loading,
					onClick: () => {
						if (hasResources) {
							if (isFreeApp) {
								return handleGetApp();
							}

							return stepState.onNext();
						}

						return navigate(
							`/insuficient-resources/${project}/${
								(account as Account).id
							}`
						);
					},
				},
				secondaryButtonProps: {visible: true},
			}}
			title="Project Selection"
		>
			<p className="my-4 secondary-text">
				{`Projects and resources available for `}

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
									<div className="h5 m-0 project-selection-page-title-text">
										{project.rootProjectId.toUpperCase()}
									</div>

									<p className="m-0 project-selection-page-description-text">
										{getCardContent(project)}
									</p>
								</div>
								<div className="d-flex justify-content-end w-100">
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
							</div>
						),
						value: project.rootProjectId,
					})) as any
				}
				leftRadio
				onSelect={(radioOption: RadioOption<ConsoleUserProject>) =>
					dispatch({
						payload: radioOption.value as unknown as string,
						type: 'SET_PROJECT',
					})
				}
				showImage={false}
			/>

			<p className="secondary-text">
				{`${i18n.translate('not-seeing-a-specific-project')} `}
				<a
					className="font-weight-bold project-selection-page-link"
					href={properties.contactSupportURL}
					target="_blank"
				>
					{i18n.translate('contact-support')}
				</a>
			</p>
		</Container>
	);
};

export default ProjectSelection;
