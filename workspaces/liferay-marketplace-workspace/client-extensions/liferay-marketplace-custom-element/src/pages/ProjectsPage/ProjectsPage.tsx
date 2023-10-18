/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useState} from 'react';

import {CreateProjectModal} from '../../components/CreateProjectModal/CreateProjectModal';
import {ProjectDetailsCard} from '../../components/CreateProjectModal/ProjectDetailsCard';
import {DashboardTable} from '../../components/DashboardTable/DashboardTable';
import {getPlacedOrders} from '../../utils/api';
import {DashboardPage} from '../DashBoardPage/DashboardPage';
import {NextStepPage} from '../NextStepPage/NextStepPage';
import {ProjectsTableRow} from './ProjectsTableRow';

import './ProjectsPage.scss';
import {useMarketplaceContext} from '../../context/MarketplaceContext';

interface ProjectsPageProps {
	icon: string;
	selectedAccount: Account;
	setShowDashboardNavigation: (value: boolean) => void;
}

const projectsTableHeaders = [
	{
		title: 'Project Name',
	},
	{
		title: 'Created By',
	},
	{
		title: 'Type',
	},
	{
		title: 'End Date',
	},
	{
		title: 'Provisioning',
	},
	{
		title: 'Project',
	},
];

export function ProjectsPage({
	icon,
	selectedAccount,
	setShowDashboardNavigation,
}: ProjectsPageProps) {
	const {channel} = useMarketplaceContext();
	const [visible, setVisible] = useState(false);
	const [showNextStepsPage, setShowNextStepsPage] = useState(false);
	const [projectOrders, setProjectOrders] = useState<PlacedOrder[]>([]);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		const makeFetch = async () => {
			setLoading(true);

			const {items} = await getPlacedOrders(
				selectedAccount.id,
				channel.id
			);

			const filteredOrders = items.filter(
				({orderTypeExternalReferenceCode}) =>
					orderTypeExternalReferenceCode === 'PROJECT60'
			);

			setProjectOrders(filteredOrders);

			setLoading(false);
		};

		makeFetch();
	}, [channel.id, selectedAccount, showNextStepsPage]);

	if (loading) {
		return (
			<ClayLoadingIndicator
				className="projects-page-loading-indicator"
				displayType="primary"
				shape="circle"
				size="md"
			/>
		);
	}

	if (showNextStepsPage) {
		return (
			<NextStepPage
				continueButtonText="Go to Dashboard"
				header={{
					description:
						'Solutions in progress project has been created and is now being processed. You will get an email notification when the trial is ready.',
					title: 'Next steps',
				}}
				linkText="Learn more about Projects"
				onClickContinue={() => {
					setShowDashboardNavigation(true);
					setShowNextStepsPage(false);
				}}
				showBackButton={false}
				showOrderId={false}
				size="lg"
			>
				<ProjectDetailsCard showHeader />
			</NextStepPage>
		);
	}

	return (
		<>
			<DashboardPage
				buttonMessage="New Project"
				messages={{
					description:
						'Manage projects to build and test your apps and solutions',
					title: 'Projects',
				}}
				onButtonClick={() => setVisible(true)}
			>
				<DashboardTable<PlacedOrder>
					emptyStateMessage={{
						description1:
							'Publish projects and they will show up here.',
						description2: 'Click on “New Projects” to start.',
						title: 'No projects yet',
					}}
					icon={icon}
					items={projectOrders}
					tableHeaders={projectsTableHeaders}
				>
					{(projectOrder) => {
						const date = new Date(projectOrder.createDate);
						const options: Intl.DateTimeFormatOptions = {
							day: 'numeric',
							month: 'short',
							year: 'numeric',
						};
						const formattedCreateDate = date.toLocaleDateString(
							'en-US',
							options
						);

						date.setDate(date.getDate() + 60);
						const formattedEndDate = date.toLocaleDateString(
							'en-US',
							options
						);

						return (
							<ProjectsTableRow
								author={projectOrder.author}
								createdAt={formattedCreateDate}
								endDate={formattedEndDate}
								projectName={
									projectOrder.customFields['Project Name']
								}
								status={projectOrder.orderStatusInfo.label_i18n}
							/>
						);
					}}
				</DashboardTable>
			</DashboardPage>

			{visible && (
				<CreateProjectModal
					currentChannel={channel}
					handleClose={() => setVisible(false)}
					selectedAccount={selectedAccount}
					setShowNextStepsPage={setShowNextStepsPage}
				/>
			)}
		</>
	);
}
